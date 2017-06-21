package net.darkhax.darkutils.features.material;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWitherDust extends Block {

    public static String[] types = new String[] { "default", "bricked", "carved", "chiseled", "magma", "tiled" };

    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType> create("variant", EnumType.class);

    public BlockWitherDust () {

        super(Material.SAND, MapColor.BLACK);
        this.setHarvestLevel("pickaxe", 1);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.DEFAULT));
        this.setHardness(0.5f);
        this.setResistance(5f);
        this.setSoundType(SoundType.GROUND);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (CreativeTabs tab, NonNullList<ItemStack> list) {

        for (int meta = 0; meta < types.length; meta++) {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState () {

        return new BlockStateContainer(this, new IProperty[] { VARIANT });
    }

    @Override
    public boolean isBeaconBase (IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {

        return true;
    }

    @Override
    public int damageDropped (IBlockState state) {

        return this.getMetaFromState(state);
    }

    @Override
    public boolean canEntityDestroy (IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {

        return !(entity instanceof EntityWither) && !(entity instanceof EntityWitherSkull);
    }

    @Override
    public void onBlockExploded (World world, BlockPos pos, Explosion explosion) {

    }

    @Override
    public boolean canDropFromExplosion (Explosion explosion) {

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords (IBlockState state, IBlockAccess source, BlockPos pos) {

        if (this.getMetaFromState(state) == 4) {
            return 15728880;
        }

        return super.getPackedLightmapCoords(state, source, pos);
    }

    public static enum EnumType implements IStringSerializable {

        DEFAULT(0, "default"),
        BRICK(1, "bricked"),
        CARVED(2, "carved"),
        CHISELED(3, "chiseled"),
        MAGMA(4, "magma"),
        TILED(5, "tiled");

        private static final EnumType[] META_LOOKUP = new EnumType[values().length];

        private final int meta;

        private final String name;

        private EnumType (int meta, String name) {

            this.meta = meta;
            this.name = name;
        }

        public int getMetadata () {

            return this.meta;
        }

        @Override
        public String toString () {

            return this.name;
        }

        public static EnumType byMetadata (int meta) {

            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName () {

            return this.name;
        }

        static {

            for (final EnumType type : values()) {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
    }
}
