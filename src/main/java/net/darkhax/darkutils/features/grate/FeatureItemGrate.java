package net.darkhax.darkutils.features.grate;

import static net.darkhax.bookshelf.util.OreDictUtils.STONE;

import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@DUFeature(name = "Item Grate", description = "A block that allows items through")
public class FeatureItemGrate extends Feature {

    public static Block blockGrate;

    private static boolean craftable;

    @Override
    public void onPreInit () {

        blockGrate = new BlockGrate();
        ModUtils.registerBlock(blockGrate, "grate");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the Item Grate be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(blockGrate), Blocks.IRON_BARS, STONE, Blocks.TRAPDOOR));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockGrate);
    }
}
