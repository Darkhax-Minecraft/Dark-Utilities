package net.darkhax.darkutils.features.glass;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockSoulGlass extends StainedGlassBlock {
    
    public BlockSoulGlass() {
        
        super(DyeColor.BROWN, Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS));
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.ENABLED, false));
    }
    
    @Override
    public BlockRenderLayer getRenderLayer () {
        
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.ENABLED);
    }
    
    @Override
    public void neighborChanged (BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        
        world.setBlockState(pos, state.with(BlockStateProperties.ENABLED, isNearLava(pos, world)));
    }
    
    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        
        return this.getDefaultState().with(BlockStateProperties.ENABLED, isNearLava(context.getPos(), context.getWorld()));
    }
    
    @Override
    public boolean canProvidePower (BlockState state) {
        
        return state.get(BlockStateProperties.ENABLED);
    }
    
    @Override
    public int getWeakPower (BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
        
        return state.get(BlockStateProperties.ENABLED) ? 15 : 0;
    }
    
    private static boolean isNearLava (BlockPos pos, IWorld world) {
        
        for (final Direction side : Direction.values()) {
            
            final IFluidState state = world.getFluidState(pos.offset(side));
            
            if (!state.isEmpty() && state.isTagged(FluidTags.LAVA)) {
                
                return true;
            }
        }
        
        return false;
    }
}