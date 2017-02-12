package net.darkhax.darkutils.features.trap;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrap extends Block {

    public static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public static final PropertyEnum<TrapType> VARIANT = PropertyEnum.<TrapType> create("variant", TrapType.class);

    public BlockTrap () {

        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, TrapType.POISON));
        this.setHardness(3.0F);
        this.setResistance(10f);
        this.setHarvestLevel("pickaxe", 1);
        this.setLightOpacity(0);
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

    private boolean canBlockStay (World world, BlockPos pos) {

        return !(world.isAirBlock(pos.down()) || !world.isSideSolid(pos.down(), EnumFacing.UP));
    }

    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {

        return BOUNDS;
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
    public int damageDropped (IBlockState state) {

        return state.getValue(VARIANT).meta;
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(VARIANT, TrapType.fromMeta(meta));
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

        if (entity instanceof EntityLivingBase) {

            final EntityLivingBase living = (EntityLivingBase) entity;
            PotionEffect effect = null;

            final int type = this.getMetaFromState(state);

            if (type == 0) {
                effect = new PotionEffect(MobEffects.POISON, 100);
            }

            if (type == 1) {
                effect = new PotionEffect(MobEffects.WEAKNESS, 60);
            }

            if (type == 2) {
                living.attackEntityFrom(DamageSource.magic, 2.5f);
            }

            if (type == 3) {
                effect = new PotionEffect(MobEffects.SLOWNESS, 60, 2);
            }

            if (type == 4) {
                living.setFire(1);
            }

            if (type == 5) {
                effect = new PotionEffect(MobEffects.WITHER, 60);
            }

            if (effect != null) {

                effect.setCurativeItems(new ArrayList<ItemStack>());
                living.addPotionEffect(effect);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item itemIn, CreativeTabs tab, List<ItemStack> list) {

        for (final TrapType type : TrapType.values()) {
            list.add(new ItemStack(itemIn, 1, type.meta));
        }
    }
}