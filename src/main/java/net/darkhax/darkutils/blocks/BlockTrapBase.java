package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTrapBase extends Block {
    
    public BlockTrapBase() {
        
        super(Material.rock);
        this.setCreativeTab(DarkUtils.tab);
        this.setHardness(3.0F);
        this.setResistance(120f);
        this.setHarvestLevel("pickaxe", 1);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0125F, 0.9375F);
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
    public MapColor getMapColor (IBlockState state) {
        
        return MapColor.obsidianColor;
    }
    
    @Override
    public boolean isFullCube () {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube () {
        
        return false;
    }
}