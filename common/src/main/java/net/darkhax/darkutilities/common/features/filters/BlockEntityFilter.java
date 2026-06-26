package net.darkhax.darkutilities.common.features.filters;

import net.darkhax.bookshelf.common.api.block.IBlockHooks;
import net.darkhax.darkutilities.common.Content;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BlockEntityFilter extends Block implements IBlockHooks {

    public static final UnaryOperator<BlockBehaviour.Properties> PROPERTIES = p -> p.mapColor(MapColor.WOOD).strength(3f, 10f).isSuffocating((_, _, _) -> false).isViewBlocking((_, _, _) -> false).noOcclusion();

    private final Predicate<Entity> filter;

    public static Function<Properties, Block> of(Predicate<Entity> filter) {
        return p -> new BlockEntityFilter(filter, p);
    }

    public BlockEntityFilter(Predicate<Entity> filter, Properties properties) {
        super(properties);
        this.filter = filter;
        BlockState defaultState = this.defaultBlockState();
        defaultState = defaultState.setValue(BlockStateProperties.POWERED, false);
        defaultState = defaultState.setValue(BlockStateProperties.INVERTED, false);
        this.registerDefaultState(defaultState);
        Content.CUTOUT.add(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.INVERTED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState placedState = super.getStateForPlacement(context);
        if (placedState != null) {
            placedState = placedState.setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
        }
        return placedState;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isCrouching()) {
            if (!world.isClientSide()) {
                world.setBlock(pos, state.setValue(BlockStateProperties.INVERTED, !state.getValue(BlockStateProperties.INVERTED)), 3);
                world.levelEvent(1008, pos, 0);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, world, pos, player, hitResult);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        final boolean isBlockPowered = level.hasNeighborSignal(pos);
        if (!level.isClientSide() && state.getValue(BlockStateProperties.POWERED) != isBlockPowered) {
            level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.5f);
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWERED, isBlockPowered));
        }
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
    }

    @Override
    public boolean isPossibleToRespawnInThis(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (!state.getValue(BlockStateProperties.POWERED) && context instanceof EntityCollisionContext entityContext) {
            final Entity entity = entityContext.getEntity();
            if (entity != null) {
                final boolean filterMatch = this.filter.test(entity);
                if (state.getValue(BlockStateProperties.INVERTED) != filterMatch) {
                    return Shapes.empty();
                }
            }
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public boolean skipRendering(BlockState ourState, BlockState neighborState, Direction side) {
        return neighborState.is(this);
    }

    @Override
    protected int getLightDampening(BlockState state) {
        return 15;
    }
}