package net.darkhax.darkutils.client.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryFactory {
    
    public static class RenderFactoryTNT implements IRenderFactory {
        
        @Override
        public Render createRenderFor (RenderManager manager) {
            
            return new RenderTNTPrimed(manager);
        }
    }
}
