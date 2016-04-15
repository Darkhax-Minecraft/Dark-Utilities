package net.darkhax.darkutils.blocks;

import java.util.Random;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockUpdateDetector extends Block {
    
    public BlockUpdateDetector() {
        
        super(Material.CIRCUITS);
        this.setDefaultState(blockState.getBaseState().withProperty(BlockStates.POWERED, false));
        this.setUnlocalizedName("darkutils.bud");
        this.setCreativeTab(DarkUtils.TAB);
    }
    
    @Override
    public void onNeighborBlockChange (World world, BlockPos pos, IBlockState state, Block neighbor) {
        
        
        if (world.isRemote)
            return;
            
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockStates.POWERED, true), 1 | 2);
        world.scheduleUpdate(pos, this, 5);
    }
    
    @Override
    public BlockStateContainer createBlockState () {
        
        return new BlockStateContainer(this, BlockStates.POWERED);
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return state.getValue(BlockStates.POWERED) ? 1 : 0;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return getDefaultState().withProperty(BlockStates.POWERED, meta == 1);
    }
    
    @Override
    public boolean canProvidePower (IBlockState state) {
        
        return true;
    }
    
    @Override
    public int getWeakPower (IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        
        return state.getValue(BlockStates.POWERED) ? 15 : 0;
    }
    
    @Override
    public int tickRate (World world) {
        
        return 5;
    }
    
    @Override
    public void updateTick (World world, BlockPos pos, IBlockState state, Random rand) {
        
        if (state.getValue(BlockStates.POWERED))
            world.setBlockState(pos, state.withProperty(BlockStates.POWERED, false), 1 | 2);
    }
    
    @Override
    public boolean isOpaqueCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isFullCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer () {
        
        return BlockRenderLayer.CUTOUT;
    }
}