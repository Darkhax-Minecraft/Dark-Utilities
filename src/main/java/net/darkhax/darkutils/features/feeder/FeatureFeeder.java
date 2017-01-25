package net.darkhax.darkutils.features.feeder;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.GEM_EMERALD;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.PANE_GLASS;

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

public class FeatureFeeder extends Feature {

    public static Block blockFeeder;

    public static boolean craftable;

    @Override
    public void onPreInit () {

        blockFeeder = new BlockFeeder();
        ModUtils.registerBlock(blockFeeder, "feeder");
        GameRegistry.registerTileEntity(TileEntityFeeder.class, "feeder");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the feeder be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFeeder), new Object[] { "ccc", "geg", "ccc", 'c', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 5), 'g', PANE_GLASS, 'e', GEM_EMERALD }));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockFeeder);
    }
}
