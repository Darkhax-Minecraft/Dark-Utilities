package net.darkhax.darkutils.features.glass;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockDarkGlass extends GlassBlock {

    public BlockDarkGlass() {
        
        super(Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS));
    }
    
    @Override
    public int getOpacity(BlockState state, IBlockReader world, BlockPos pos) {
        
        return world.getMaxLightLevel();
    }
    
    @Override
    public BlockRenderLayer getRenderLayer() {
        
        return BlockRenderLayer.TRANSLUCENT;
     }
}