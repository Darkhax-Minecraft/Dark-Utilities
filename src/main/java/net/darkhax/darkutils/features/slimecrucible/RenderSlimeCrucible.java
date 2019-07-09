package net.darkhax.darkutils.features.slimecrucible;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A tile entity renderer for slime crucibles.
 */
@OnlyIn(Dist.CLIENT)
public class RenderSlimeCrucible extends TileEntityRenderer<TileEntitySlimeCrucible> {
    
    @Override
    public void render (TileEntitySlimeCrucible tile, double x, double y, double z, float partialTicks, int destroyStage) {
        
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) x + 0.5F, (float) (y + tile.getEntityHeightOffset()), (float) z + 0.5F);
        this.renderCustomName(tile, x, y, z);
        this.renderMob(tile, x, y, z, partialTicks);
        GlStateManager.popMatrix();
    }
    
    /**
     * Renders a nameplate if the entity has a custom name.
     * 
     * @param tile The tile being rendered.
     * @param x The X coordinate of the tile.
     * @param y The Y coordinate of the tile.
     * @param z The Z coordinate of the tile.
     */
    private void renderCustomName (TileEntitySlimeCrucible tile, double x, double y, double z) {
        
        if (tile.hasCustomName()) {
            this.setLightmapDisabled(true);
            this.drawNameplate(tile, tile.getDisplayName().getFormattedText(), x, y + tile.getEntityHeightOffset() + 0.25, z, 12);
            this.setLightmapDisabled(false);
        }
    }
    
    /**
     * Renders the slime entity into the tile.
     * 
     * @param tile The tile being rendered.
     * @param posX The X coordinate of the tile.
     * @param posY The Y coordinate of the tile.
     * @param posZ The Z coordinate of the tile.
     * @param partialTicks The amount of ticks that have passed since the last frame.
     */
    private void renderMob (TileEntitySlimeCrucible tile, double posX, double posY, double posZ, float partialTicks) {
        
        final SlimeEntity entity = tile.getContainedSlime(tile.getWorld());
        
        if (entity != null) {
            
            final float scale = 1.5f;
            
            GlStateManager.scalef(scale, scale, scale);
            GlStateManager.rotatef(tile.getSideToFace().getHorizontalAngle(), 0, 1, 0);
            
            final float elapsedSquish = MathHelper.lerp(partialTicks, tile.getPrevSquishFactor(), tile.getSquishFactor()) / 4;
            final float squishOffset = 1F / (elapsedSquish + 1.0F);
            GlStateManager.scalef(squishOffset, 1.0F / squishOffset, squishOffset);
            
            Minecraft.getInstance().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
        }
    }
}