package net.darkhax.darkutils.features.filter;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;

@DUFeature(name = "Mob Filters", description = "Blocks for filtering mobs")
public class FeatureFilter extends Feature {

    public static Block blockFilter;

    public static Block blockInvertedFilter;

    @Override
    public void onRegistry () {

        blockFilter = new BlockFilter();
        DarkUtils.REGISTRY.registerBlock(blockFilter, new ItemBlockFilter(blockFilter, FilterType.getTypes()), "filter");
        blockInvertedFilter = new BlockInvertedFilter();
        DarkUtils.REGISTRY.registerBlock(blockInvertedFilter, new ItemBlockFilter(blockInvertedFilter, FilterType.getTypes()), "filter_inverted");
    }
}
