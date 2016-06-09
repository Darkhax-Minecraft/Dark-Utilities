package net.darkhax.darkutils.client.renderer;

import net.darkhax.darkutils.entity.EntityFakeTNT;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryFactory {
    
    public static class RenderFactoryTNT implements IRenderFactory<EntityFakeTNT> {
        
        @Override
        public RenderTNTPrimed createRenderFor (RenderManager manager) {
            
            return new RenderTNTPrimed(manager);
        }
    }
}
