package net.darkhax.darkutils.features.flatblocks;

import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockFlatTile extends Block implements IBucketPickupHandler, ILiquidContainer {
    
    public static final Properties BLOCK_PROPERTIES = Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(3f, 10f);
    public static final VoxelShape BOUNDS = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    public static final VoxelShape EFFECT_BOUNDS = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    
    private final CollisionEffect collisionEffect;
    
    public BlockFlatTile() {
        
        this(null);
    }
    
    public BlockFlatTile(CollisionEffect collisionEffect) {
        
        this(BLOCK_PROPERTIES, collisionEffect);
    }
    
    public BlockFlatTile(Properties properties, CollisionEffect collisionEffect) {
        
        super(properties);
        this.collisionEffect = collisionEffect;
        
        BlockState defaultState = this.stateContainer.getBaseState();
        defaultState = defaultState.with(BlockStateProperties.POWERED, false);
        defaultState = defaultState.with(BlockStateProperties.WATERLOGGED, false);
        
        this.setDefaultState(defaultState);
    }
    
    @Override
    public void onEntityCollision (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (this.collisionEffect != null && !state.get(BlockStateProperties.POWERED) && EFFECT_BOUNDS.getBoundingBox().offset(pos).intersects(entity.getBoundingBox())) {
            
            this.collisionEffect.apply(state, world, pos, entity);
        }
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.WATERLOGGED);
    }
    
    @Override
    public VoxelShape getShape (BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        
        return BOUNDS;
    }
    
    @Override
    public boolean canSpawnInBlock () {
        
        return true;
    }
    
    @Override
    public boolean canContainFluid (IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        
        return true;
    }
    
    @Override
    public boolean receiveFluid (IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
        
        if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            
            if (!worldIn.isRemote()) {
                
                worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }
            
        }
        
        return false;
    }
    
    @Override
    public Fluid pickupFluid (IWorld worldIn, BlockPos pos, BlockState state) {
        
        if (state.get(BlockStateProperties.WATERLOGGED)) {
            
            worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)), 3);
            return Fluids.WATER;
        }
        
        return Fluids.EMPTY;
    }
    
    @Override
    public IFluidState getFluidState (BlockState state) {
        
        return state.get(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
    
    @Override
    public BlockState updatePostPlacement (BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        
        if (stateIn.get(BlockStateProperties.WATERLOGGED)) {
            
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
    
    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        
        final IFluidState preExistingFluidState = context.getWorld().getFluidState(context.getPos());
        
        BlockState placedState = super.getStateForPlacement(context);
        placedState = placedState.with(BlockStateProperties.WATERLOGGED, preExistingFluidState.getFluid() == Fluids.WATER);
        placedState = placedState.with(BlockStateProperties.POWERED, context.getWorld().isBlockPowered(context.getPos()));
        
        return placedState;
    }
    
    @Override
    public BlockRenderLayer getRenderLayer () {
        
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    @Override
    public void neighborChanged (BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        
        if (!worldIn.isRemote) {
            
            worldIn.setBlockState(pos, state.with(BlockStateProperties.POWERED, worldIn.isBlockPowered(pos)));
        }
        
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }
}