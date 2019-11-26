package net.darkhax.darkutils.features.redstone;

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

public class BlockShieldedRedstone extends Block {
    
    public BlockShieldedRedstone() {
        
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.FACING, Direction.NORTH));
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.FACING);
    }
    
    @Override
    public boolean canProvidePower (BlockState state) {
        
        return true;
    }
    
    @Override
    public int getWeakPower (BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
        
        return side.getOpposite() == state.get(BlockStateProperties.FACING) ? 15 : 0;
    }
    
    @Override
    @Nullable
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        
        return super.getStateForPlacement(context).with(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }
}