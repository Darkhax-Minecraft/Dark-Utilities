package net.darkhax.darkutils.features.timer;

import static net.darkhax.bookshelf.util.OreDictUtils.STONE;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@DUFeature(name = "Redstone Timer", description = "A block for timing redstone")
public class FeatureTimer extends Feature {

    public static Block blockTimer;

    private static boolean craftable = true;

    @Override
    public void onRegistry () {

        blockTimer = new BlockTimer();
        DarkUtils.REGISTRY.registerBlock(blockTimer, "timer");
        GameRegistry.registerTileEntity(TileEntityTimer.class, "timer");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the timer be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTimer), new Object[] { "sts", "tct", "sts", 's', STONE, 't', Blocks.REDSTONE_TORCH, 'c', Items.CLOCK }));
        }
    }
}
