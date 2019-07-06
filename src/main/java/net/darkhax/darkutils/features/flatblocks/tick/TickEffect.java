package net.darkhax.darkutils.features.flatblocks.tick;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface TickEffect {
    
    /**
     * This functional interface is used by the flat tile blocks in this mod. This tick effect
     * will be applied based on the specified tick rate.
     *
     * @param state The current blocks tate.
     * @param world The world the block is in.
     * @param pos The position of the block.
     */
    void apply (BlockState state, World world, BlockPos pos);
}