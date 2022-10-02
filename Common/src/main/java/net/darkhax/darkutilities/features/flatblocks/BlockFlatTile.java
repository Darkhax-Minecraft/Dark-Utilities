package net.darkhax.darkutilities.features.flatblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockFlatTile extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
    public static final Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(3f, 10f).noCollission().sound(SoundType.DEEPSLATE_TILES);
    public static final VoxelShape BOUNDS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    private final CollisionEffect collisionEffect;

    public BlockFlatTile(CollisionEffect collisionEffect) {

        this(BLOCK_PROPERTIES, collisionEffect);
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
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (this.collisionEffect != null && !state.getValue(BlockStateProperties.POWERED) && entity.getY() <= (double) pos.getY() + 0.4d) {

            this.collisionEffect.onCollision(state, world, pos, entity);

            if (state.getValue(HIDDEN)) {

                world.levelEvent(3002, pos, -1);
                world.setBlockAndUpdate(pos, state.setValue(HIDDEN, false));
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
    public boolean isPossibleToRespawnInThis() {

        return true;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (player.getItemInHand(hand).is(Items.REDSTONE_TORCH)) {

            boolean oldValue = state.getValue(LOCKED);
            level.setBlock(pos, state.setValue(LOCKED, !oldValue), 2);

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(!oldValue ? DustParticleOptions.REDSTONE : ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.2f / 16, pos.getZ() + 0.5, 16, 0.25, 0, 0.25, 0);
                serverLevel.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3f, !oldValue ? 0.6F : 0.5F);
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {

        if (state.getValue(BlockStateProperties.WATERLOGGED)) {

            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
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
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        if (!world.isClientSide) {
            if (!state.getValue(LOCKED)) {
                world.setBlock(pos, state.setValue(BlockStateProperties.POWERED, world.hasNeighborSignal(pos)), 2);
            }
        }

        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    @FunctionalInterface
    public interface CollisionEffect {

        void onCollision(BlockState state, Level world, BlockPos pos, Entity entity);
    }
}