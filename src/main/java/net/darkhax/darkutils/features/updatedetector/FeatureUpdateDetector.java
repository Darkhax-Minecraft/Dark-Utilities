package net.darkhax.darkutils.features.updatedetector;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@DUFeature(name = "Update Detector", description = "A block for detecting block updates")
public class FeatureUpdateDetector extends Feature {

    public static Block blockDetector;

    @Override
    public void onPreInit () {

        blockDetector = DarkUtils.REGISTRY.registerBlock(new BlockUpdateDetector(), "update_detector");
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapelessRecipe("convert_bud", new ItemStack(Blocks.OBSERVER), blockDetector);
    }
}
