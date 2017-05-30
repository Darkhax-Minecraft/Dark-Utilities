package net.darkhax.darkutils.features.updatedetector;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.BLOCK_REDSTONE;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureUpdateDetector extends Feature {

    public static Block blockDetector;

    private static boolean craftable = true;

    @Override
    public void onPreInit () {

        blockDetector = new BlockUpdateDetector();
        ModUtils.registerBlock(blockDetector, "update_detector");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the Update Detector be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockDetector), new Object[] { "sps", "srs", "sps", 's', STONE, 'p', Blocks.PISTON, 'r', BLOCK_REDSTONE }));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockDetector);
    }
}
