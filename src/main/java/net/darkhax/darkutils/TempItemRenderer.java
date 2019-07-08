package net.darkhax.darkutils;

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
    
    private final TextureManager textureManager;
    private final ItemRenderer itemRenderer;
    public float zLevel = 0f;
    
    public TempItemRenderer(ItemRenderer renderer) {
        
        this.itemRenderer = renderer;
        this.textureManager = Minecraft.getInstance().textureManager;
    }
    
    public void renderItemAndEffectIntoGUI (ItemStack stack, int xPosition, int yPosition, int color) {
        
        this.renderItemAndEffectIntoGUI(Minecraft.getInstance().player, stack, xPosition, yPosition, color);
    }
    
    public void renderItemAndEffectIntoGUI (@Nullable LivingEntity entityIn, ItemStack itemIn, int x, int y, int color) {
        
        if (!itemIn.isEmpty()) {
            this.zLevel += 50.0F;
            
            try {
                this.renderItemModelIntoGUI(itemIn, x, y, color, this.itemRenderer.getItemModelWithOverrides(itemIn, (World) null, entityIn));
            }
            catch (final Throwable throwable) {
                final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
                final CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
                crashreportcategory.addDetail("Item Type", () -> {
                    return String.valueOf(itemIn.getItem());
                });
                crashreportcategory.addDetail("Registry Name", () -> String.valueOf(itemIn.getItem().getRegistryName()));
                crashreportcategory.addDetail("Item Damage", () -> {
                    return String.valueOf(itemIn.getDamage());
                });
                crashreportcategory.addDetail("Item NBT", () -> {
                    return String.valueOf(itemIn.getTag());
                });
                crashreportcategory.addDetail("Item Foil", () -> {
                    return String.valueOf(itemIn.hasEffect());
                });
                throw new ReportedException(crashreport);
            }
            
            this.zLevel -= 50.0F;
        }
    }
    
    protected void renderItemModelIntoGUI (ItemStack stack, int x, int y, int color, IBakedModel bakedmodel) {
        
        GlStateManager.pushMatrix();
        this.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        this.textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlphaTest();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.setupGuiTransform(x, y, bakedmodel.isGui3d());
        bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
        this.renderItem(stack, bakedmodel, color);
        GlStateManager.disableAlphaTest();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        this.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        this.textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }
    
    private void setupGuiTransform (int xPosition, int yPosition, boolean isGui3d) {
        
        GlStateManager.translatef(xPosition, yPosition, 100.0F + this.zLevel);
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
    
    public void renderItem (ItemStack stack, IBakedModel model, int color) {
        
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            
            this.renderModel(model, color, stack);
            
            GlStateManager.popMatrix();
        }
    }
    
    private void renderModel (IBakedModel model, int color, ItemStack stack) {
        
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
        final Random random = new Random();
        final long i = 42L;
        
        for (final Direction direction : Direction.values()) {
            random.setSeed(42L);
            this.itemRenderer.renderQuads(bufferbuilder, model.getQuads((BlockState) null, direction, random), color, stack);
        }
        
        random.setSeed(42L);
        this.itemRenderer.renderQuads(bufferbuilder, model.getQuads((BlockState) null, (Direction) null, random), color, stack);
        tessellator.draw();
    }
}
