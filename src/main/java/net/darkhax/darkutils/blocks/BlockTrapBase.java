package net.darkhax.darkutils.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrapBase extends Block {
    
    public BlockTrapBase() {
        
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
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
    public AxisAlignedBB getCollisionBoundingBox (World worldIn, BlockPos pos, IBlockState state) {
        
        return new AxisAlignedBB((double) pos.getX() + 0.0625F, (double) pos.getY(), (double) pos.getZ() + 0.0625F, (double) pos.getX() + 0.9375F, (double) pos.getY() + 0.0125F, (double) pos.getZ() + 0.9375F);
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