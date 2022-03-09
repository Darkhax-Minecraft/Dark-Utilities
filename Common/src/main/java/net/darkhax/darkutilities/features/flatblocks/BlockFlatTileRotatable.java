package net.darkhax.darkutilities.features.flatblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

public class BlockFlatTileRotatable extends BlockFlatTile {

    public BlockFlatTileRotatable() {

        this(BLOCK_PROPERTIES, null);
    }

    public BlockFlatTileRotatable(CollisionEffect collisionEffect) {

        this(BLOCK_PROPERTIES, collisionEffect);
    }

    public BlockFlatTileRotatable(Properties properties, CollisionEffect collisionEffect) {

        super(properties, collisionEffect);

        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (player.isCrouching()) {

            world.setBlockAndUpdate(pos, this.rotate(state, Rotation.CLOCKWISE_90));
            return InteractionResult.SUCCESS;
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        BlockState placedState = super.getStateForPlacement(context);

        if (placedState != null) {

            for (final Direction facing : context.getNearestLookingDirections()) {

                if (facing.getAxis().isHorizontal()) {

                    return placedState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
                }
            }
        }

        return placedState;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {

        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rot.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {

        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }
}
