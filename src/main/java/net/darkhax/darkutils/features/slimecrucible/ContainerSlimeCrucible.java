package net.darkhax.darkutils.features.slimecrucible;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.InventoryListener;
import net.darkhax.darkutils.SlotOutput;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerSlimeCrucible extends Container {
    
    private final IWorldPosCallable worldPosition;
    private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
    private final World world;
    private List<StonecuttingRecipe> availableRecipes = Lists.newArrayList();
    private ItemStack currentInput = ItemStack.EMPTY;
    private long lastSoundPlayingTick;
    final Slot slotInput;
    final Slot slotOutput;
    
    private final InventoryListener inventory = new InventoryListener(1, this::onCraftMatrixChanged);
    private final CraftResultInventory reultInventory = new CraftResultInventory();
    
    public ContainerSlimeCrucible(int id, PlayerInventory playerInv) {
        
        this(id, playerInv, IWorldPosCallable.DUMMY);
    }
    
    public ContainerSlimeCrucible(int id, PlayerInventory playerInventory, final IWorldPosCallable worldPosition) {
        
        super(DarkUtils.containerType, id);
        this.worldPosition = worldPosition;
        this.world = playerInventory.player.world;
        this.slotInput = this.addSlot(new Slot(this.inventory, 0, 20, 33));
        this.slotOutput = this.addSlot(new SlotOutput(this.reultInventory, 1, 143, 33, this::onOutputSlotChanged));
        
        // Add player inventory
        for (int row = 0; row < 3; row++) {
            
            for (int column = 0; column < 9; column++) {
                
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        
        // Add hotbar slots
        for (int slotIndex = 0; slotIndex < 9; slotIndex++) {
            
            this.addSlot(new Slot(playerInventory, slotIndex, 8 + slotIndex * 18, 142));
        }
        
        this.func_216958_a(this.selectedRecipe);
    }
    
    public int getSelectedRecipe () {
        
        return this.selectedRecipe.get();
    }
    
    public List<StonecuttingRecipe> getAvailableRecipes () {
        
        return this.availableRecipes;
    }
    
    public int getAvailableRecipesSize () {
        
        return this.availableRecipes.size();
    }
    
    public boolean canDisplayRecipes () {
        
        return this.slotInput.getHasStack() && !this.availableRecipes.isEmpty();
    }
    
    @Override
    public boolean canInteractWith (PlayerEntity playerIn) {
        
        return true; // isWithinUsableDistance(this.worldPosition, playerIn,
                     // Blocks.STONECUTTER);
    }
    
    @Override
    public boolean enchantItem (PlayerEntity playerIn, int id) {
        
        if (id >= 0 && id < this.availableRecipes.size()) {
            this.selectedRecipe.set(id);
            this.updateOutputs();
        }
        
        return true;
    }
    
    @Override
    public void onCraftMatrixChanged (IInventory inventoryIn) {
        
        final ItemStack inputStack = this.slotInput.getStack();
        if (inputStack.getItem() != this.currentInput.getItem()) {
            this.currentInput = inputStack.copy();
            this.updateAvailableRecipes(inventoryIn, inputStack);
        }
        
    }
    
    private void updateAvailableRecipes (IInventory inventory, ItemStack inputStack) {
        
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.slotOutput.putStack(ItemStack.EMPTY);
        if (!inputStack.isEmpty()) {
            this.availableRecipes = this.world.getRecipeManager().getRecipes(IRecipeType.STONECUTTING, inventory, this.world);
        }
    }
    
    private void updateOutputs () {
        
        if (!this.availableRecipes.isEmpty()) {
            final StonecuttingRecipe stonecuttingrecipe = this.availableRecipes.get(this.selectedRecipe.get());
            this.slotOutput.putStack(stonecuttingrecipe.getCraftingResult(this.inventory));
        }
        else {
            this.slotOutput.putStack(ItemStack.EMPTY);
        }
        
        this.detectAndSendChanges();
    }
    
    @Override
    public ContainerType<?> getType () {
        
        return DarkUtils.containerType;
    }
    
    public void setUpdateListener (Consumer<IInventory> listener) {
        
        this.inventory.setInventoryListener(listener);
    }
    
    @Override
    public boolean canMergeSlot (ItemStack stack, Slot slotIn) {
        
        return false;
    }
    
    @Override
    public void onContainerClosed (PlayerEntity playerIn) {
        
        super.onContainerClosed(playerIn);
        this.reultInventory.removeStackFromSlot(1);
        this.worldPosition.consume( (worldIn, posIn) -> this.clearContainer(playerIn, playerIn.world, this.inventory));
    }
    
    private void onOutputSlotChanged (PlayerEntity player, ItemStack stack) {
        
        final ItemStack inputStack = this.slotInput.decrStackSize(1);
        
        if (!inputStack.isEmpty()) {
            this.updateOutputs();
        }
        
        stack.getItem().onCreated(stack, player.world, player);
        this.worldPosition.consume(this::playCraftingSound);
    }
    
    private void playCraftingSound (World world, BlockPos pos) {
        
        final TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TileEntitySlimeCrucible) {
            
            final SlimeCrucibleType type = ((TileEntitySlimeCrucible) tileEntity).getCrucibleType();
            
            if (type != null) {
                
                final long worldTime = world.getGameTime();
                if (this.lastSoundPlayingTick != worldTime) {
                    world.playSound((PlayerEntity) null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.lastSoundPlayingTick = worldTime;
                }
            }
        }
    }
    
    @Override
    public ItemStack transferStackInSlot (PlayerEntity playerIn, int index) {
        
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {
            
            final ItemStack inputStack = slot.getStack();
            final Item inputItem = inputStack.getItem();
            itemstack = inputStack.copy();
            
            // Shift click on output slot
            if (index == 1) {
                
                inputItem.onCreated(inputStack, playerIn.world, playerIn);
                
                if (!this.mergeItemStack(inputStack, 2, 38, true)) {
                    
                    return ItemStack.EMPTY;
                }
                
                slot.onSlotChange(inputStack, itemstack);
            }
            
            // Shift click on input slot
            else if (index == 0) {
                
                if (!this.mergeItemStack(inputStack, 2, 38, false)) {
                    
                    return ItemStack.EMPTY;
                }
            }
            
            // Handle player inventory
            else if (index >= 2 && index < 29) {
                
                if (!this.mergeItemStack(inputStack, 29, 38, false)) {
                    
                    return ItemStack.EMPTY;
                }
            }
            
            // Handle HotBar
            else if (index >= 29 && index < 38 && !this.mergeItemStack(inputStack, 2, 29, false)) {
                
                return ItemStack.EMPTY;
            }
            
            if (inputStack.isEmpty()) {
                
                slot.putStack(ItemStack.EMPTY);
            }
            
            slot.onSlotChanged();
            
            if (inputStack.getCount() == itemstack.getCount()) {
                
                return ItemStack.EMPTY;
            }
            
            slot.onTake(playerIn, inputStack);
            this.detectAndSendChanges();
        }
        
        return itemstack;
    }
}