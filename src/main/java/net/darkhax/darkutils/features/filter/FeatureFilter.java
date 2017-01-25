package net.darkhax.darkutils.features.filter;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import java.util.HashMap;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureFilter extends Feature {

    public static Block blockFilter;

    public static Block blockInvertedFilter;

    private static HashMap<FilterType, Boolean> craftableNormal = new HashMap<>();

    private static HashMap<FilterType, Boolean> craftableInverted = new HashMap<>();

    @Override
    public void onPreInit () {

        blockFilter = new BlockFilter();
        ModUtils.registerBlock(blockFilter, new ItemBlockFilter(blockFilter, FilterType.getTypes()), "filter");

        blockInvertedFilter = new BlockInvertedFilter();
        ModUtils.registerBlock(blockInvertedFilter, new ItemBlockFilter(blockInvertedFilter, FilterType.getTypes()), "filter_inverted");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        for (final FilterType type : FilterType.values()) {

            craftableNormal.put(type, config.getBoolean("Craft " + type.type + " Filter", this.configName, true, "Should the " + type.type + " filter be craftable?"));
            craftableInverted.put(type, config.getBoolean("Craft Inverted " + type.type + " Filter", this.configName, true, "Should the inverted " + type.type + " filter be craftable?"));
        }
    }

    @Override
    public void setupRecipes () {

        for (final FilterType type : FilterType.values()) {

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 4, type.meta), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', type.crafting }));

            if (craftableInverted.get(type)) {

                ModUtils.addConversionRecipes(new ItemStack(blockFilter, 1, type.meta), new ItemStack(blockInvertedFilter, 1, type.meta));
                ModUtils.addConversionRecipes(new ItemStack(blockInvertedFilter, 1, type.meta), new ItemStack(blockFilter, 1, type.meta));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockFilter, "filter", FilterType.getTypes());
        ModUtils.registerBlockInvModel(blockInvertedFilter, "filter_inverted", FilterType.getTypes());
    }
}
