package net.darkhax.darkutils.features.dyeslime;

import java.util.List;

import net.minecraft.block.BlockSlime;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSlimeDyed extends BlockSlime {

    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor> create("color", EnumDyeColor.class);

    public BlockSlimeDyed () {

        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public int damageDropped (IBlockState state) {

        return state.getValue(COLOR).getMetadata();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item itemIn, CreativeTabs tab, List<ItemStack> list) {

        for (final EnumDyeColor enumdyecolor : EnumDyeColor.values())
            list.add(new ItemStack(itemIn, 1, enumdyecolor.getMetadata()));
    }

    @Override
    public MapColor getMapColor (IBlockState state) {

        return state.getValue(COLOR).getMapColor();
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(COLOR).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState () {

        return new BlockStateContainer(this, new IProperty[] { COLOR });
    }
}
