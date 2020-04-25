package net.darkhax.darkutils.features.flatblocks.collision;

import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CollisionEffectAnchor implements CollisionEffect {
    
    @Override
    public void apply (BlockState state, World world, BlockPos pos, Entity entity) {
        
        final Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);
        
        if (!entity.isSneaking()) {
            
            if (entity instanceof LivingEntity && entity.isNonBoss()) {
                
                final BlockPos offset = pos.offset(direction.getOpposite(), 5);
                final LivingEntity living = (LivingEntity) entity;
                living.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(offset.getX() + 0.5f, offset.getY() + 1.5f, offset.getZ() + 0.5f));
            }
            
            entity.setPosition(pos.getX() + 0.5f, pos.getY() + 0.0625D, pos.getZ() + 0.5f);
        }
    }
}