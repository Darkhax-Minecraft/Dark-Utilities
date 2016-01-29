package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrate extends Block {
    
    public BlockGrate() {
        
        super(Material.rock);
        this.setUnlocalizedName("darkutils.grate");
        this.setCreativeTab(DarkUtils.tab);
        this.setHardness(3.0F);
        this.setResistance(5f);
        this.setBlockBounds(0.01f, 0.87f, 0.01f, 0.99f, 0.99f, 0.99f);
    }
    
    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity) {
        
        if (entity instanceof EntityItem)
            entity.setPositionAndUpdate(pos.getX() + 0.5f, pos.getY() - 0.2f, pos.getZ() + 0.5f);
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