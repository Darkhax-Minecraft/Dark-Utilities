package net.darkhax.darkutils.features.grate;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;

@DUFeature(name = "Item Grate", description = "A block that allows items through")
public class FeatureItemGrate extends Feature {

    public static Block blockGrate;

    @Override
    public void onPreInit () {

        blockGrate = DarkUtils.REGISTRY.registerBlock(new BlockGrate(), "grate");
    }
}
