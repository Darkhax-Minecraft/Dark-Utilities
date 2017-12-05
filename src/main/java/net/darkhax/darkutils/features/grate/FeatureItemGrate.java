package net.darkhax.darkutils.features.grate;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@DUFeature(name = "Item Grate", description = "A block that allows items through")
public class FeatureItemGrate extends Feature {

    public static Block blockGrate;

    @Override
    public void onPreInit () {

        blockGrate = DarkUtils.REGISTRY.registerBlock(new BlockGrate(), "grate");
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapelessRecipe("item_grate", new ItemStack(blockGrate), OreDictUtils.STONE, Blocks.IRON_BARS, Blocks.TRAPDOOR);
    }
}
