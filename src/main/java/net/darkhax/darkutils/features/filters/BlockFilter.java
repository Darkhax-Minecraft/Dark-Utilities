package net.darkhax.darkutils.features.filters;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockFilter extends Block {
    
    public static final VoxelShape EMPTY = Block.makeCuboidShape(0.0D, 0.0D, 0.00D, 0.0D, 0.0D, 0.0D);
    public static final Properties BLOCK_PROPERTIES = Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(3f, 10f).setSuffocates( (a, b, c) -> false);
    private final IFilterTest filter;
    
    public BlockFilter(IFilterTest filter) {
        
        this(filter, BLOCK_PROPERTIES);
    }
    
    public BlockFilter(IFilterTest filter, Properties properties) {
        
        super(properties);
        this.filter = filter;
        
        BlockState defaultState = this.stateContainer.getBaseState();
        defaultState = defaultState.with(BlockStateProperties.POWERED, false);
        defaultState = defaultState.with(BlockStateProperties.INVERTED, false);
        
        this.setDefaultState(defaultState);
    }
    
    public IFilterTest getFilter (BlockState state, IBlockReader world, BlockPos pos) {
        
        return this.filter;
    }
    
    public boolean shouldInvertFilter (BlockState state, IBlockReader world, BlockPos pos) {
        
        return state.get(BlockStateProperties.INVERTED);
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.INVERTED);
    }
    
    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        
        BlockState placedState = super.getStateForPlacement(context);
        placedState = placedState.with(BlockStateProperties.POWERED, context.getWorld().isBlockPowered(context.getPos()));
        
        return placedState;
    }
    
    @Override
    public ActionResultType onBlockActivated (BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        
        if (player.isSneaking()) {
            
            if (!world.isRemote) {
                
                world.setBlockState(pos, state.with(BlockStateProperties.INVERTED, !state.get(BlockStateProperties.INVERTED)), 3);
                world.playEvent(1008, pos, 0);
            }
            
            return ActionResultType.SUCCESS;
        }
        
        return super.onBlockActivated(state, world, pos, player, handIn, hit);
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
        
        if (context.getEntity() != null && !state.get(BlockStateProperties.POWERED)) {
            
            final boolean filterMatch = this.getFilter(state, world, pos).test(state, pos, world, context.getEntity());
            
            if (this.shouldInvertFilter(state, world, pos) ? !filterMatch : filterMatch) {
                
                return EMPTY;
            }
        }
        
        return super.getCollisionShape(state, world, pos, context);
    }
}