package net.darkhax.darkutils.features.endertether;

import java.util.Random;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasic;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnderTether extends BlockTorch implements ITileEntityProvider {

    public BlockEnderTether () {

        this.hasTileEntity = true;
        this.setResistance(2000f);
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityEnderTether && playerIn.isSneaking()) {

            final TileEntityEnderTether tether = (TileEntityEnderTether) worldIn.getTileEntity(pos);
            tether.showBorder = !tether.showBorder;
            tether.markDirty();
            return true;
        }

        return false;
    }

    @Override
    public TileEntity createNewTileEntity (World world, int meta) {

        return new TileEntityEnderTether();
    }

    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {

        final TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileEntityBasic) {

            ((TileEntityBasic) tile).onTileRemoved(worldIn, pos, state);
        }

        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    @Deprecated
    public boolean eventReceived (IBlockState state, World worldIn, BlockPos pos, int id, int param) {

        super.eventReceived(state, worldIn, pos, id, param);
        final TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick (IBlockState state, World world, BlockPos pos, Random rand) {

        for (int i = 0; i < 3; i++) {
            world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 1D - rand.nextDouble(), pos.getY() + 0.8f - rand.nextDouble(), pos.getZ() + 1D - rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2D, (rand.nextDouble() - 0.5D) * 2D, (rand.nextDouble() - 0.5D) * 2D, new int[0]);
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape (IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {

        return BlockFaceShape.UNDEFINED;
    }
}