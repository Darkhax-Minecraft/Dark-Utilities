package net.darkhax.darkutils.features.enderhopper;

import javax.annotation.Nullable;

import net.darkhax.bookshelf.block.BlockTileEntity;
import net.darkhax.bookshelf.lib.BlockStates;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnderHopper extends BlockTileEntity {

    public static final AxisAlignedBB BOUNDS_DOWN = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1D, 1.0D);

    public static final AxisAlignedBB BOUNDS_UP = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public static final AxisAlignedBB BOUNDS_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1D, 1D);

    public static final AxisAlignedBB BOUNDS_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1D, 0.0625D);

    public static final AxisAlignedBB BOUNDS_WEST = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1D, 1D, 1.0D);

    public static final AxisAlignedBB BOUNDS_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1D, 1.0D);

    protected BlockEnderHopper () {

        super(Material.ROCK);
        this.setHardness(1.5f);
        this.setResistance(9000f);
        this.setLightLevel(0.25f);
        this.setHarvestLevel("pickaxe", 1);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStates.FACING, EnumFacing.UP));
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityEnderHopper && playerIn.isSneaking()) {

            final TileEntityEnderHopper hopper = (TileEntityEnderHopper) worldIn.getTileEntity(pos);
            hopper.showBorder = !hopper.showBorder;
            hopper.markDirty();
            return true;
        }
        return false;
    }

    @Override
    public IBlockState getStateForPlacement (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {

        return this.blockState.getBaseState().withProperty(BlockStates.FACING, facing);
    }

    @Override
    public TileEntity createNewTileEntity (World worldIn, int meta) {

        return new TileEntityEnderHopper();
    }

    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {

        final EnumFacing direction = state.getValue(BlockStates.FACING);

        if (direction == EnumFacing.DOWN) {
            return BOUNDS_DOWN;
        }
        else if (direction == EnumFacing.UP) {
            return BOUNDS_UP;
        }
        else if (direction == EnumFacing.NORTH) {
            return BOUNDS_NORTH;
        }
        else if (direction == EnumFacing.SOUTH) {
            return BOUNDS_SOUTH;
        }
        else if (direction == EnumFacing.WEST) {
            return BOUNDS_WEST;
        }
        else if (direction == EnumFacing.EAST) {
            return BOUNDS_EAST;
        }

        return BOUNDS_UP;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox (IBlockState blockState, World worldIn, BlockPos pos) {

        return NULL_AABB;
    }

    @Override
    public boolean isFullCube (IBlockState state) {

        return false;
    }

    @Override
    public boolean isPassable (IBlockAccess worldIn, BlockPos pos) {

        return true;
    }

    @Override
    public boolean isOpaqueCube (IBlockState state) {

        return false;
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(BlockStates.FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(BlockStates.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState () {

        return new BlockStateContainer(this, new IProperty[] { BlockStates.FACING });
    }
}
