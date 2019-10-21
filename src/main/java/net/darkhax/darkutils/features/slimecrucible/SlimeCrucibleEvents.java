package net.darkhax.darkutils.features.slimecrucible;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

public class SlimeCrucibleEvents {
    
    /**
     * This event is fired when a player crafts an item using the slime crucible. It allows you
     * to change change the output item or do other things in reaction to crafting an item.
     * 
     * This event is fired on the forge event bus and can not be canceled.
     */
    public static class RecipeCraftedEvent extends Event {
        
        /**
         * The recipe being crafted.
         */
        private final RecipeSlimeCrafting recipe;
        
        /**
         * The player crafting the recipe.
         */
        private final PlayerEntity player;
        
        /**
         * The world instance.
         */
        private final World world;
        
        /**
         * The position of the slime crucible.
         */
        private final BlockPos pos;
        
        /**
         * The original output item before modifications.
         */
        private final ItemStack originalOutput;
        
        /**
         * The output item that will be used.
         */
        private ItemStack output;
        
        public RecipeCraftedEvent(RecipeSlimeCrafting recipe, PlayerEntity player, World world, BlockPos pos, ItemStack originalOutput) {
            
            this.recipe = recipe;
            this.player = player;
            this.world = world;
            this.pos = pos;
            this.originalOutput = originalOutput;
            this.output = originalOutput.copy();
        }
        
        /**
         * Gets the stack instance that will actually be created.
         * 
         * @return The actual recipe output.
         */
        public ItemStack getOutput () {
            
            return this.output;
        }
        
        /**
         * Sets the output to a different item stack.
         * 
         * @param output The new output stack.
         */
        public void setOutput (ItemStack output) {
            
            this.output = output;
        }
        
        /**
         * Gets the recipe being crafted.
         * 
         * @return The recipe being crafted.
         */
        public RecipeSlimeCrafting getRecipe () {
            
            return this.recipe;
        }
        
        /**
         * Gets the player crafting the recipe.
         * 
         * @return The player crafting the recipe.
         */
        public PlayerEntity getPlayer () {
            
            return this.player;
        }
        
        /**
         * Gets the world instance.
         * 
         * @return The world instance.
         */
        public World getWorld () {
            
            return this.world;
        }
        
        /**
         * Gets the position of the crucible block.
         * 
         * @return The position of the crucible block.
         */
        public BlockPos getPos () {
            
            return this.pos;
        }
        
        /**
         * Gets the original and unmodified output stack.
         * 
         * @return The original and unmodified output stack.
         */
        public ItemStack getOriginalOutput () {
            
            return this.originalOutput;
        }
    }
    
    /**
     * This event is fired on the client and can be used to determine if a recipe is visible to
     * a player or not. This event can not be canceled. This event is fired on the forge event
     * bus. You can use this recipe to make normally visible recipes hidden, or normally hidden
     * recipes visible.
     */
    @OnlyIn(Dist.CLIENT)
    public static class RecipeVisibleEvent extends Event {
        
        /**
         * The recipe being tested for visibility.
         */
        private final RecipeSlimeCrafting recipe;
        
        /**
         * The player trying to view the recipe.
         */
        private final PlayerEntity player;
        
        /**
         * Whether or not the recipe is visible under normal circumstances.
         */
        private final boolean originallyVisible;
        
        /**
         * Whether or not the recipe will be visible.
         */
        private boolean isVisible;
        
        public RecipeVisibleEvent(RecipeSlimeCrafting recipe, PlayerEntity player, boolean isVisible) {
            
            this.recipe = recipe;
            this.player = player;
            this.originallyVisible = isVisible;
            this.isVisible = isVisible;
        }
        
        /**
         * Checks if the recipe would originally be visible to the player.
         * 
         * @return Whether or not the recipe would normally be visible.
         */
        public boolean wasOriginallyVisible () {
            
            return this.originallyVisible;
        }
        
        /**
         * Checks if the recipe is currently visible.
         * 
         * @return Whether or not the recipe is currently visible.
         */
        public boolean isVisible () {
            
            return this.isVisible;
        }
        
        /**
         * Sets the visibility of the recipe. Allows the recipe to be hidden or made visible.
         * 
         * @param isVisible Whether or not the recipe should be visible.
         */
        public void setVisible (boolean isVisible) {
            
            this.isVisible = isVisible;
        }
        
        /**
         * The recipe being tested.
         * 
         * @return The recipe being tested.
         */
        public RecipeSlimeCrafting getRecipe () {
            
            return this.recipe;
        }
        
        /**
         * The player trying to view the recipe.
         * 
         * @return The player trying to view the recipe.
         */
        public PlayerEntity getPlayer () {
            
            return this.player;
        }
    }
}