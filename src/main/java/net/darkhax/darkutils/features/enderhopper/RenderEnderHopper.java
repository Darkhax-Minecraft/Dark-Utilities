package net.darkhax.darkutils.features.enderhopper;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.animation.TileEntityRendererFast;

@OnlyIn(Dist.CLIENT)
public class RenderEnderHopper extends TileEntityRendererFast<TileEntityEnderHopper> {
    
    @Override
    public void renderTileEntityFast(TileEntityEnderHopper tile, double x, double y, double z, float partialTicks, int destroyStage, BufferBuilder buffer) {
        final BlockState state = tile.getWorld().getBlockState(tile.getPos());
    
        if (state.get(BlockEnderHopper.SHOW_BORDER)) {
        
            GlStateManager.pushMatrix();
            // 0.00001 to avoid z-fighting
            renderCubeOverlay(9f - 0.001f, x, y, z, 0xffffff , buffer);
            GlStateManager.popMatrix();
        }
    }
    
    private static void renderCubeOverlay (float box, double x, double y, double z, int color, BufferBuilder buffer) {
        
        @SuppressWarnings("deprecation") 
        TextureAtlasSprite icon = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(new ItemStack(Blocks.PURPLE_STAINED_GLASS)).getParticleTexture();
        buffer.setTranslation(x + 0.5, y + 0.5, z + 0.5);
        drawOverlay(box, box, box, icon, color, 255, buffer);
        
    }
    
    public static void drawOverlay (float width, float height, float length, TextureAtlasSprite icon, int color, int alpha, BufferBuilder buffer) {
    
        
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
    
        int light1 = 200;
        int light2 = 200;
    
        // TOP
        buffer.pos(-(width / 2), (height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), (height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), (height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), (height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        
        // BOTTOM
        buffer.pos(-(width / 2), -(height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), -(height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), -(height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), -(height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        
        // NORTH
        buffer.pos((width / 2), -(height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), -(height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), (height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), (height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        
        // SOUTH
        buffer.pos(-(width / 2), -(height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), -(height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), (height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), (height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
    
        // EAST
        buffer.pos(-(width / 2), (height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), (height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), -(height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos(-(width / 2), -(height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
    
        // WEST
        buffer.pos((width / 2), -(height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), -(height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), (height / 2), (length / 2)).color(r, g, b, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        buffer.pos((width / 2), (height / 2), -(length / 2)).color(r, g, b, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
    
    }
}