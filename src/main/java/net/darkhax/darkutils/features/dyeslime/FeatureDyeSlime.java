package net.darkhax.darkutils.features.dyeslime;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureDyeSlime extends Feature {

    private static String[] COLORS = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };

    public static Block blockSlime;

    private boolean craftable;

    @Override
    public void onPreInit () {

        blockSlime = new BlockSlimeDyed();
        ModUtils.registerBlock(blockSlime, new ItemBlockBasic(blockSlime, COLORS, false), "slime_dyed");

        for (final EnumDyeColor color : EnumDyeColor.values())
            OreDictionary.registerOre(OreDictUtils.BLOCK_SLIME, new ItemStack(blockSlime, 1, color.getMetadata()));
    }

    @Override
    public void setupConfiguration (Configuration config) {

        this.craftable = config.getBoolean("Craftable", this.configName, true, "Should the dyed slime block be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (this.craftable)
            for (final EnumDyeColor color : EnumDyeColor.values())
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockSlime, 8, color.getMetadata()), new Object[] { "xxx", "xyx", "xxx", 'x', OreDictUtils.BLOCK_SLIME, 'y', new ItemStack(Items.DYE, 1, color.getDyeDamage()) }));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockSlime, "slime_dyed", COLORS);
    }
}
