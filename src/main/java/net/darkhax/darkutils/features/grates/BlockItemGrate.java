package net.darkhax.darkutils.features.grates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockItemGrate extends Block {
    
    public static final VoxelShape SHAPE = Block.makeCuboidShape(0, 15, 0, 16, 16, 16);
    
    public BlockItemGrate(Properties properties) {
        
        super(properties);
        
        BlockState defaultState = this.stateContainer.getBaseState();
        defaultState = defaultState.with(BlockStateProperties.POWERED, false);
        
        this.setDefaultState(defaultState);
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.POWERED);
    }
    
    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        
        BlockState placedState = super.getStateForPlacement(context);
        placedState = placedState.with(BlockStateProperties.POWERED, context.getWorld().isBlockPowered(context.getPos()));
        
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
    public boolean isSolid (BlockState state) {
        
        return false;
    }
    
    @Override
    public boolean causesSuffocation (BlockState state, IBlockReader world, BlockPos pos) {
        
        return false;
    }
    
    @Override
    public boolean isNormalCube (BlockState state, IBlockReader world, BlockPos pos) {
        
        return false;
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
    
    @Override
    public BlockRenderLayer getRenderLayer () {
        
        return BlockRenderLayer.CUTOUT;
    }
}