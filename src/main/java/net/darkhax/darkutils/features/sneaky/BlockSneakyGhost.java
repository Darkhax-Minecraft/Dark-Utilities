package net.darkhax.darkutils.features.sneaky;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSneakyGhost extends BlockSneaky {

    public BlockSneakyGhost() {

    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox (IBlockState blockState, World worldIn, BlockPos pos) {

        return null;
    }
}