package net.darkhax.darkutils.blocks;

import net.darkhax.bookshelf.client.RenderUtils;
import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.tileentity.TileEntitySneaky;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
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
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        
        TileEntity tile = worldIn.getTileEntity(pos);
        
        if (!tile.isInvalid() && tile instanceof TileEntitySneaky && placer instanceof EntityPlayer) {
            
            TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            sneaky.playerID = ((EntityPlayer) placer).getUniqueID().toString();
        }
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
    
    @Override
    public boolean addLandingEffects (WorldServer world, BlockPos pos, IBlockState state, EntityLivingBase entity, int count) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {
            
            TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            
            if (sneaky.heldState != null) {
                
                world.spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, count, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(sneaky.heldState) });
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean addHitEffects (World world, MovingObjectPosition hitPos, EffectRenderer renderer) {
        
        TileEntity tile = world.getTileEntity(hitPos.getBlockPos());
        
        if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {
            
            TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            
            if (sneaky.heldState != null)
                return RenderUtils.spawnDigParticles(renderer, sneaky.heldState, world, hitPos.getBlockPos(), hitPos.sideHit);
        }
        
        return false;
    }
    
    @Override
    public boolean addDestroyEffects (World world, BlockPos pos, EffectRenderer renderer) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntitySneaky && !tile.isInvalid()) {
            
            TileEntitySneaky sneaky = (TileEntitySneaky) tile;
            
            if (sneaky.heldState != null)
                return RenderUtils.spawnBreakParticles(renderer, sneaky.heldState, world, pos);
        }
        
        return false;
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