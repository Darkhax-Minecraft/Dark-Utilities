package net.darkhax.darkutils.blocks;

import net.darkhax.bookshelf.lib.BlockStates;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSneakyLever extends BlockSneaky {
    
    public BlockSneakyLever() {
        
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStates.POWERED, Boolean.valueOf(false)));
        this.setUnlocalizedName("darkutils.sneaky.lever");
    }
    
    @Override
    public BlockState createBlockState () {
        
        return new ExtendedBlockState(this, new IProperty[] { BlockStates.POWERED }, new IUnlistedProperty[] { BlockStates.HELD_STATE });
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return state.getValue(BlockStates.POWERED).booleanValue() ? 1 : 0;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return getDefaultState().withProperty(BlockStates.POWERED, (meta == 0) ? false : true);
    }
    
    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        if (playerIn.isSneaking() && playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() != null)
            return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
            
        if (worldIn.isRemote)
            return true;
            
        else {
            
            state = state.cycleProperty(BlockStates.POWERED);
            worldIn.setBlockState(pos, state, 1 | 2);
            worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean) state.getValue(BlockStates.POWERED)).booleanValue() ? 0.6F : 0.5F);
            worldIn.notifyNeighborsOfStateChange(pos, this);
            return true;
        }
    }
    
    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        
        if (((Boolean) state.getValue(BlockStates.POWERED)).booleanValue())
            worldIn.notifyNeighborsOfStateChange(pos, this);
            
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public int getWeakPower (IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        
        return ((Boolean) state.getValue(BlockStates.POWERED)).booleanValue() ? 15 : 0;
    }
    
    @Override
    public int getStrongPower (IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        
        return !((Boolean) state.getValue(BlockStates.POWERED)).booleanValue() ? 0 : 15;
    }
    
    @Override
    public boolean canProvidePower () {
        
        return true;
    }
}