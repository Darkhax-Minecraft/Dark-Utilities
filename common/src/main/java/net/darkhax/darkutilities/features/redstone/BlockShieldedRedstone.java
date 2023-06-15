package net.darkhax.darkutilities.features.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;

public class BlockShieldedRedstone extends Block {

    public BlockShieldedRedstone() {

        super(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).strength(3.5f));
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public boolean isSignalSource(BlockState state) {

        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {

        return side.getOpposite() == state.getValue(BlockStateProperties.FACING) ? 15 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        return super.getStateForPlacement(context).setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }
}