package net.darkhax.darkutils.features.filter;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

@DUFeature(name = "Mob Filters", description = "Blocks for filtering mobs")
public class FeatureFilter extends Feature {

    public static Block blockFilter;

    public static Block blockInvertedFilter;

    @Override
    public void onPreInit () {

        blockFilter = new BlockFilter();
        DarkUtils.REGISTRY.registerBlock(blockFilter, new ItemBlockFilter(blockFilter, FilterType.getTypes()), "filter");
        blockInvertedFilter = new BlockInvertedFilter();
        DarkUtils.REGISTRY.registerBlock(blockInvertedFilter, new ItemBlockFilter(blockInvertedFilter, FilterType.getTypes()), "filter_inverted");
    }

    @Override
    public void onPreRecipe () {

        for (final FilterType type : FilterType.values()) {

            DarkUtils.REGISTRY.addShapedRecipe("filter_" + type.type, new ItemStack(blockFilter, 4, type.meta), "fsf", "sis", "fsf", 'f', OreDictUtils.FENCE_GATE_WOOD, 's', OreDictUtils.STONE, 'i', type.crafting);
            DarkUtils.REGISTRY.addShapelessRecipe("filter_convert_" + type.type, new ItemStack(blockInvertedFilter, 1, type.meta), new ItemStack(blockFilter, 1, type.meta));
            DarkUtils.REGISTRY.addShapelessRecipe("filter_convert_back_" + type.type, new ItemStack(blockFilter, 1, type.meta), new ItemStack(blockInvertedFilter, 1, type.meta));
        }
    }
}
