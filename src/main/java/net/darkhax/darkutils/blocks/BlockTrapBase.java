package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTrapBase extends Block {
    
    public static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0d, 0d, 0.0625d, 1d, 0.0125d, 1d);
    
    public BlockTrapBase() {
        
        super(Material.ROCK);
        this.setCreativeTab(DarkUtils.TAB);
        this.setHardness(3.0F);
        this.setResistance(10f);
        this.setHarvestLevel("pickaxe", 1);
    }
    
    private boolean checkForDrop (World world, BlockPos pos, IBlockState state) {
        
        if (!this.canBlockStay(world, pos)) {
            
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return false;
        }
        
        else
            return true;
    }
    
    private boolean canBlockStay (World world, BlockPos pos) {
        
        return !(world.isAirBlock(pos.down()) || !world.isSideSolid(pos.down(), EnumFacing.UP));
    }
    
    @Override
    public boolean canPlaceBlockAt (World world, BlockPos pos) {
        
        return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos);
    }
    
    @Override
    public void onNeighborBlockChange (World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        
        this.checkForDrop(world, pos, state);
    }
    
    @Override
    public boolean isFullCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube (IBlockState state) {
        
        return false;
    }
}