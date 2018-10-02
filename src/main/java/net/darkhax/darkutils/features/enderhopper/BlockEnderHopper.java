package net.darkhax.darkutils.features.enderhopper;

import net.darkhax.bookshelf.block.BlockTileEntity;
import net.darkhax.bookshelf.data.Blockstates;
import net.darkhax.bookshelf.util.GameUtils;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

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
        this.setDefaultState(this.blockState.getBaseState().withProperty(Blockstates.FACING, EnumFacing.UP).withProperty(Blockstates.ENABLED, false));
    }

    @Override
    @Deprecated
    public IBlockState getActualState (IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        return state.withProperty(Blockstates.ENABLED, isEnabled(state, worldIn, pos));
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityEnderHopper && playerIn.isSneaking()) {

            final TileEntityEnderHopper hopper = (TileEntityEnderHopper) worldIn.getTileEntity(pos);
            hopper.showBorder = !hopper.showBorder;
            hopper.markDirty();
            return true;
        }

        return false;
    }

    @Override
    @Deprecated
    public IBlockState getStateForPlacement (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {

        return this.blockState.getBaseState().withProperty(Blockstates.FACING, facing);
    }

    @Override
    public TileEntity createNewTileEntity (World worldIn, int meta) {

        return new TileEntityEnderHopper();
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {

        final EnumFacing direction = state.getValue(Blockstates.FACING);

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
    @Deprecated
    public AxisAlignedBB getCollisionBoundingBox (IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {

        return NULL_AABB;
    }

    @Override
    @Deprecated
    public boolean isFullCube (IBlockState state) {

        return false;
    }

    @Override
    public boolean isPassable (IBlockAccess worldIn, BlockPos pos) {

        return true;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube (IBlockState state) {

        return false;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(Blockstates.FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(Blockstates.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState () {

        return new BlockStateContainer(this, new IProperty[] { Blockstates.FACING, Blockstates.ENABLED });
    }

    public static boolean isEnabled (IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (GameUtils.isClient()) {

            if (PlayerUtils.getClientPlayer().getEntityWorld().isBlockPowered(pos)) {

                return false;
            }
        }

        final EnumFacing direction = state.getValue(Blockstates.FACING);
        final TileEntity tile = worldIn.getTileEntity(pos.offset(direction.getOpposite()));
        return tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction);
    }

    @Override
    public BlockFaceShape getBlockFaceShape (IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {

        return BlockFaceShape.UNDEFINED;
    }
}
