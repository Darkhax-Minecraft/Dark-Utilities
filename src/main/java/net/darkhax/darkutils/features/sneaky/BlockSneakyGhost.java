package net.darkhax.darkutils.features.sneaky;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSneakyGhost extends BlockSneaky {

    public BlockSneakyGhost () {

    }

    @Override
    @Deprecated
    public AxisAlignedBB getCollisionBoundingBox (IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {

        return null;
    }
}