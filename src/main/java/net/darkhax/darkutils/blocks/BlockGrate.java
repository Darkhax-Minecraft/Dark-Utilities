package net.darkhax.darkutils.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrate extends Block {
    
    public BlockGrate() {
        
        super(Material.rock);
        this.setUnlocalizedName("darkutils.grate");
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0f, 0.87f, 0f, 1f, 1f, 1f);
    }
    
    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity) {
        
        if (entity instanceof EntityItem && world.isAirBlock(pos.offset(EnumFacing.DOWN, 1)))
            entity.setPositionAndUpdate(pos.getX() + 0.5f, pos.getY() - 0.3f, pos.getZ() + 0.5f);
    }
    
    @Override
    public boolean isFullCube () {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube () {
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer () {
        
        return EnumWorldBlockLayer.CUTOUT;
    }
}