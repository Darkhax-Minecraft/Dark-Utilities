package net.darkhax.darkutils.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;

import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.Entity;

public class TileEntityRendererSlimeThing extends TileEntityRenderer<TileEntitySlimeCrucible> {

    @Override
    public void render (TileEntitySlimeCrucible tile, double x, double y, double z, float delta, int destroyStage) {

        super.render(tile, x, y, z, delta, destroyStage);

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
        this.renderMob(tile, x, y, z, delta);
        GlStateManager.popMatrix();
    }

    private void renderMob (TileEntitySlimeCrucible tile, double posX, double posY, double posZ, float partialTicks) {

        final Entity entity = tile.getContainedSlime(tile.getWorld());

        if (entity != null) {

            final float scale = 1.5f;

            GlStateManager.translatef(0.0F, (float) tile.getEntityHeightOffset(), 0.0F);
            GlStateManager.scalef(scale, scale, scale);
            GlStateManager.rotatef(tile.getSideToFace().getHorizontalAngle(), 0, 1, 0);
            entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            Minecraft.getInstance().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
        }
    }
}