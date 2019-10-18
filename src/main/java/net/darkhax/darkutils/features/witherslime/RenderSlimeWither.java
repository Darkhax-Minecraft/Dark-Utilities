package net.darkhax.darkutils.features.witherslime;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSlimeWither extends SlimeRenderer {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("darkutils", "textures/entity/wither_slime.png");
    
    public RenderSlimeWither(EntityRendererManager renderManagerIn) {
        
        super(renderManagerIn);
    }
    
    @Override
    protected ResourceLocation getEntityTexture (SlimeEntity entity) {
        
        return TEXTURE;
    }
}