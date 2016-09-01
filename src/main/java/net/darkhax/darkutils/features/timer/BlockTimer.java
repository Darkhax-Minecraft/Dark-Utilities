package net.darkhax.darkutils.features.timer;

import java.util.Random;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTimer extends BlockContainer {
    
    public static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0d, 0.0d, 0.0d, 1.0d, 0.125d, 1.0d);
    
    public BlockTimer() {
        
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStates.POWERED, false));
        this.setUnlocalizedName("darkutils.timer");
        this.setHardness(1f);
        this.setCreativeTab(DarkUtils.TAB);
    }
    
    @Override
    public boolean onBlockActivated (World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        final TileEntityTimer timer = (TileEntityTimer) world.getTileEntity(pos);
        
        if (!timer.isInvalid()) {
            
            player.openGui(DarkUtils.instance, GuiHandler.TIMER, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {
        
        return BOUNDS;
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntityTimer();
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
        
        return this.getDefaultState().withProperty(BlockStates.POWERED, meta == 1);
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
    
    @Override
    public EnumBlockRenderType getRenderType (IBlockState state) {
        
        return EnumBlockRenderType.MODEL;
    }
}