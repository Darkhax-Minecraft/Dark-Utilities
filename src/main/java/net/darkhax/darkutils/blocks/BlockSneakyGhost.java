package net.darkhax.darkutils.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSneakyGhost extends BlockSneaky {
    
    public BlockSneakyGhost() {
        
        this.setUnlocalizedName("darkutils.sneaky.ghost");
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox (World worldIn, BlockPos pos, IBlockState state) {
        
        return null;
    }
}