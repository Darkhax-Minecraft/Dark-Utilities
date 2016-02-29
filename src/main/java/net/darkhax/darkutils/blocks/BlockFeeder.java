package net.darkhax.darkutils.blocks;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.blocks.BlockFilter.EnumType;
import net.darkhax.darkutils.tileentity.TileEntityFeeder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFeeder extends BlockContainer {
    
    public static final PropertyInteger FOOD = PropertyInteger.create("food", 0, 10);
    
    public BlockFeeder() {
        
        super(Material.glass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FOOD, Integer.valueOf(0)));
        this.setUnlocalizedName("darkutils.feeder");
        this.setCreativeTab(DarkUtils.tab);
    }
    
    @Override
    public boolean onBlockActivated (World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntityFeeder && ItemStackUtils.isValidStack(playerIn.getHeldItem())) {
            
            TileEntityFeeder feeder = (TileEntityFeeder) tile;
            ItemStack heldStack = playerIn.getHeldItem();
            
            if (feeder.isItemValidForSlot(-1, heldStack)) {

                feeder.setFood(feeder.getFood() +1);
                heldStack.stackSize--;
                
                if (heldStack.stackSize == 0)
                    playerIn.setCurrentItemOrArmor(0, null);
                
                return true;
            }
        }
        
        return false;
    }
    
    
    @Override
    public int damageDropped (IBlockState state) {
        
        return ((Integer) state.getValue(FOOD).intValue());
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

    @Override
    public TileEntity createNewTileEntity (World worldIn, int meta) {
        
        return new TileEntityFeeder();
    }
}
