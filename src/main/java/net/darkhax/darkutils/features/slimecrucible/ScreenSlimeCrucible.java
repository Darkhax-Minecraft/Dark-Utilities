package net.darkhax.darkutils.features.slimecrucible;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import net.darkhax.darkutils.TempItemRenderer;
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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenSlimeCrucible extends ContainerScreen<ContainerSlimeCrucible> {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("darkutils", "textures/gui/container/slime_crucible.png");
    private final World clientWorld;
    private float sliderProgress;
    private boolean mouseBeingDragged;
    private int recipeIndexOffset;
    private boolean showRecipes;
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
        
        if (this.renderEntity != null) {
            
            final int x = this.guiLeft + 28;
            final int y = this.guiTop + 38;
            InventoryScreen.drawEntityOnScreen(x, y, 30, x - mouseX, y - mouseY, this.renderEntity);
        }
        
        else if (this.container.getType() != null) {
            
            this.renderEntity = this.container.getCrucibleType().createSlime(this.clientWorld);
        }
        
        this.renderHoveredToolTip(mouseX, mouseY);
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
        this.renderRecipeOutputButtons(mouseX, mouseY, selectionBoxX, selectionBoxY, lastRecipeIndex);
        this.renderRecipeOutputs(selectionBoxX, mouseX, mouseY, selectionBoxY, lastRecipeIndex);
    }
    
    private void renderRecipeOutputButtons (int mouseX, int mouseY, int selectionBoxX, int selectionBoxY, int lastRecipeIndex) {
        
        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
            
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeY = selectionBoxY + recipeRow * 18 + 2;
            final boolean canCraftRecipe = this.container.canCraft(i);
            int textureY = this.ySize;
            int color = 0xffffffff;
            
            if (!canCraftRecipe && this.container.getAvailableRecipes().get(i).isHidden()) {
                
                continue;
            }
            
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
            
            TempItemRenderer.drawModalRectWithCustomSizedTexture(recipeX, recipeY - 1, this.blitOffset, 0, textureY, 16, 18, 256, 256, color);
        }
    }
    
    private void renderRecipeOutputs (int selectionBoxX, int mouseX, int mouseY, int selectionBoxY, int lastRecipeIndex) {
        
        RenderHelper.enableGUIStandardItemLighting();
        final List<RecipeSlimeCrafting> list = this.container.getAvailableRecipes();
        
        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
            
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeItemX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeItemY = selectionBoxY + recipeRow * 18 + 2;
            
            if (!this.container.canCraft(i) && this.container.getAvailableRecipes().get(i).isHidden()) {
                
                continue;
            }
            
            TempItemRenderer.renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), recipeItemX, recipeItemY, this.container.canCraft(i) ? 0xffffffff : 0x80808080);
        }
        
        RenderHelper.disableStandardItemLighting();
    }
    
    private void renderRecipeTooltips (int selectionBoxX, int selectionBoxY, int mouseX, int mouseY, int lastRecipeIndex) {
        
        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
            
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeItemX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeItemY = selectionBoxY + recipeRow * 18 + 2;
            
            // Attempt to render tooltip
            if (mouseX >= recipeItemX && mouseY >= recipeItemY && mouseX < recipeItemX + 16 && mouseY < recipeItemY + 18) {
                
                final RecipeSlimeCrafting recipe = this.container.getAvailableRecipes().get(i);
                final ItemStack[] inputs = recipe.getValidItemStacks();
                
                if (!recipe.isHidden() || this.container.canCraft(i)) {
                    
                    final List<String> tooltip = new ArrayList<>();
                    tooltip.add(I18n.format("tooltips.darkutils.input", inputs[(int) (this.clientWorld.getGameTime() / 20 % inputs.length)].getDisplayName().getFormattedText()));
                    tooltip.add(I18n.format("tooltips.darkutils.output", recipe.getRecipeOutput().getDisplayName().getFormattedText()));
                    tooltip.add(I18n.format("tooltips.darkutils.slime_points", this.container.getCrucibleType().getMaterialName().getFormattedText(), recipe.getSlimePoints()));
                    this.renderTooltip(tooltip, mouseX, mouseY);
                }
                
                break;
            }
        }
        
        final int x = this.guiLeft + 20;
        final int y = this.guiTop + 22;
        
        if (mouseX >= x && mouseY >= y && mouseX < x + 16 && mouseY < y + 16) {
            
            final List<String> tooltip = new ArrayList<>();
            tooltip.add(I18n.format("tooltips.darkutils.slime_points", this.container.getCrucibleType().getMaterialName().getFormattedText(), this.container.getSlimePoints()));
            this.renderTooltip(tooltip, mouseX, mouseY);
        }
    }
    
    @Override
    public boolean mouseClicked (double mouseX, double mouseY, int buttonId) {
        
        this.mouseBeingDragged = false;
        
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
        else {
            return super.mouseDragged(mouseX, mouseY, mouseButtonId, dragX, dragY);
        }
    }
    
    @Override
    public boolean mouseScrolled (double mouseX, double mouseY, double mouseDelta) {
        
        if (this.canScroll()) {
            final int i = this.getHiddenRows();
            this.sliderProgress = (float) (this.sliderProgress - mouseDelta / i);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int) (this.sliderProgress * i + 0.5D) * 4;
        }
        
        return true;
    }
    
    private boolean canScroll () {
        
        return this.showRecipes && this.container.getAvailableRecipesSize() > 12;
    }
    
    protected int getHiddenRows () {
        
        final int size = this.container.getAvailableRecipesSize();
        return size > 12 ? (size + 3) / 4 - 3 : 0;
    }
    
    private void listenToContainerUpdate (IInventory inventory) {
        
        this.container.onCraftMatrixChanged(inventory);
        this.showRecipes = this.container.canDisplayRecipes();
        
        if (!this.showRecipes) {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }
    }
}