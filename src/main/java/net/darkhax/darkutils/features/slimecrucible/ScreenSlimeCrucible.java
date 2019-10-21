package net.darkhax.darkutils.features.slimecrucible;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import net.darkhax.bookshelf.util.MathsUtils;
import net.darkhax.bookshelf.util.RenderUtils;
import net.darkhax.bookshelf.util.RenderUtils.IQuadColorHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenSlimeCrucible extends ContainerScreen<ContainerSlimeCrucible> {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("darkutils", "textures/gui/container/slime_crucible.png");
    
    /**
     * An instance of the client world.
     */
    private final World clientWorld;
    
    /**
     * The progress of the scroll bar.
     */
    private float sliderProgress;
    
    /**
     * Whether or not the mouse is being dragged over the scroll bar.
     */
    private boolean mouseBeingDragged;
    
    /**
     * The amount of recipes to offset by the scroll bar.
     */
    private int recipeIndexOffset;
    
    /**
     * Whether or not recipes can be shown.
     */
    private boolean showRecipes;
    
    /**
     * The client side entity to be rendered into the GUI.
     */
    private SlimeEntity renderEntity;
    
    public ScreenSlimeCrucible(ContainerSlimeCrucible container, PlayerInventory playerInventory, ITextComponent title) {
        
        super(container, playerInventory, title);
        this.clientWorld = playerInventory.player.world;
        container.setUpdateListener(this::listenToContainerUpdate);
    }
    
    @Override
    public void render (int mouseX, int mouseY, float partialTicks) {
        
        super.render(mouseX, mouseY, partialTicks);
        
        final int selectionBoxX = this.guiLeft + 52;
        final int selectionBoxY = this.guiTop + 14;
        final int lastRecipeIndex = this.recipeIndexOffset + 12;
        
        // Render the slime entity onto the GUI
        if (this.renderEntity != null) {
            
            final int x = this.guiLeft + 28;
            final int y = this.guiTop + 38;
            InventoryScreen.drawEntityOnScreen(x, y, 30, x - mouseX, y - mouseY, this.renderEntity);
        }
        
        // If the entity doesn't exist and the type isn't null, create a new entity.
        else if (this.container.getCrucibleType() != null) {
            
            this.renderEntity = this.container.getCrucibleType().createSlime(this.clientWorld);
        }
        
        // Render the vanilla inventory tooltips.
        this.renderHoveredToolTip(mouseX, mouseY);
        
        // Render custom tooltps.
        this.renderRecipeTooltips(selectionBoxX, selectionBoxY, mouseX, mouseY, lastRecipeIndex);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer (int mouseX, int mouseY) {
        
        this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, this.ySize - 94f, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer (float partialTicks, int mouseX, int mouseY) {
        
        this.renderBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final int sliderHeightOffset = (int) (41.0F * this.sliderProgress);
        this.blit(this.guiLeft + 119, this.guiTop + 15 + sliderHeightOffset, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        final int selectionBoxX = this.guiLeft + 52;
        final int selectionBoxY = this.guiTop + 14;
        final int lastRecipeIndex = this.recipeIndexOffset + 12;
        
        // Render the recipe output backgrounds.
        this.renderRecipeOutputButtons(mouseX, mouseY, selectionBoxX, selectionBoxY, lastRecipeIndex);
        
        // Render the recipe output item icons.
        this.renderRecipeOutputs(mouseX, mouseY, selectionBoxX, selectionBoxY, lastRecipeIndex);
    }
    
    /**
     * Renders the button backgrounds for the recipe outputs.
     * 
     * @param mouseX The x position of the mouse.
     * @param mouseY The y position of the mouse.
     * @param selectionBoxX The starting x position of the selection box.
     * @param selectionBoxY The starting y position of the selection box.
     * @param lastRecipeIndex The last recipe index to display.
     */
    private void renderRecipeOutputButtons (int mouseX, int mouseY, int selectionBoxX, int selectionBoxY, int lastRecipeIndex) {
        
        // Iterate through the first 12 or less recipes to show.
        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
            
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeY = selectionBoxY + recipeRow * 18 + 2;
            final boolean canCraftRecipe = this.container.canCraft(i);
            int textureY = this.ySize;
            int color = 0xffffffff;
            
            // If the player can't craft it, and it's hidden, the recipe is not rendered.
            if (!canCraftRecipe && this.container.getAvailableRecipes().get(i).isHidden()) {
                
                continue;
            }
            
            // If the recipe can be crafted shift to the brighter texture.
            if (!canCraftRecipe) {
                
                textureY += 54;
            }
            
            // Render the selected/pressed version of the button.
            else if (i == this.container.getSelectedRecipeId()) {
                
                textureY += 18;
            }
            
            // Render the mouseover/highlighted texture
            else if (mouseX >= recipeX && mouseY >= recipeY && mouseX < recipeX + 16 && mouseY < recipeY + 18) {
                
                textureY += 36;
                color = this.container.getCrucibleType().getOverlayColor();
            }
            
            RenderUtils.drawTexturedRect(recipeX, recipeY - 1, this.blitOffset, 0, textureY, 16, 18, 256, 256, color);
        }
    }
    
    public static final IQuadColorHandler DARKEN_QUAD_COLORS = (stack, quad, providedColor) -> {
        
        final int originalColor = RenderUtils.DEFAULT_QUAD_COLORS.getColorForQuad(stack, quad, providedColor);
        
        return MathsUtils.multiplyColor(originalColor, 0.6f);
    };
    
    /**
     * Renders the output itemstack of the visible recipes.
     * 
     * @param mouseX The x position of the mouse.
     * @param mouseY The y position of the mouse.
     * @param selectionBoxX The starting x position of the selection box.
     * @param selectionBoxY The starting y position of the selection box.
     * @param lastRecipeIndex The last recipe index to display.
     */
    private void renderRecipeOutputs (int mouseX, int mouseY, int selectionBoxX, int selectionBoxY, int lastRecipeIndex) {
        
        RenderHelper.enableGUIStandardItemLighting();
        final List<RecipeSlimeCrafting> list = this.container.getAvailableRecipes();
        
        // Iterate the first 12 or less visible recipes.
        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
            
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeItemX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeItemY = selectionBoxY + recipeRow * 18 + 2;
            final boolean canCraft = this.container.canCraft(i);
            
            // Skip recipes that can't be crafted and are hidden.
            if (!canCraft && this.container.getAvailableRecipes().get(i).isHidden()) {
                
                continue;
            }
            
            RenderUtils.renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), recipeItemX, recipeItemY, -1, canCraft ? RenderUtils.DEFAULT_QUAD_COLORS : DARKEN_QUAD_COLORS);
        }
        
        RenderHelper.disableStandardItemLighting();
    }
    
    private void renderRecipeTooltips (int selectionBoxX, int selectionBoxY, int mouseX, int mouseY, int lastRecipeIndex) {
        
        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
            
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeItemX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeItemY = selectionBoxY + recipeRow * 18 + 2;
            
            // Render the tooltip for the recipe if one is under the mouse.
            if (mouseX >= recipeItemX && mouseY >= recipeItemY && mouseX < recipeItemX + 16 && mouseY < recipeItemY + 18) {
                
                final RecipeSlimeCrafting recipe = this.container.getAvailableRecipes().get(i);
                final ItemStack[] inputs = recipe.getValidItemStacks();
                
                if (!recipe.isHidden() || this.container.canCraft(i)) {
                    
                    final List<String> tooltip = new ArrayList<>();
                    tooltip.add(recipe.getRecipeOutput().getDisplayName().getFormattedText());
                    
                    final TextFormatting inputColor = recipe.isValid(this.container.getCurrentInput()) ? TextFormatting.GREEN : TextFormatting.RED;
                    final TextFormatting slimeColor = recipe.getSlimePoints() <= this.container.getSlimePoints() ? TextFormatting.GREEN : TextFormatting.RED;
                    final ItemStack inputStack = inputs[(int) (this.clientWorld.getGameTime() / 20 % inputs.length)];
                    tooltip.add(TextFormatting.GRAY + I18n.format("tooltips.darkutils.input", inputColor, inputStack.getDisplayName().getFormattedText(), recipe.getInputCount()));
                    tooltip.add(TextFormatting.GRAY + I18n.format("tooltips.darkutils.slime_points", this.container.getCrucibleType().getMaterialName().getFormattedText(), slimeColor, recipe.getSlimePoints()));
                    this.renderTooltip(tooltip, mouseX, mouseY);
                }
                
                break;
            }
        }
        
        // Render a tooltip if the player has a mouse over the slime entity.
        final int x = this.guiLeft + 20;
        final int y = this.guiTop + 22;
        
        if (mouseX >= x && mouseY >= y && mouseX < x + 16 && mouseY < y + 16) {
            
            final List<String> tooltip = new ArrayList<>();
            tooltip.add(I18n.format("tooltips.darkutils.slime_points", this.container.getCrucibleType().getMaterialName().getFormattedText(), TextFormatting.WHITE, this.container.getSlimePoints()));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltips.darkutils.slime_crucible.info", this.container.getCrucibleType().getMaterialName().getFormattedText()));
            this.renderTooltip(tooltip, mouseX, mouseY);
        }
    }
    
    @Override
    public boolean mouseClicked (double mouseX, double mouseY, int buttonId) {
        
        this.mouseBeingDragged = false;
        
        // Handle clicking on a recipe output button.
        if (this.showRecipes) {
            
            final int selectionBoxX = this.guiLeft + 52;
            final int selectionBoxY = this.guiTop + 14;
            
            for (int recipeId = this.recipeIndexOffset; recipeId < this.recipeIndexOffset + 12; recipeId++) {
                
                final int recipeButtonId = recipeId - this.recipeIndexOffset;
                final double relativeClickX = mouseX - (selectionBoxX + recipeButtonId % 4 * 16);
                final double relativeClickY = mouseY - (selectionBoxY + recipeButtonId / 4 * 18);
                
                if (relativeClickX >= 0.0D && relativeClickY >= 0.0D && relativeClickX < 16.0D && relativeClickY < 18.0D && this.container.enchantItem(this.minecraft.player, recipeId)) {
                    
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(this.container.getCrucibleType().getCraftingSound(), 1.0F));
                    this.minecraft.playerController.sendEnchantPacket(this.container.windowId, recipeId);
                    return true;
                }
            }
            
            final int scrollerX = this.guiLeft + 119;
            final int scrollerY = this.guiTop + 9;
            
            if (mouseX >= scrollerX && mouseX < scrollerX + 12 && mouseY >= scrollerY && mouseY < scrollerY + 62) {
                
                this.mouseBeingDragged = true;
            }
        }
        
        // Handle clicking on the slime entity.
        final int x = this.guiLeft + 20;
        final int y = this.guiTop + 22;
        
        final ItemStack mouseItem = this.playerInventory.getItemStack();
        if (mouseX >= x && mouseY >= y && mouseX < x + 16 && mouseY < y + 16 && this.container.enchantItem(this.minecraft.player, -42)) {
            
            this.minecraft.playerController.sendEnchantPacket(this.container.windowId, -42);
            this.renderEntity.squishAmount = 1f;
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(this.renderEntity.getEatSound(mouseItem), 1.0F));
            return true;
        }
        
        return super.mouseClicked(mouseX, mouseY, buttonId);
    }
    
    @Override
    public boolean mouseDragged (double mouseX, double mouseY, int mouseButtonId, double dragX, double dragY) {
        
        if (this.mouseBeingDragged && this.canScroll()) {
            
            final int i = this.guiTop + 14;
            final int j = i + 54;
            this.sliderProgress = ((float) mouseY - i - 7.5F) / (j - i - 15.0F);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int) (this.sliderProgress * this.getHiddenRows() + 0.5D) * 4;
            return true;
        }
        
        return super.mouseDragged(mouseX, mouseY, mouseButtonId, dragX, dragY);
    }
    
    @Override
    public boolean mouseScrolled (double mouseX, double mouseY, double mouseDelta) {
        
        // Handle scrolling through the recipes
        if (this.canScroll()) {
            
            final int hiddenRows = this.getHiddenRows();
            this.sliderProgress = (float) (this.sliderProgress - mouseDelta / hiddenRows);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int) (this.sliderProgress * hiddenRows + 0.5D) * 4;
        }
        
        return true;
    }
    
    /**
     * Checks if there are enough recipes for players to be able to scroll.
     * 
     * @return Whether or not there are enough recipes available to allow scrolling.
     */
    private boolean canScroll () {
        
        return this.showRecipes && this.container.getAvailableRecipesSize() > 12;
    }
    
    /**
     * Get the amount of rows which are being hidden.
     * 
     * @return The amount of hidden rows.
     */
    protected int getHiddenRows () {
        
        final int size = this.container.getAvailableRecipesSize();
        return size > 12 ? (size + 3) / 4 - 3 : 0;
    }
    
    /**
     * A hooks that is called when the container has it's contents changed.
     * 
     * @param inventory An inventory listener.
     */
    private void listenToContainerUpdate (IInventory inventory) {
        
        // Update the state of the container.
        this.container.onCraftMatrixChanged(inventory);
        this.showRecipes = this.container.canDisplayRecipes();
        
        // Reset the scrolling state back to the start.
        if (!this.showRecipes) {
            
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }
    }
}