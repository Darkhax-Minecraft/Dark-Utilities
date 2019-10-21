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
import net.darkhax.darkutils.features.slimecrucible.SlimeCrucibleEvents.RecipeCraftedEvent;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ContainerSlimeCrucible extends Container {
    
    /**
     * The server side player entity. This is only available on the server, and represents the
     * player who opened this instance of the container.
     */
    @Nullable
    private final ServerPlayerEntity playerEntity;
    
    /**
     * A reference to the world and position of the block that was interacted with to open the
     * container. This exists on both the client and server but may only be used reliably on
     * the server.
     */
    private final IWorldPosCallable worldPosition;
    
    /**
     * A reference to the player's world. This is available on the client and server.
     */
    private final World playerWorld;
    
    /**
     * A synchronized integer which represents the recipe selected in the container. -1 is a
     * special value which represents no selected recipe. -42 is used when the player clicks on
     * the slime in the GUI.
     */
    private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
    
    /**
     * A synchronized integer which represents the amount of available slime points.
     */
    private final IntReferenceHolder slimePoints = IntReferenceHolder.single();
    
    /**
     * A boolean flag used to check if the container has synced it's data to the client. This
     * is used for one-time synchronization packets in {@link #detectAndSendChanges()}.
     */
    private boolean hasSyncedType = false;
    
    /**
     * The type of slime crucible that is being interacted with.
     */
    private SlimeCrucibleType crucibleType;
    
    /**
     * A list of sorted recipes that represent the recipes currently available to the player.
     * Not all of these recipes are valid to be crafting, but could be if the player had the
     * right ingredients.
     */
    private final List<RecipeSlimeCrafting> availableRecipes = Lists.newArrayList();
    
    /**
     * The item that is currently in the input slot.
     */
    private ItemStack currentInput = ItemStack.EMPTY;
    
    /**
     * The last tick that a sound was played. This is used to prevent situations where sounds
     * are played twice by events firing on the ame tick.
     */
    private long lastSoundPlayingTick;
    
    /**
     * The input slot where people put in ingredients.
     */
    final Slot slotInput;
    
    /**
     * The output slot where people take items out.
     */
    final Slot slotOutput;
    
    /**
     * An inventory that is tied to the {@link #slotInput}.
     */
    private final InventoryListenable inventory = new InventoryListenable(1, this::onCraftMatrixChanged);
    
    /**
     * An inventory that is tied to the {@link #slotOutput}.
     */
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
        this.selectedRecipe.set(-1);
        
        // Run an initial sync to get the crucible type and stored points in the original tile
        // entity.
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
        
        // Adds the trackable fields to the container's tracking list.
        this.trackInt(this.selectedRecipe);
        this.trackInt(this.slimePoints);
    }
    
    /**
     * Checks if the current selected recipe can be crafted.
     * 
     * @param recipeIndex The index of the recipe being crafted.
     * @return Whether or not the recipe can be crafted.
     */
    public boolean canCraft (int recipeIndex) {
        
        return this.canCraft(this.getAvailableRecipes().get(recipeIndex));
    }
    
    /**
     * Checks if a recipe can be crafted.
     * 
     * @param recipe The recipe to check.
     * @return Whether or not the recipe can be crafted.
     */
    public boolean canCraft (RecipeSlimeCrafting recipe) {
        
        return recipe.isValid(this.getCurrentInput(), this.getCrucibleType(), this.getSlimePoints());
    }
    
    /**
     * Sets the current crucible type. This will cause the list of available recipes to be
     * updated.
     * 
     * @param type The new crucible type.
     */
    public void setCrucibleType (SlimeCrucibleType type) {
        
        this.crucibleType = type;
        this.updateAvailableRecipes();
    }
    
    /**
     * Gets the crucible type being interacted with.
     * 
     * @return The type of crucible being interacted with.
     */
    public SlimeCrucibleType getCrucibleType () {
        
        return this.crucibleType;
    }
    
    /**
     * Gets the Id of the selected recipe.
     * 
     * @return The If of the selected recipe.
     */
    public int getSelectedRecipeId () {
        
        return this.selectedRecipe.get();
    }
    
    /**
     * Gets the amount of stored slime points.
     * 
     * @return The amount of stored slime points.
     */
    public int getSlimePoints () {
        
        return this.slimePoints.get();
    }
    
    /**
     * Gets the list of available crafting crecipes.
     * 
     * @return The list of available recipes.
     */
    public List<RecipeSlimeCrafting> getAvailableRecipes () {
        
        return this.availableRecipes;
    }
    
    /**
     * Gets the amount of available recipes.
     * 
     * @return The amount of available recipes.
     */
    public int getAvailableRecipesSize () {
        
        return this.availableRecipes.size();
    }
    
    /**
     * Checks if the container can have it's recipes displayed.
     * 
     * @return Whether or not the recipes can be displayed.
     */
    public boolean canDisplayRecipes () {
        
        return !this.availableRecipes.isEmpty();
    }
    
    /**
     * Gets the itemstack in the input slot.
     * 
     * @return The item in the input slot.
     */
    public ItemStack getCurrentInput () {
        
        return this.currentInput;
    }
    
    /**
     * Updates the list of available crafting recipes.
     */
    private void updateAvailableRecipes () {
        
        this.availableRecipes.clear();
        
        if (this.getCrucibleType() != null) {
            
            // Get all the recipes from the crafting manager. This list is pre-sorted using the
            // translation key of the output.
            Collection<RecipeSlimeCrafting> recipes = WorldUtils.getRecipeList(DarkUtils.content.recipeTypeSlimeCrafting, this.playerWorld.getRecipeManager());
            
            // Sorts the recipes further to bring craftable recipes to the front and
            // uncraftable hidden recipes to the back.
            recipes = recipes.stream().sorted(Comparator.comparingInt(recipe -> this.canCraft(recipe) ? -1 : recipe.isHidden() ? 1 : 0)).collect(Collectors.toList());
            
            // Filter through all the recipes and check if the crucible type is valid for that
            // recipe.
            for (final RecipeSlimeCrafting recipe : recipes) {
                
                if (recipe.isValid(this.getCrucibleType())) {
                    
                    this.availableRecipes.add(recipe);
                }
            }
        }
    }
    
    /**
     * Updates the state of the output slot based on the state of the container.
     */
    private void updateOutputs () {
        
        // Check if a valid recipe has been selected.
        if (!this.availableRecipes.isEmpty() && this.selectedRecipe.get() >= 0 && this.selectedRecipe.get() < this.availableRecipes.size()) {
            
            // Set the output slot to hold the output of the current selected recipe.
            this.slotOutput.putStack(this.getSelectedRecipe().getCraftingResult(this.inventory));
        }
        
        // If no valid recipe has been selected clear the output slot.
        else {
            
            this.slotOutput.putStack(ItemStack.EMPTY);
        }
        
        // Force an update attempt to the client.
        this.detectAndSendChanges();
    }
    
    /**
     * Gets the recipe that has been selected by the user.
     * 
     * @return The current selected recipe.
     */
    public RecipeSlimeCrafting getSelectedRecipe () {
        
        return this.availableRecipes.get(this.getSelectedRecipeId());
    }
    
    private ItemStack tempCraftingStack = ItemStack.EMPTY;
    
    /**
     * Handles the output slot being changed by the player.
     * 
     * @param player The player who changed the slot.
     * @param stack The newly created ItemStack.
     */
    private ItemStack onOutputSlotChanged (PlayerEntity player, ItemStack stack) {
        
        final RecipeSlimeCrafting recipe = this.getSelectedRecipe();
        final ItemStack inputStack = this.slotInput.decrStackSize(recipe.getInputCount());
        final ItemStack outputCopy = stack;
        
        // Remove the consumed slime points from the tile and from the container.
        this.worldPosition.consume( (world, pos) -> {
            
            final RecipeCraftedEvent event = new RecipeCraftedEvent(recipe, player, world, pos, outputCopy);
            MinecraftForge.EVENT_BUS.post(event);
            this.tempCraftingStack = event.getOutput();
            
            this.playerEntity.addStat(DarkUtils.content.statSlimeCrucibleItemsCrafted);
            final int consumedPoints = recipe.getSlimePoints();
            this.slimePoints.set(this.getSlimePoints() - consumedPoints);
            final TileEntity tileEntity = world.getTileEntity(pos);
            
            if (tileEntity instanceof TileEntitySlimeCrucible) {
                
                final TileEntitySlimeCrucible crucible = (TileEntitySlimeCrucible) tileEntity;
                crucible.removeSlimePoints(consumedPoints);
            }
        });
        
        // If there are remaining input items left, trigger the output update again.
        if (!inputStack.isEmpty()) {
            
            this.updateOutputs();
        }
        
        stack = this.tempCraftingStack;
        this.tempCraftingStack = ItemStack.EMPTY;
        
        // Call the creation hook on the item so they can run their effects.
        stack.getItem().onCreated(stack, player.world, player);
        
        // Play the crafting sound on the server.
        this.worldPosition.consume(this::playCraftingSound);
        
        return stack;
    }
    
    /**
     * Plays the sound that is used when a player crafts an item.
     * 
     * @param world The world to play the sound in.
     * @param pos The source position of the sound.
     */
    private void playCraftingSound (World world, BlockPos pos) {
        
        if (this.getCrucibleType() != null) {
            
            final long worldTime = world.getGameTime();
            
            if (this.lastSoundPlayingTick != worldTime) {
                
                world.playSound((PlayerEntity) null, pos, this.getCrucibleType().getCraftingSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                this.lastSoundPlayingTick = worldTime;
            }
        }
    }
    
    /**
     * Sets an update listener on the inventory of the container.
     * 
     * @param listener The new update listener.
     */
    public void setUpdateListener (Consumer<IInventory> listener) {
        
        this.inventory.setInventoryListener(listener);
    }
    
    @Override
    public boolean canInteractWith (PlayerEntity playerIn) {
        
        // To interact the player must be within 64 blocks and the block must be an
        // ISlimeCrucibleBlock
        return WorldUtils.isWithinDistanceAndUsable(this.worldPosition, playerIn, state -> state.getBlock() instanceof ISlimeCrucibleBlock, 64d);
    }
    
    @Override
    public boolean enchantItem (PlayerEntity playerIn, int id) {
        
        // Handle the player clicking on a recipe button
        if (id >= 0 && id < this.availableRecipes.size() && this.canCraft(id)) {
            
            this.selectedRecipe.set(id);
            this.updateOutputs();
            return true;
        }
        
        // Handle the player clicking on the slime in the GUI.
        if (id == -42) {
            
            final ItemStack mouseStack = playerIn.inventory.getItemStack();
            final int itemPoints = TileEntitySlimeCrucible.getSlimePointsForItem(this.playerWorld, mouseStack, this.getCrucibleType());
            
            // If the item in the mouse on the GUI is a valid food item, consume it and make
            // slime points.
            if (itemPoints > 0 && this.getSlimePoints() < this.getCrucibleType().getMaxSlimePoints()) {
                
                this.worldPosition.consume( (world, pos) -> {
                    
                    final TileEntity tile = world.getTileEntity(pos);
                    
                    if (tile instanceof TileEntitySlimeCrucible) {
                        
                        ((TileEntitySlimeCrucible) tile).addSlimePoints(itemPoints);
                        
                        if (this.playerEntity != null) {
                            
                            this.playerEntity.addStat(DarkUtils.content.statSlimeCrucibleFeed);
                        }
                    }
                });
                
                mouseStack.shrink(1);
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void detectAndSendChanges () {
        
        // Check if the TileEntity has had it's slime points changed, and reflect those
        // changes.
        this.worldPosition.consume( (worldIn, pos) -> {
            
            final TileEntity tileEntity = worldIn.getTileEntity(pos);
            
            if (tileEntity instanceof TileEntitySlimeCrucible) {
                
                final int currentSlimePoints = ((TileEntitySlimeCrucible) tileEntity).getContainedSlimePoints();
                
                if (this.getSlimePoints() != currentSlimePoints) {
                    
                    this.slimePoints.set(currentSlimePoints);
                }
            }
        });
        
        super.detectAndSendChanges();
        
        // On the first sync of the container, send an additional packet with the crucible type
        // to the client.
        if (!this.hasSyncedType && this.playerEntity != null && this.getType() != null) {
            
            DarkUtils.NETWORK.sendToPlayer(this.playerEntity, new MessageSyncCrucibleType(this.getCrucibleType().getRegistryName()));
            this.hasSyncedType = true;
        }
    }
    
    @Override
    public void onCraftMatrixChanged (IInventory inventoryIn) {
        
        final ItemStack inputStack = this.slotInput.getStack();
        
        // Update the item that is in the input slot.
        if (!ItemStack.areItemStacksEqual(inputStack, this.currentInput)) {
            
            this.currentInput = inputStack.copy();
        }
        
        // If there is no selected recipe, or the selected recipe can not be crafted, deeselect
        // the recipe and clear the output.
        if (this.getSelectedRecipeId() == -1 || !this.getAvailableRecipes().get(this.getSelectedRecipeId()).isValid(inputStack, this.getCrucibleType(), this.getSlimePoints())) {
            
            this.selectedRecipe.set(-1);
            this.slotOutput.putStack(ItemStack.EMPTY);
        }
        
        // Refresh the list of available recipes.
        this.updateAvailableRecipes();
    }
    
    @Override
    public ContainerType<?> getType () {
        
        return DarkUtils.content.containerSlimeCrucible;
    }
    
    @Override
    public boolean canMergeSlot (ItemStack stack, Slot slotIn) {
        
        return false;
    }
    
    @Override
    public void onContainerClosed (PlayerEntity playerIn) {
        
        super.onContainerClosed(playerIn);
        
        // Clear the output
        this.reultInventory.removeStackFromSlot(1);
        
        // Restore any remaining input items to the player.
        this.worldPosition.consume( (world, posIn) -> this.clearContainer(playerIn, playerIn.world, this.inventory));
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