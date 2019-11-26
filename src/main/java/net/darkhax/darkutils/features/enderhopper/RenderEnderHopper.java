package net.darkhax.darkutils.features.enderhopper;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.animation.TileEntityRendererFast;

@OnlyIn(Dist.CLIENT)
public class RenderEnderHopper extends TileEntityRendererFast<TileEntityEnderHopper> {
    
    @Override
    public void renderTileEntityFast (TileEntityEnderHopper tile, double x, double y, double z, float partialTicks, int destroyStage, BufferBuilder buffer) {
        
        final BlockState state = tile.getWorld().getBlockState(tile.getPos());
        
        if (state.getBlock() instanceof BlockEnderHopper && state.get(BlockEnderHopper.SHOW_BORDER)) {
            
            GlStateManager.pushMatrix();
            renderCubeOverlay(9f + 0.001f, x, y, z, 0xffffff, buffer);
            GlStateManager.popMatrix();
        }
    }
    
    private static TextureAtlasSprite icon;
    
    private static void renderCubeOverlay (float box, double x, double y, double z, int color, BufferBuilder buffer) {
        
        if (icon == null) {
            
            icon = Minecraft.getInstance().getTextureMap().getSprite(new ResourceLocation("minecraft", "block/nether_portal"));
        }
        
        buffer.setTranslation(x + 0.5, y + 0.5, z + 0.5);
        drawOverlay(box, box, box, icon, color, 255, buffer);
    }
    
    private static Map<Direction, Renderable> directionMap = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, (buffer, width, height, length, icon, red, green, blue, alpha, light1, light2) -> {
            buffer.pos(-(width / 2), height / 2, -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, height / 2, -(length / 2)).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, height / 2, length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), height / 2, length / 2).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        });
        map.put(Direction.DOWN, (buffer, width, height, length, icon, red, green, blue, alpha, light1, light2) -> {
            buffer.pos(-(width / 2), -(height / 2), length / 2).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, -(height / 2), length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, -(height / 2), -(length / 2)).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), -(height / 2), -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        });
        map.put(Direction.NORTH, (buffer, width, height, length, icon, red, green, blue, alpha, light1, light2) -> {
            buffer.pos(width / 2, -(height / 2), length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), -(height / 2), length / 2).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), height / 2, length / 2).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, height / 2, length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        });
        map.put(Direction.SOUTH, (buffer, width, height, length, icon, red, green, blue, alpha, light1, light2) -> {
            buffer.pos(-(width / 2), -(height / 2), -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, -(height / 2), -(length / 2)).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, height / 2, -(length / 2)).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), height / 2, -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        });
        map.put(Direction.EAST, (buffer, width, height, length, icon, red, green, blue, alpha, light1, light2) -> {
            buffer.pos(-(width / 2), height / 2, -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), height / 2, length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), -(height / 2), length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(-(width / 2), -(height / 2), -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
        });
        map.put(Direction.WEST, (buffer, width, height, length, icon, red, green, blue, alpha, light1, light2) -> {
            buffer.pos(width / 2, -(height / 2), -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, -(height / 2), length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMinV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, height / 2, length / 2).color(red, green, blue, alpha).tex(icon.getMaxU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
            buffer.pos(width / 2, height / 2, -(length / 2)).color(red, green, blue, alpha).tex(icon.getMinU(), icon.getMaxV()).lightmap(light1, light2).endVertex();
        });
    });
    
    public static void drawOverlay (float width, float height, float length, TextureAtlasSprite icon, int color, int alpha, BufferBuilder buffer) {
        
        final Direction[] directions = Direction.getFacingDirections(Minecraft.getInstance().renderViewEntity);
        
        for (final Direction direction : directions) {
            final Renderable renderable = directionMap.get(direction.getAxis().isHorizontal() ? direction : direction.getOpposite());
            renderable.render(buffer, width, height, length, icon, color >> 16 & 255, color >> 8 & 255, color & 255, alpha, 200, 200);
        }
    }
    
    private interface Renderable {
        
        void render (BufferBuilder buffer, float width, float height, float length, TextureAtlasSprite icon, int red, int blue, int green, int alpha, int light1, int light2);
    }
}