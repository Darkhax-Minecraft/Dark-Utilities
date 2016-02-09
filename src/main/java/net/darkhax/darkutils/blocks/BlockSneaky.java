package net.darkhax.darkutils.blocks;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.tileentity.TileEntitySneaky;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSneaky extends BlockContainer {
    
    public BlockSneaky(Material material) {
        
        super(material);
        this.setUnlocalizedName("darkutils.sneaky");
        this.setCreativeTab(DarkUtils.tab);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypePiston);
    }
    
    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        TileEntitySneaky tile = ((TileEntitySneaky) worldIn.getTileEntity(pos));
        ItemStack stack = playerIn.getHeldItem();
        
        if (!tile.isInvalid() && stack != null && stack.getItem() != null) {
            
            Block block = Block.getBlockFromItem(stack.getItem());
            
            if (block instanceof Block) {
                
                tile.heldState = block.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, playerIn.getHeldItem().getMetadata(), playerIn);
                worldIn.markBlockForUpdate(pos);
            }
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public int getRenderType () {
        
        return 3;
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntitySneaky();
    }
    
    @Override
    public BlockState createBlockState () {
        
        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { BlockStates.HELD_STATE });
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return getDefaultState();
    }
    
    @Override
    public IBlockState getExtendedState (IBlockState state, IBlockAccess world, BlockPos pos) {
        
        if (world.getTileEntity(pos) instanceof TileEntitySneaky) {
            
            TileEntitySneaky tile = ((TileEntitySneaky) world.getTileEntity(pos));
            return ((IExtendedBlockState) state).withProperty(BlockStates.HELD_STATE, tile.heldState);
        }
        
        else
            return ((IExtendedBlockState) state);
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
    public boolean canRenderInLayer (EnumWorldBlockLayer layer) {
        
        return true;
    }
}