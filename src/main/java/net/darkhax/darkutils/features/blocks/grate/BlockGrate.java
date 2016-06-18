package net.darkhax.darkutils.features.blocks.grate;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrate extends Block {
    
    public static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.01d, 0.87d, 0.01d, 0.99d, 0.99d, 0.99d);
    
    public BlockGrate() {
        
        super(Material.ROCK);
        this.setUnlocalizedName("darkutils.grate");
        this.setCreativeTab(DarkUtils.TAB);
        this.setHardness(3.0F);
        this.setResistance(5f);
    }
    
    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity) {
        
        if (entity instanceof EntityItem)
            entity.setPositionAndUpdate(pos.getX() + 0.5f, pos.getY() - 0.2f, pos.getZ() + 0.5f);
    }
    
    @Override
    public boolean isFullCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer () {
        
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {
        
        return BOUNDS;
    }
}