package net.darkhax.darkutils.features.enderhopper;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderEnderHopper extends TileEntityRenderer<TileEntityEnderHopper> {
    
    @Override
    public void render (TileEntityEnderHopper tile, double x, double y, double z, float partialTicks, int destroyStage) {
        
        final BlockState state = tile.getWorld().getBlockState(tile.getPos());
        
        // TODO fix
        if (state.get(BlockEnderHopper.SHOW_BORDER)) {
            
        }
    }
}