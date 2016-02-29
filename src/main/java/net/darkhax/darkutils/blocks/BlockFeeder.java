package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFeeder extends Block {
    
    public static final PropertyInteger FOOD = PropertyInteger.create("food", 0, 10);
    
    public BlockFeeder() {
        
        super(Material.glass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FOOD, Integer.valueOf(0)));
        this.setUnlocalizedName("darkutils.feeder");
        this.setCreativeTab(DarkUtils.tab);
    }
    
    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        int food = ((Integer)state.getValue(FOOD)).intValue();
        worldIn.setBlockState(pos, state.withProperty(FOOD, (food == 10) ? 0 : food + 1), 3);
        return true;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(FOOD, Integer.valueOf((meta > 10) ? 10 : (meta < 0) ? 0 : meta));
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return ((Integer) state.getValue(FOOD)).intValue();
    }
    
    @Override
    protected BlockState createBlockState () {
        
        return new BlockState(this, new IProperty[] { FOOD });
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
    public int getRenderType () {
        
        return 3;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer () {
        
        return EnumWorldBlockLayer.CUTOUT;
    }
}
