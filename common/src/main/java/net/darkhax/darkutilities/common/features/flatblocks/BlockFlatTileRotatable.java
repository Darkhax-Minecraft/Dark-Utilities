package net.darkhax.darkutilities.common.features.flatblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockFlatTileRotatable extends BlockFlatTile {

    public static Function<Properties, Block> of(CollisionEffect effect) {
        return p -> new BlockFlatTileRotatable(p, effect);
    }

    public BlockFlatTileRotatable(Properties properties, CollisionEffect collisionEffect) {

        super(properties, collisionEffect);

        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isCrouching()) {
            level.setBlockAndUpdate(pos, this.rotate(state, Rotation.CLOCKWISE_90));
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
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
