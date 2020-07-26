package net.darkhax.darkutils.features.grates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockItemGrate extends Block implements IBucketPickupHandler, ILiquidContainer {
    
    public static final VoxelShape SHAPE = Block.makeCuboidShape(0, 15, 0, 16, 16, 16);
    
    public BlockItemGrate(Properties properties) {
        
        super(properties);
        
        BlockState defaultState = this.stateContainer.getBaseState();
        defaultState = defaultState.with(BlockStateProperties.POWERED, false);
        defaultState = defaultState.with(BlockStateProperties.WATERLOGGED, false);
        this.setDefaultState(defaultState);
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.WATERLOGGED);
    }
    
    @Override
    public boolean canContainFluid (IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        
        return true;
    }
    
    @Override
    public boolean receiveFluid (IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        
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
    public FluidState getFluidState (BlockState state) {
        
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
        
        final FluidState preExistingFluidState = context.getWorld().getFluidState(context.getPos());
        BlockState placedState = super.getStateForPlacement(context);
        placedState = placedState.with(BlockStateProperties.POWERED, context.getWorld().isBlockPowered(context.getPos()));
        placedState = placedState.with(BlockStateProperties.WATERLOGGED, preExistingFluidState.getFluid() == Fluids.WATER);
        return placedState;
    }
    
    @Override
    public void neighborChanged (BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        
        final boolean isBlockPowered = worldIn.isBlockPowered(pos);
        
        if (!worldIn.isRemote && state.get(BlockStateProperties.POWERED) != isBlockPowered) {
            
            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5f);
            worldIn.setBlockState(pos, state.with(BlockStateProperties.POWERED, isBlockPowered));
        }
        
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }
    
    @Override
    public boolean canSpawnInBlock () {
        
        return true;
    }
    
    @Override
    public VoxelShape getCollisionShape (BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        
        if (context.getEntity() instanceof ItemEntity && !state.get(BlockStateProperties.POWERED)) {
            
            return VoxelShapes.empty();
        }
        
        return SHAPE;
    }
    
    @Override
    public VoxelShape getShape (BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        
        return SHAPE;
    }
}