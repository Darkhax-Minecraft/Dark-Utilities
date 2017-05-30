package net.darkhax.darkutils.features.material;

import net.darkhax.bookshelf.item.ItemSubType;

public class ItemMaterial extends ItemSubType {

    public static String[] varients = new String[] { "wither", "unstable", "cream", "sugar" };

    public ItemMaterial () {

        super(varients);
    }
}