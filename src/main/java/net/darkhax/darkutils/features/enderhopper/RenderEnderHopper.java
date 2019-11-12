package net.darkhax.darkutils.features.enderhopper;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderEnderHopper extends TileEntityRenderer<TileEntityEnderHopper> {
    
    @Override
    public void render (TileEntityEnderHopper tile, double x, double y, double z, float partialTicks, int destroyStage) {
        
        final BlockState state = tile.getWorld().getBlockState(tile.getPos());
        
        //TODO fix
        if (state.get(BlockEnderHopper.SHOW_BORDER)) {
            
            GlStateManager.pushMatrix();
            renderCubeOverlay(5f, x, y, z, 0xffffff);
            GlStateManager.popMatrix();
        }
    }
    
    private static void renderCubeOverlay (float box, double x, double y, double z, int color) {
        
        @SuppressWarnings("deprecation")
        TextureAtlasSprite icon = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(new ItemStack(Blocks.PURPLE_STAINED_GLASS)).getParticleTexture();
        
        GlStateManager.pushMatrix();
        
        GlStateManager.translated(x + 0.5, y + 0.5, z + 0.5);
        drawOverlay(box, box, box, icon, color, 255);
        GlStateManager.translated(0, 0, 0);
        
        GlStateManager.popMatrix();
    }
    
    public static void drawOverlay (float width, float height, float length, TextureAtlasSprite icon, int color, int alpha) {
        
        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        
        // TOP
        bufferBuilder.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        
        // BOTTOM
        bufferBuilder.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        
        // NORTH
        bufferBuilder.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        
        // SOUTH
        bufferBuilder.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        
        // EAST
        bufferBuilder.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        
        // WEST
        bufferBuilder.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        
        tessellator.draw();
    }
}