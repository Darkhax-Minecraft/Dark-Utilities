package net.darkhax.darkutils.features.filter;

import java.util.List;

import net.darkhax.bookshelf.lib.util.BlockUtils;
import net.darkhax.bookshelf.lib.util.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInvertedFilter extends Block {

    public static final PropertyEnum<FilterType> VARIANT = PropertyEnum.<FilterType> create("variant", FilterType.class);

    public BlockInvertedFilter () {

        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, FilterType.PLAYER));
        this.setHardness(3.0F);
        this.setResistance(10f);
        this.setHarvestLevel("pickaxe", 1);
        this.setLightOpacity(255);
    }

    @Override
    public int damageDropped (IBlockState state) {

        return state.getValue(VARIANT).meta;
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(VARIANT, FilterType.fromMeta(meta));
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(VARIANT).meta;
    }

    @Override
    protected BlockStateContainer createBlockState () {

        return new BlockStateContainer(this, new IProperty[] { VARIANT });
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
    public void addCollisionBoxToList (IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity collidingEntity) {

        final int meta = state.getValue(VARIANT).meta;

        if (collidingEntity instanceof EntityLivingBase) {

            final EntityLivingBase living = (EntityLivingBase) collidingEntity;

            if (!FilterType.isValidTarget(living, meta)) {

                this.snagMob(living, pos);
                return;
            }
        }

        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, collidingEntity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item item, CreativeTabs tab, List<ItemStack> list) {

        for (final FilterType type : FilterType.values())
            list.add(new ItemStack(item, 1, type.meta));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer () {

        return BlockRenderLayer.CUTOUT;
    }

    private void snagMob (EntityLivingBase living, BlockPos pos) {

        if (BlockUtils.isFluid(living.getEntityWorld().getBlockState(pos.offset(EnumFacing.UP)).getBlock()))
            EntityUtils.pushTowards(living, pos.offset(EnumFacing.DOWN), 0.6f);
    }
}