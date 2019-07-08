package net.darkhax.darkutils.features.slimecrucible;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.darkhax.bookshelf.inventory.InventoryListenable;
import net.darkhax.bookshelf.inventory.SlotOutput;
import net.darkhax.bookshelf.util.WorldUtils;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerSlimeCrucible extends Container {
    
    @Nullable
    private final ServerPlayerEntity playerEntity;
    private final IWorldPosCallable worldPosition;
    private final World playerWorld;
    private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
    private final IntReferenceHolder slimePoints = IntReferenceHolder.single();
    private boolean hasSyncedType = false;
    private SlimeCrucibleType crucibleType;
    private final List<RecipeSlimeCrafting> availableRecipes = Lists.newArrayList();
    private ItemStack currentInput = ItemStack.EMPTY;
    private long lastSoundPlayingTick;
    final Slot slotInput;
    final Slot slotOutput;
    
    private final InventoryListenable inventory = new InventoryListenable(1, this::onCraftMatrixChanged);
    private final CraftResultInventory reultInventory = new CraftResultInventory();
    
    public ContainerSlimeCrucible(int id, PlayerInventory playerInv) {
        
        this(id, playerInv, IWorldPosCallable.DUMMY);
    }
    
    public ContainerSlimeCrucible(int id, PlayerInventory playerInventory, final IWorldPosCallable worldPosition) {
        
        super(DarkUtils.content.containerSlimeCrucible, id);
        this.playerEntity = playerInventory.player instanceof ServerPlayerEntity ? (ServerPlayerEntity) playerInventory.player : null;
        this.playerWorld = playerInventory.player.world;
        this.worldPosition = worldPosition;
        this.slotInput = this.addSlot(new Slot(this.inventory, 0, 20, 49));
        this.slotOutput = this.addSlot(new SlotOutput(this.reultInventory, 1, 143, 33, this::onOutputSlotChanged));
        
        this.worldPosition.consume( (world, pos) -> {
            
            final TileEntity tileEntity = world.getTileEntity(pos);
            
            if (tileEntity instanceof TileEntitySlimeCrucible) {
                
                this.setCrucibleType(((TileEntitySlimeCrucible) tileEntity).getCrucibleType());
                this.slimePoints.set(((TileEntitySlimeCrucible) tileEntity).getContainedSlimePoints());
            }
        });
        
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
        this.func_216958_a(this.slimePoints);
    }
    
    public boolean canCraft (int recipeIndex) {
        
        return this.canCraft(this.getAvailableRecipes().get(recipeIndex));
    }
    
    public boolean canCraft (RecipeSlimeCrafting recipe) {
        
        return recipe.isValid(this.getCurrentInput(), this.getCrucibleType(), this.getSlimePoints());
    }
    
    public void setCrucibleType (SlimeCrucibleType type) {
        
        this.crucibleType = type;
        this.updateAvailableRecipes();
    }
    
    public SlimeCrucibleType getCrucibleType () {
        
        return this.crucibleType;
    }
    
    public int getSelectedRecipe () {
        
        return this.selectedRecipe.get();
    }
    
    public int getSlimePoints () {
        
        return this.slimePoints.get();
    }
    
    public List<RecipeSlimeCrafting> getAvailableRecipes () {
        
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
        
        if (id >= 0 && id < this.availableRecipes.size() && this.canCraft(id)) {
            this.selectedRecipe.set(id);
            this.updateOutputs();
            return true;
        }
        
        // TODO let items be eaten
        return false;
    }
    
    @Override
    public void detectAndSendChanges () {
        
        super.detectAndSendChanges();
        
        if (!this.hasSyncedType && this.playerEntity != null && this.getType() != null) {
            
            DarkUtils.NETWORK.sendToPlayer(this.playerEntity, new MessageSyncCrucibleType(this.getCrucibleType().getRegistryName()));
            this.hasSyncedType = true;
        }
    }
    
    @Override
    public void onCraftMatrixChanged (IInventory inventoryIn) {
        
        final ItemStack inputStack = this.slotInput.getStack();
        
        if (inputStack.getItem() != this.currentInput.getItem()) {
            
            this.currentInput = inputStack.copy();
        }
        
        // TODO check if the item is still valid before resetting.
        this.selectedRecipe.set(-1);
        this.slotOutput.putStack(ItemStack.EMPTY);
        this.updateAvailableRecipes();
    }
    
    public ItemStack getCurrentInput () {
        
        return this.currentInput;
    }
    
    private void updateAvailableRecipes () {
        
        this.availableRecipes.clear();
        
        if (this.getCrucibleType() != null) {
            
            Collection<RecipeSlimeCrafting> recipes = WorldUtils.getRecipeList(DarkUtils.content.recipeTypeSlimeCrafting, this.playerWorld.getRecipeManager());
            recipes = recipes.stream().sorted(Comparator.comparingInt(recipe -> this.canCraft(recipe) ? 0 : 1)).collect(Collectors.toList());
            for (final RecipeSlimeCrafting recipe : recipes) {
                
                if (recipe.isValid(this.getCrucibleType())) {
                    
                    this.availableRecipes.add(recipe);
                }
            }
        }
    }
    
    private void updateOutputs () {
        
        if (!this.availableRecipes.isEmpty() && this.selectedRecipe.get() >= 0 && this.selectedRecipe.get() < this.availableRecipes.size()) {
            
            final RecipeSlimeCrafting recipeSelected = this.availableRecipes.get(this.selectedRecipe.get());
            this.slotOutput.putStack(recipeSelected.getCraftingResult(this.inventory));
        }
        
        else {
            
            this.slotOutput.putStack(ItemStack.EMPTY);
        }
        
        this.detectAndSendChanges();
    }
    
    @Override
    public ContainerType<?> getType () {
        
        return DarkUtils.content.containerSlimeCrucible;
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
        this.worldPosition.consume( (world, posIn) -> this.clearContainer(playerIn, playerIn.world, this.inventory));
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
        
        if (this.getCrucibleType() != null) {
            
            final long worldTime = world.getGameTime();
            if (this.lastSoundPlayingTick != worldTime) {
                world.playSound((PlayerEntity) null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                this.lastSoundPlayingTick = worldTime;
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