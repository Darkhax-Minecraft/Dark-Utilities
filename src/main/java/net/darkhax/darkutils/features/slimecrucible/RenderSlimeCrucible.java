package net.darkhax.darkutils.features.slimecrucible;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
    
    private void renderCustomName (TileEntitySlimeCrucible tile, double x, double y, double z) {
        
        if (tile.hasCustomName()) {
            this.setLightmapDisabled(true);
            this.drawNameplate(tile, tile.getDisplayName().getFormattedText(), x, y + tile.getEntityHeightOffset() + 0.25, z, 12);
            this.setLightmapDisabled(false);
        }
    }
    
    private void renderMob (TileEntitySlimeCrucible tile, double posX, double posY, double posZ, float partialTicks) {
        
        final SlimeEntity entity = tile.getContainedSlime(tile.getWorld());
        
        if (entity != null) {
            
            final float scale = 1.5f;
            
            GlStateManager.scalef(scale, scale, scale);
            GlStateManager.rotatef(tile.getSideToFace().getHorizontalAngle(), 0, 1, 0);
            
            final float f2 = MathHelper.lerp(partialTicks, tile.getPrevSquishFactor(), tile.getSquishFactor()) / 4;
            final float f3 = 1F / (f2 + 1.0F);
            GlStateManager.scalef(f3, 1.0F / f3, f3);
            
            Minecraft.getInstance().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
        }
    }
}