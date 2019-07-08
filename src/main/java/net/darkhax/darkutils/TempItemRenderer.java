package net.darkhax.darkutils;

import java.awt.Color;
import java.util.Random;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class TempItemRenderer {
    
    /**
     * An internal random reference. The seed should always be set to 41 before using it.
     */
    private static final Random ITEM_RANDOM = new Random();
    
    /**
     * Renders an item and it's effect layer int a GUI. Allows the color of the item to be
     * changed.
     * 
     * @param stack The item to render.
     * @param xPosition The x coordinate to render the item at.
     * @param yPosition The y coordinate to render the item at.
     * @param color The color to render the item. Supports alpha values.
     */
    public static void renderItemAndEffectIntoGUI (ItemStack stack, int xPosition, int yPosition, int color) {
        
        final Minecraft mc = Minecraft.getInstance();
        renderItemAndEffectIntoGUI(mc.getTextureManager(), mc.getItemRenderer(), mc.player, stack, xPosition, yPosition, color);
    }
    
    /**
     * Renders an item into a gui.
     * 
     * @param textureManager The texture manager instance.
     * @param itemRenderer The item renderer.
     * @param entityIn The entity, usually the client player.
     * @param itemIn The item to render.
     * @param x The x coordinate of the item.
     * @param y The y coordinate of the item.
     * @param color The color of the item.
     */
    public static void renderItemAndEffectIntoGUI (TextureManager textureManager, ItemRenderer itemRenderer, @Nullable LivingEntity entityIn, ItemStack itemIn, int x, int y, int color) {
        
        if (!itemIn.isEmpty()) {
            
            itemRenderer.zLevel += 50f;
            
            try {
                
                renderItemModelIntoGUI(textureManager, itemRenderer, itemIn, x, y, color, itemRenderer.getItemModelWithOverrides(itemIn, (World) null, entityIn));
            }
            
            catch (final Exception exception) {
                
                final CrashReport crashreport = CrashReport.makeCrashReport(exception, "Rendering item");
                final CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
                crashreportcategory.addDetail("Item Type", () -> String.valueOf(itemIn.getItem()));
                crashreportcategory.addDetail("Registry Name", () -> String.valueOf(itemIn.getItem().getRegistryName()));
                crashreportcategory.addDetail("Item Damage", () -> String.valueOf(itemIn.getDamage()));
                crashreportcategory.addDetail("Item NBT", () -> String.valueOf(itemIn.getTag()));
                crashreportcategory.addDetail("Item Foil", () -> String.valueOf(itemIn.hasEffect()));
                throw new ReportedException(crashreport);
            }
            
            itemRenderer.zLevel -= 50.0F;
        }
    }
    
    /**
     * Renders an Item into a GUI.
     * 
     * @param textureManager The texture manager instance.
     * @param itemRenderer The item renderer.
     * @param stack The item to render.
     * @param x The x coordinate of the item.
     * @param y The y coordinate of the item.
     * @param color The color of the item.
     * @param bakedmodel The model of the item.
     */
    @SuppressWarnings("deprecation")
    public static void renderItemModelIntoGUI (TextureManager textureManager, ItemRenderer itemRenderer, ItemStack stack, int x, int y, int color, IBakedModel bakedmodel) {
        
        GlStateManager.pushMatrix();
        textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlphaTest();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        setupGuiTransform(x, y, bakedmodel.isGui3d(), itemRenderer.zLevel);
        bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
        renderItem(itemRenderer, stack, bakedmodel, color);
        GlStateManager.disableAlphaTest();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }
    
    /**
     * Sets up the transforms and scale for rendering an item in a gui.
     * 
     * @param xPosition The x coord of the item.
     * @param yPosition The y coord of the item.
     * @param isGui3d Whether or not the gui is 3d.
     * @param zLevel The zlevel of the item.
     */
    public static void setupGuiTransform (int xPosition, int yPosition, boolean isGui3d, float zLevel) {
        
        GlStateManager.translatef(xPosition, yPosition, 100.0F + zLevel);
        GlStateManager.translatef(8.0F, 8.0F, 0.0F);
        GlStateManager.scalef(1.0F, -1.0F, 1.0F);
        GlStateManager.scalef(16.0F, 16.0F, 16.0F);
        
        if (isGui3d) {
            
            GlStateManager.enableLighting();
        }
        
        else {
            
            GlStateManager.disableLighting();
        }
        
    }
    
    /**
     * Renders an item with an item renderer. Will not render if the item is empty. It also
     * offsets the item.
     * 
     * @param itemRenderer The item renderer.
     * @param model The model to render.
     * @param color The color for the model.
     * @param stack The ItemStack instance.
     */
    public static void renderItem (ItemRenderer itemRenderer, ItemStack stack, IBakedModel model, int color) {
        
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            
            renderModel(itemRenderer, model, color, stack);
            
            GlStateManager.popMatrix();
        }
    }
    
    /**
     * Renders the actual quads of an item using an ItemRenderer.
     * 
     * @param itemRenderer The item renderer.
     * @param model The model to render.
     * @param color The color for the model.
     * @param stack The ItemStack instance.
     */
    public static void renderModel (ItemRenderer itemRenderer, IBakedModel model, int color, ItemStack stack) {
        
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
        
        for (final Direction direction : Direction.values()) {
            
            ITEM_RANDOM.setSeed(42L);
            itemRenderer.renderQuads(bufferbuilder, model.getQuads((BlockState) null, direction, ITEM_RANDOM), color, stack);
        }
        
        ITEM_RANDOM.setSeed(42L);
        itemRenderer.renderQuads(bufferbuilder, model.getQuads((BlockState) null, (Direction) null, ITEM_RANDOM), color, stack);
        tessellator.draw();
    }
    
    public static void drawModalRectWithCustomSizedTexture (int x, int y, int z, float u, float v, int width, int height, float textureWidth, float textureHeight, int color) {

        drawModalRectWithCustomSizedTexture(x, y, z, u, v, width, height, textureWidth, textureHeight, (color >> 16) & 0xFF, (color >> 8) & 0xFF, (color >> 0) & 0xFF, (color >> 24) & 0xff);
    }
    
    public static void drawModalRectWithCustomSizedTexture (int x, int y, int z, float u, float v, int width, int height, float textureWidth, float textureHeight, int red, int green, int blue, int alpha) {
        
        final float widthRatio = 1.0F / textureWidth;
        final float heightRatio = 1.0F / textureHeight;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(x, y + height, z).tex(u * widthRatio, (v + height) * heightRatio).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x + width, y + height, z).tex((u + width) * widthRatio, (v + height) * heightRatio).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x + width, y, z).tex((u + width) * widthRatio, v * heightRatio).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x, y, z).tex(u * widthRatio, v * heightRatio).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }
}