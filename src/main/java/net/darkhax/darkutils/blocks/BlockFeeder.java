package net.darkhax.darkutils.blocks;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.tileentity.TileEntityFeeder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
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

public class BlockFeeder extends BlockContainer {
    
    public static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.375d, 0d, 0.375d, 0.625d, 0.75d, 0.625d);
    public static final PropertyInteger FOOD = PropertyInteger.create("food", 0, 10);
    
    public BlockFeeder() {
        
        super(Material.GLASS);
        this.setUnlocalizedName("darkutils.feeder");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FOOD, Integer.valueOf(0)));
        this.setCreativeTab(DarkUtils.TAB);
    }
    
    @Override
    public boolean onBlockActivated (World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntityFeeder && ItemStackUtils.isValidStack(playerIn.getHeldItemMainhand())) {
            
            TileEntityFeeder feeder = (TileEntityFeeder) tile;
            ItemStack heldStack = playerIn.getHeldItemMainhand();
            
            if (feeder.isItemValidForSlot(-1, heldStack)) {
                
                feeder.setFood(feeder.getFood() + 1);
                
                if (!playerIn.capabilities.isCreativeMode) {
                    
                    heldStack.stackSize--;
                    
                    if (heldStack.stackSize == 0)
                        playerIn.setHeldItem(EnumHand.MAIN_HAND, null);
                }
                
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public int damageDropped (IBlockState state) {
        
        return 0;
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
    protected BlockStateContainer createBlockState () {
        
        return new BlockStateContainer(this, new IProperty[] { FOOD });
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
    public EnumBlockRenderType getRenderType (IBlockState state) {
        
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer () {
        
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public TileEntity createNewTileEntity (World worldIn, int meta) {
        
        return new TileEntityFeeder();
    }
    
    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {
        
        return BOUNDS;
    }
}
