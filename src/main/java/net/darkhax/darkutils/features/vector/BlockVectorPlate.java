package net.darkhax.darkutils.features.vector;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVectorPlate extends Block {

    public static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    private final double speed;

    public BlockVectorPlate (double speed) {

        super(Material.ROCK);
        this.setHardness(3.0F);
        this.setResistance(10f);
        this.setHarvestLevel("pickaxe", 1);
        this.setLightOpacity(0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.speed = speed;
    }

    private boolean checkForDrop (World world, BlockPos pos, IBlockState state) {

        if (!this.canBlockStay(world, pos)) {

            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return false;
        }

        else
            return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {

        return BOUNDS;
    }

    private boolean canBlockStay (World world, BlockPos pos) {

        return !(world.isAirBlock(pos.down()) || !world.isSideSolid(pos.down(), EnumFacing.UP));
    }

    @Override
    public boolean canPlaceBlockAt (World world, BlockPos pos) {

        return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos);
    }

    @Override
    public void neighborChanged (IBlockState state, World worldIn, BlockPos pos, Block blockIn) {

        this.checkForDrop(worldIn, pos, state);
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
    public boolean isOpaqueCube (IBlockState state) {

        return false;
    }

    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity) {

        final EnumFacing direction = state.getValue(FACING).getOpposite();

        if (!entity.isSneaking()) {

            entity.motionX += this.speed * direction.getFrontOffsetX();
            entity.motionZ += this.speed * direction.getFrontOffsetZ();

            if (entity instanceof EntityItem) {

                final EntityItem item = (EntityItem) entity;

                if (FeatureVectorPlate.preventItemDespawn) {
                    item.setAgeToCreativeDespawnTime();
                }

                if (FeatureVectorPlate.preventItemPickup && item.delayBeforeCanPickup < 5) {
                    item.setPickupDelay(20);
                }
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState () {

        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @Override
    public IBlockState getStateForPlacement (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {

        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public boolean canSpawnInBlock () {

        return true;
    }
}