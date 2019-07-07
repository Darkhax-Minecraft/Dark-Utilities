package net.darkhax.darkutils.features.slimecrucible;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.darkhax.darkutils.TempItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenSlimeCrucible extends ContainerScreen<ContainerSlimeCrucible> {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("darkutils", "textures/gui/container/slime_crucible.png");
    private final TempItemRenderer tempRenderer = new TempItemRenderer(Minecraft.getInstance().getItemRenderer());
    private float sliderProgress;
    private boolean mouseBeingDragged;
    private int recipeIndexOffset;
    private boolean showRecipes;
    
    public ScreenSlimeCrucible(ContainerSlimeCrucible container, PlayerInventory playerInventory, ITextComponent title) {
        
        super(container, playerInventory, title);
        container.setUpdateListener(this::listenToContainerUpdate);
    }
    
    @Override
    public void render (int mouseX, int mouseY, float partialTicks) {
        
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
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
        this.renderRecipeOutputs(selectionBoxX, selectionBoxY, lastRecipeIndex);
    }
    
    private void renderRecipeOutputButtons (int mouseX, int mouseY, int selectionBoxX, int selectionBoxY, int lastRecipeIndex) {
        
        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
            
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeY = selectionBoxY + recipeRow * 18 + 2;
            
            int textureY = this.ySize;
            
            if (!this.container.getAvailableRecipes().get(i).isValid(this.container.slotInput.getStack(), this.container.getCrucibleType(), 1000f)) {
                
                textureY += 54;
            }
            
            // Render the selected/pressed version of the button.
            else if (i == this.container.getSelectedRecipe()) {
                textureY += 18;
            }
            
            // Render the mouseover/highlighted texture
            else if (mouseX >= recipeX && mouseY >= recipeY && mouseX < recipeX + 16 && mouseY < recipeY + 18) {
                
                textureY += 36;
            }
            
            this.blit(recipeX, recipeY - 1, 0, textureY, 16, 18);
        }
    }
    
    private void renderRecipeOutputs (int selectionBoxX, int selectionBoxY, int lastRecipeIndex) {
        
        RenderHelper.enableGUIStandardItemLighting();
        final List<RecipeSlimeCrafting> list = this.container.getAvailableRecipes();

        for (int i = this.recipeIndexOffset; i < lastRecipeIndex && i < this.container.getAvailableRecipesSize(); i++) {
          
            final int recipeIndex = i - this.recipeIndexOffset;
            final int recipeItemX = selectionBoxX + recipeIndex % 4 * 16;
            final int recipeRow = recipeIndex / 4;
            final int recipeItemY = selectionBoxY + recipeRow * 18 + 2;
            this.tempRenderer.renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), recipeItemX, recipeItemY, 0xff000000);
            
            if (!this.container.getAvailableRecipes().get(i).isValid(this.container.slotInput.getStack(), this.container.getCrucibleType(), 1000f)) {
                
                GlStateManager.color4f(1, 1, 1, 0.3f);
                GlStateManager.disableTexture();   
                GlStateManager.disableDepthTest();
                GlStateManager.enableBlend(); 
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder tes = tessellator.getBuffer();
                tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
                //x & y - position on screen in pixels
                //(the same as you use when drawing GUI
                net.minecraft.client.renderer.ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                
                int x = recipeItemX;
                int y = recipeItemY;
                tes.pos(x, y + 16, itemRenderer.zLevel).endVertex();
                tes.pos(x + 16, y + 16, itemRenderer.zLevel).endVertex();
                tes.pos(x + 16, y, itemRenderer.zLevel).endVertex();
                tes.pos(x, y, itemRenderer.zLevel).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture();
            }
        }
        

        RenderHelper.disableStandardItemLighting();
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
                    
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.BLOCK_SLIME_BLOCK_STEP, 1.0F));
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