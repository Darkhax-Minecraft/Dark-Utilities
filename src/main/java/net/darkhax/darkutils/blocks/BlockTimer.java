package net.darkhax.darkutils.blocks;

import java.util.Random;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.darkhax.darkutils.tileentity.TileEntityTimer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTimer extends BlockContainer {
    
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    
    public BlockTimer() {
        
        super(Material.rock);
        this.setDefaultState(blockState.getBaseState().withProperty(POWERED, false));
        this.setUnlocalizedName("darkutils.timer");
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setHardness(1f);
        this.setCreativeTab(DarkUtils.tab);
    }
    
    @Override
    public boolean onBlockActivated (World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        TileEntityTimer timer = (TileEntityTimer) world.getTileEntity(pos);
        
        if (!timer.isInvalid()) {
            
            player.openGui(DarkUtils.instance, GuiHandler.TIMER, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntityTimer();
    }
    
    @Override
    public BlockState createBlockState () {
        
        return new BlockState(this, POWERED);
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return state.getValue(POWERED) ? 1 : 0;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return getDefaultState().withProperty(POWERED, meta == 1);
    }
    
    @Override
    public boolean canProvidePower () {
        
        return true;
    }
    
    @Override
    public int getWeakPower (IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        
        return state.getValue(POWERED) ? 15 : 0;
    }
    
    @Override
    public int tickRate (World world) {
        
        return 5;
    }
    
    @Override
    public void updateTick (World world, BlockPos pos, IBlockState state, Random rand) {
        
        if (state.getValue(POWERED))
            world.setBlockState(pos, state.withProperty(POWERED, false), 1 | 2);
    }
    
    @Override
    public int getRenderType () {
        
        return 3;
    }
    
    @Override
    public boolean isOpaqueCube () {
        
        return false;
    }
    
    @Override
    public boolean isFullCube () {
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer () {
        
        return EnumWorldBlockLayer.CUTOUT;
    }
}
