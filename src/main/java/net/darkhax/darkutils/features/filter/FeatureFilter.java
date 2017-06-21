package net.darkhax.darkutils.features.filter;

import static net.darkhax.bookshelf.util.OreDictUtils.STONE;

import java.util.HashMap;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.handler.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

@DUFeature(name = "Mob Filters", description = "Blocks for filtering mobs")
public class FeatureFilter extends Feature {

    public static Block blockFilter;

    public static Block blockInvertedFilter;

    private static HashMap<FilterType, Boolean> craftableNormal = new HashMap<>();

    private static HashMap<FilterType, Boolean> craftableInverted = new HashMap<>();

    @Override
    public void onRegistry () {

        blockFilter = new BlockFilter();
        DarkUtils.REGISTRY.registerBlock(blockFilter, new ItemBlockFilter(blockFilter, FilterType.getTypes()), "filter");
        blockInvertedFilter = new BlockInvertedFilter();
        DarkUtils.REGISTRY.registerBlock(blockInvertedFilter, new ItemBlockFilter(blockInvertedFilter, FilterType.getTypes()), "filter_inverted");
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

            RecipeHandler.addShapedOreRecipe(new ItemStack(blockFilter, 4, type.meta), "gsg", "sis", "gsg", 'g', OreDictUtils.FENCE_GATE_WOOD, 's', STONE, 'i', type.crafting);

            if (craftableInverted.get(type)) {

                RecipeHandler.createConversionRecipes(new ItemStack(blockFilter, 1, type.meta), new ItemStack(blockInvertedFilter, 1, type.meta));
                RecipeHandler.createConversionRecipes(new ItemStack(blockInvertedFilter, 1, type.meta), new ItemStack(blockFilter, 1, type.meta));
            }
        }
    }
}
