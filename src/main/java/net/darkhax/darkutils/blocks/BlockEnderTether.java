package net.darkhax.darkutils.blocks;

import java.util.Random;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnderTether extends BlockTorch implements ITileEntityProvider {
    
    public BlockEnderTether() {
        
        this.isBlockContainer = true;
        this.setCreativeTab(DarkUtils.tab);
        this.setResistance(2000f);
        this.setUnlocalizedName("darkutils.endertether");
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntityEnderTether();
    }
    
    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
    
    @Override
    public boolean onBlockEventReceived (World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick (World world, BlockPos pos, IBlockState state, Random rand) {
        
        for (int i = 0; i < 3; i++)
            world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 1D - rand.nextDouble(), pos.getY() + 0.8f - rand.nextDouble(), pos.getZ() + 1D - rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2D, (rand.nextDouble() - 0.5D) * 2D, (rand.nextDouble() - 0.5D) * 2D, new int[0]);
    }
}