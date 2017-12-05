package net.darkhax.darkutils.features.loretag;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum LoreType {

    BLACK(OreDictUtils.DYE_BLACK, '0'),
    DARK_BLUE(OreDictUtils.DYE_BLUE, '1'),
    DARK_GREEN(OreDictUtils.DYE_GREEN, '2'),
    DARK_AQUA(OreDictUtils.DYE_CYAN, '3'),
    DARK_RED(OreDictUtils.DUST_REDSTONE, '4'),
    DARK_PURPLE(OreDictUtils.DYE_PURPLE, '5'),
    GOLD(OreDictUtils.DYE_ORANGE, '6'),
    GRAY(OreDictUtils.DYE_LIGHT_GRAY, '7'),
    DARK_GRAY(OreDictUtils.DYE_GRAY, '8'),
    BLUE(OreDictUtils.DYE_LIGHT_BLUE, '9'),
    GREEN(OreDictUtils.DYE_LIME, 'a'),
    AQUA(OreDictUtils.GEM_DIAMOND, 'b'),
    RED(OreDictUtils.DYE_RED, 'c'),
    LIGHT_PURPLE(OreDictUtils.DYE_MAGENTA, 'd'),
    YELLOW(OreDictUtils.DYE_YELLOW, 'e'),
    WHITE(OreDictUtils.DYE_WHITE, 'f');

    private final Object crafting;
    private final char color;

    LoreType (Object crafting, char color) {

        this.crafting = crafting;
        this.color = color;
    }

    public Object getCrafting () {

        return this.crafting;
    }

    @SideOnly(Side.CLIENT)
    public int getColor () {

        return Minecraft.getMinecraft().fontRenderer.getColorCode(this.color);
    }
}
