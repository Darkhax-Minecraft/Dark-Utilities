package net.darkhax.darkutils.features.filters;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface IFilterTest {
    
    boolean test (BlockState state, BlockPos pos, IBlockReader world, Entity entity);
}
