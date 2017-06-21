package net.darkhax.darkutils.features.updatedetector;

import static net.darkhax.bookshelf.util.OreDictUtils.BLOCK_REDSTONE;
import static net.darkhax.bookshelf.util.OreDictUtils.STONE;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.handler.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

@DUFeature(name = "Update Detector", description = "A block for detecting block updates")
public class FeatureUpdateDetector extends Feature {

    public static Block blockDetector;

    private static boolean craftable = true;

    @Override
    public void onRegistry () {

        blockDetector = DarkUtils.REGISTRY.registerBlock(new BlockUpdateDetector(), "update_detector");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the Update Detector be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            RecipeHandler.addShapedOreRecipe(new ItemStack(blockDetector), "sps", "srs", "sps", 's', STONE, 'p', Blocks.PISTON, 'r', BLOCK_REDSTONE);
        }
    }
}
