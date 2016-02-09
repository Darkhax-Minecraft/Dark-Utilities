package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.tileentity.TileEntitySneaky;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSneakyLever extends BlockSneaky {
    
    public BlockSneakyLever() {
        
        super(Material.circuits);
        this.setUnlocalizedName("darkutils.sneaky.lever");
    }
    
    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        if (playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() != null)
            return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
            
        else {
            
            worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, isPowered(worldIn, pos) ? 0.6F : 0.5F);
            worldIn.notifyNeighborsOfStateChange(pos, this);
            return true;
        }
    }
    
    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        
        if (isPowered(worldIn, pos))
            worldIn.notifyNeighborsOfStateChange(pos, this);
            
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public int getWeakPower (IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        
        return isPowered(worldIn, pos) ? 15 : 0;
    }
    
    @Override
    public int getStrongPower (IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        
        return isPowered(worldIn, pos) ? 15 : 0;
    }
    
    @Override
    public boolean canProvidePower () {
        
        return true;
    }
    
    public boolean isPowered (World world, BlockPos pos) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (!tile.isInvalid() && tile instanceof TileEntitySneaky)
            return ((TileEntitySneaky) tile).powered;
            
        return false;
    }
    
    public boolean isPowered (IBlockAccess world, BlockPos pos) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (!tile.isInvalid() && tile instanceof TileEntitySneaky)
            return ((TileEntitySneaky) tile).powered;
            
        return false;
    }
    
    public void setPowered (World world, BlockPos pos, boolean state) {
        
        TileEntity tile = world.getTileEntity(pos);
        
        if (!tile.isInvalid() && tile instanceof TileEntitySneaky)
            ((TileEntitySneaky) tile).powered = state;
    }
}