package net.darkhax.darkutils.features.blocks.faketnt;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryTNT implements IRenderFactory<EntityFakeTNT> {
    
    @Override
    public RenderTNTPrimed createRenderFor (RenderManager manager) {
        
        return new RenderTNTPrimed(manager);
    }
}