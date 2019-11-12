package net.darkhax.darkutils.features.enderhopper;

import net.darkhax.bookshelf.util.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockEnderHopper extends Block implements ITileEntityProvider {
    
    private static final Properties PROPERTIES = Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(50.0F, 1200.0F);
    protected static final BooleanProperty SHOW_BORDER = BooleanProperty.create("darkutils_show_border");
    
    private static final VoxelShape SHAPE_DOWN = Block.makeCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_UP = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    private static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    
    public BlockEnderHopper() {
        
        super(PROPERTIES);
        
        BlockState defaultState = this.getDefaultState();
        defaultState = defaultState.with(BlockStateProperties.FACING, Direction.UP);
        defaultState = defaultState.with(BlockStateProperties.ENABLED, true);
        defaultState = defaultState.with(SHOW_BORDER, false);
        this.setDefaultState(defaultState);
    }
    
    @Override
    public boolean onBlockActivated (BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        
        if (player.isSneaking()) {
            
            worldIn.setBlockState(pos, state.with(SHOW_BORDER, !state.get(SHOW_BORDER)));
            return true;
        }
        
        return false;
    }
    
    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        
        return this.getDefaultState().with(BlockStateProperties.FACING, context.getFace()).with(BlockStateProperties.ENABLED, hasInventory(context.getWorld(), context.getPos().offset(context.getFace().getOpposite()), context.getFace()));
    }
    
    @Override
    public VoxelShape getShape (BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        
        switch (state.get(BlockStateProperties.FACING)) {
            
            case DOWN:
                return SHAPE_DOWN;
            
            case EAST:
                return SHAPE_EAST;
            
            case NORTH:
                return SHAPE_NORTH;
            
            case SOUTH:
                return SHAPE_SOUTH;
            
            case UP:
                return SHAPE_UP;
            
            case WEST:
                return SHAPE_WEST;
            
            default:
                return SHAPE_UP;
        }
    }
    
    @Override
    public boolean canSpawnInBlock () {
        
        return true;
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        builder.add(BlockStateProperties.FACING, BlockStateProperties.ENABLED, SHOW_BORDER);
    }
    
    @Override
    public BlockState updatePostPlacement (BlockState myState, Direction facing, BlockState facingState, IWorld world, BlockPos myPos, BlockPos facingPos) {
        
        if (facing.getOpposite() == myState.get(BlockStateProperties.FACING)) {
            
            return myState.with(BlockStateProperties.ENABLED, hasInventory((World) world, facingPos, facing.getOpposite()));
        }
        
        return super.updatePostPlacement(myState, facing, facingState, world, myPos, facingPos);
    }
    
    private static boolean hasInventory (World world, BlockPos pos, Direction facing) {
        
        return InventoryUtils.getInventory(world, pos, facing) != null;
    }
    
    @Override
    public TileEntity createNewTileEntity (IBlockReader worldIn) {
        
        return new TileEntityEnderHopper();
    }
    
    @Override
    public boolean eventReceived (BlockState state, World worldIn, BlockPos pos, int id, int param) {
        
        super.eventReceived(state, worldIn, pos, id, param);
        final TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }
}