package net.darkhax.darkutilities.features.filters;

import net.darkhax.bookshelf.api.block.IBindRenderLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

public class BlockEntityFilter extends Block implements IBindRenderLayer {

    public static final Properties BLOCK_PROPERTIES = Properties.of().mapColor(MapColor.WOOD).strength(3f, 10f).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).noOcclusion();

    private final Predicate<Entity> filter;

    public BlockEntityFilter(Predicate<Entity> filter) {

        this(filter, BLOCK_PROPERTIES);
    }

    public BlockEntityFilter(Predicate<Entity> filter, Properties properties) {

        super(properties);
        this.filter = filter;

        BlockState defaultState = this.defaultBlockState();
        defaultState = defaultState.setValue(BlockStateProperties.POWERED, false);
        defaultState = defaultState.setValue(BlockStateProperties.INVERTED, false);

        this.registerDefaultState(defaultState);
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
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (player.isCrouching()) {

            if (!world.isClientSide) {

                world.setBlock(pos, state.setValue(BlockStateProperties.INVERTED, !state.getValue(BlockStateProperties.INVERTED)), 3);
                world.levelEvent(1008, pos, 0);
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        final boolean isBlockPowered = world.hasNeighborSignal(pos);

        if (!world.isClientSide && state.getValue(BlockStateProperties.POWERED) != isBlockPowered) {

            world.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.5f);
            world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWERED, isBlockPowered));
        }

        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
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
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {

        return state.getFluidState().isEmpty();
    }

    @Override
    public RenderType getRenderLayerToBind() {
        
        return RenderType.translucent();
    }
}