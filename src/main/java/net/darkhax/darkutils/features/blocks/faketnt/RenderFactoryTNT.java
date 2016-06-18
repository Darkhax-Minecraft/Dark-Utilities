package net.darkhax.darkutils.features.blocks.faketnt;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFactoryTNT implements IRenderFactory<EntityFakeTNT> {
    
    @Override
    public RenderTNTPrimed createRenderFor (RenderManager manager) {
        
        return new RenderTNTPrimed(manager);
    }
}