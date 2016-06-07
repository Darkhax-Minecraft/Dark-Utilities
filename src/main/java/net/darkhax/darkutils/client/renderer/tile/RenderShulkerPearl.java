package net.darkhax.darkutils.client.renderer.tile;

import net.darkhax.darkutils.tileentity.TileEntityShulkerPearl;
import net.minecraft.client.model.ModelShulkerBullet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.util.ResourceLocation;

public class RenderShulkerPearl extends TileEntitySpecialRenderer<TileEntityShulkerPearl> {
    
    private static final ResourceLocation SHULKER_SPARK_TEXTURE = new ResourceLocation("textures/entity/shulker/spark.png");
    private final ModelShulkerBullet model = new ModelShulkerBullet();
    
    private float rotLerp (float p_188347_1_, float p_188347_2_, float p_188347_3_) {
        
        float f;
        
        for (f = p_188347_2_ - p_188347_1_; f < -180.0F; f += 360.0F)
            ;
            
        while (f >= 180.0F)
            f -= 360.0F;
            
        return p_188347_1_ + p_188347_3_ * f;
    }
    
    @Override
    public void renderTileEntityAt (TileEntityShulkerPearl te, double x, double y, double z, float partialTicks, int destroyStage) {
        
        final EntityShulkerBullet entity = te.getPearl();
        
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5, (float) y + 0.5F, (float) z + 0.5);
        GlStateManager.disableLighting();
        final float f3 = 0.03125F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.bindTexture(SHULKER_SPARK_TEXTURE);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, partialTicks, partialTicks, f3);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.scale(1.5F, 1.5F, 1.5F);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, partialTicks, partialTicks, f3);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
