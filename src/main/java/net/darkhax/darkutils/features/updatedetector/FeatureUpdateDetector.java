package net.darkhax.darkutils.features.updatedetector;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;

@DUFeature(name = "Update Detector", description = "A block for detecting block updates")
public class FeatureUpdateDetector extends Feature {

    public static Block blockDetector;

    @Override
    public void onRegistry () {

        blockDetector = DarkUtils.REGISTRY.registerBlock(new BlockUpdateDetector(), "update_detector");
    }
}
