package net.darkhax.darkutils.features.flatblocks.collision;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface CollisionEffect {

    /**
     * This functional interface is used by the flat tile blocks in this mod. They are applied
     * to an entity when that entity collides with the block.
     *
     * @param state The state of the block.
     * @param world Instance of the world.
     * @param pos The current block position.
     * @param entity The entity that collided with the block.
     */
    void apply (BlockState state, World world, BlockPos pos, Entity entity);
}