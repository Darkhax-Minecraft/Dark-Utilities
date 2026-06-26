package net.darkhax.darkutilities.common.features.flatblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BlockFlatTile extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
    public static final UnaryOperator<BlockBehaviour.Properties> PROPERTIES = p -> p.mapColor(MapColor.DEEPSLATE).strength(2f, 10f).noCollision().sound(SoundType.DEEPSLATE_TILES);
    public static final VoxelShape BOUNDS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    private final CollisionEffect collisionEffect;

    public static Function<BlockBehaviour.Properties, Block> of(CollisionEffect effect) {
        return p -> new BlockFlatTile(p, effect);
    }

    public BlockFlatTile(Properties properties, CollisionEffect collisionEffect) {
        super(properties);
        this.collisionEffect = collisionEffect;
        BlockState defaultState = this.defaultBlockState();
        defaultState = defaultState.setValue(BlockStateProperties.POWERED, false);
        defaultState = defaultState.setValue(BlockStateProperties.WATERLOGGED, false);
        defaultState = defaultState.setValue(HIDDEN, false);
        defaultState = defaultState.setValue(LOCKED, false);
        this.registerDefaultState(defaultState);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (this.collisionEffect != null && !state.getValue(BlockStateProperties.POWERED) && entity.getY() <= (double) pos.getY() + 0.4d) {
            this.collisionEffect.onCollision(state, level, pos, entity);
            if (state.getValue(HIDDEN)) {
                level.levelEvent(3002, pos, -1);
                level.setBlockAndUpdate(pos, state.setValue(HIDDEN, false));
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.WATERLOGGED, HIDDEN, LOCKED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BOUNDS;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context.isAbove(BOUNDS, pos, true) && !context.isDescending()) {
            return BOUNDS;
        }
        return Shapes.empty();
    }

    @Override
    public boolean isPossibleToRespawnInThis(BlockState state) {
        return true;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).is(Items.REDSTONE_TORCH)) {
            boolean oldValue = state.getValue(LOCKED);
            level.setBlock(pos, state.setValue(LOCKED, !oldValue), 2);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(!oldValue ? DustParticleOptions.REDSTONE : ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.2f / 16, pos.getZ() + 0.5, 16, 0.25, 0, 0.25, 0);
                serverLevel.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3f, !oldValue ? 0.6F : 0.5F);
                if (player instanceof ServerPlayer sPlayer) {
                    sPlayer.sendSystemMessage(Component.translatable("block.darkutils.plate." + (!oldValue ? "locked" : "unlocked"), state.getBlock().getName()), true);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        final FluidState preExistingFluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockState placedState = super.getStateForPlacement(context);
        if (placedState != null) {
            placedState = placedState.setValue(BlockStateProperties.WATERLOGGED, preExistingFluidState.is(Fluids.WATER));
            placedState = placedState.setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
        }
        return placedState;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        if (!level.isClientSide() && !state.getValue(LOCKED)) {
            level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, level.hasNeighborSignal(pos)), 2);
        }
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
    }

    @FunctionalInterface
    public interface CollisionEffect {
        void onCollision(BlockState state, Level world, BlockPos pos, Entity entity);
    }
}