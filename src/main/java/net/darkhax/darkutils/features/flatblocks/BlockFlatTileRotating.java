package net.darkhax.darkutils.features.flatblocks;

import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockFlatTileRotating extends BlockFlatTile {
    
    public BlockFlatTileRotating(CollisionEffect collisionEffect) {
        
        this(BLOCK_PROPERTIES, collisionEffect);
    }
    
    public BlockFlatTileRotating(Properties properties, CollisionEffect collisionEffect) {
        
        super(properties, collisionEffect);
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }
    
    @Override
    public ActionResultType onBlockActivated (BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        
        if (player.isShiftKeyDown()) {
            
            world.setBlockState(pos, this.rotate(state, Rotation.CLOCKWISE_90));
            return ActionResultType.SUCCESS;
        }
        
        return super.onBlockActivated(state, world, pos, player, handIn, hit);
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }
    
    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        
        BlockState placedState = super.getStateForPlacement(context);
        
        for (final Direction facing : context.getNearestLookingDirections()) {
            
            if (facing.getAxis().isHorizontal()) {
                
                placedState = placedState.with(BlockStateProperties.HORIZONTAL_FACING, facing);
                break;
            }
        }
        
        return placedState;
    }
    
    @Override
    public BlockState rotate (BlockState state, Rotation rot) {
        
        return state.with(BlockStateProperties.HORIZONTAL_FACING, rot.rotate(state.get(BlockStateProperties.HORIZONTAL_FACING)));
    }
    
    @Override
    public BlockState mirror (BlockState state, Mirror mirror) {
        
        return state.with(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.get(BlockStateProperties.HORIZONTAL_FACING)));
    }
}
