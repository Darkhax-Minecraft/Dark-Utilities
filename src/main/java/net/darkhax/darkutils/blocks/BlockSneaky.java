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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSneaky extends BlockContainer {
    
    public BlockSneaky() {
        
        super(Material.rock);
        this.setUnlocalizedName("darkutils.sneaky");
        this.setCreativeTab(DarkUtils.tab);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setDefaultState(((IExtendedBlockState) blockState.getBaseState()).withProperty(BlockStates.HELD_STATE, null).withProperty(BlockStates.BLOCK_ACCESS, null).withProperty(BlockStates.BLOCKPOS, null));
    }
    
    @Override
    public boolean onBlockActivated (World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        TileEntity tile = world.getTileEntity(pos);
        ItemStack stack = playerIn.getHeldItem();
        
        if (!tile.isInvalid() && tile instanceof TileEntitySneaky && stack != null && stack.getItem() != null) {
            
            TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            Block block = Block.getBlockFromItem(stack.getItem());
            
            if (block instanceof Block) {
                
                IBlockState heldState = Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getItemDamage());
                
                if (heldState != null && isValidBlock(heldState)) {
                    
                    sneaky.heldState = heldState;
                    world.markBlockForUpdate(pos);
                    return true;
                }
            }
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
        
        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { BlockStates.HELD_STATE, BlockStates.BLOCK_ACCESS, BlockStates.BLOCKPOS });
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
        
        state = ((IExtendedBlockState) state).withProperty(BlockStates.BLOCK_ACCESS, world).withProperty(BlockStates.BLOCKPOS, pos);
        
        if (world.getTileEntity(pos) instanceof TileEntitySneaky) {
            
            TileEntitySneaky tile = ((TileEntitySneaky) world.getTileEntity(pos));
            return ((IExtendedBlockState) state).withProperty(BlockStates.HELD_STATE, tile.heldState);
        }
        
        else
            return state;
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
    
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier (IBlockAccess world, BlockPos pos, int pass) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntitySneaky) {
            
            TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            IBlockState state = sneaky.heldState;
            
            if (state != null)
                return state.getBlock() instanceof BlockSneaky ? 0xFFFFFF : state.getBlock().colorMultiplier(world, pos, pass);
        }
        
        return 0xFFFFFF;
    }
    
    /**
     * A check to see if a block is valid for the sneaky block. For a block to be valid, it
     * must be an opaque cube, or have a render type of 3. Tile entities are considered
     * invalid.
     * 
     * @param state The current BlockState being tested.
     * @return boolean Whether or not the block is valid for the sneaky block.
     */
    private static boolean isValidBlock (IBlockState state) {
        
        Block block = state.getBlock();
        return (block.isOpaqueCube() || block.getRenderType() == 3) && !block.hasTileEntity(state) && block.getMaterial() != Material.air;
    }
}