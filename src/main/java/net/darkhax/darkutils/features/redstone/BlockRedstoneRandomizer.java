package net.darkhax.darkutils.features.redstone;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockRedstoneRandomizer extends Block {

    public BlockRedstoneRandomizer() {
        
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.5F).tickRandomly());
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.ENABLED, false).with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }
    
    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        
        world.setBlockState(pos, state.with(BlockStateProperties.ENABLED, !(state.get(BlockStateProperties.ENABLED))));
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.ENABLED, BlockStateProperties.HORIZONTAL_FACING);
    }
    
    @Override
    public boolean canProvidePower(BlockState state) {
        
        return state.get(BlockStateProperties.ENABLED);
     }

    @Override
     public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
        
        return state.get(BlockStateProperties.ENABLED) && side.getOpposite() == state.get(BlockStateProperties.HORIZONTAL_FACING)? 15 : 0;
     }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        BlockState placedState = super.getStateForPlacement(context);
        
        for (final Direction facing : context.getNearestLookingDirections()) {
            
            if (facing.getAxis().isHorizontal()) {
                
                placedState = placedState.with(BlockStateProperties.HORIZONTAL_FACING, facing);
                break;
            }
        }
        
        return placedState;
    }
}