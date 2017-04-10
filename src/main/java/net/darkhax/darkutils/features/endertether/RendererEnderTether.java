package net.darkhax.darkutils.features.endertether;

import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RendererEnderTether extends TileEntitySpecialRenderer<TileEntityEnderTether> {

    @Override
    public void renderTileEntityAt (TileEntityEnderTether te, double x, double y, double z, float partialTicks, int destroyStage) {

        if (!te.showBorder)
            return;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(25.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        RenderUtils.translateAgainstPlayer(te.getPos(), false);
        // drawSelectionBoundingBox
        RenderGlobal.drawSelectionBoundingBox(te.area, 1f, 0f, 1f, 0.5f);

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
