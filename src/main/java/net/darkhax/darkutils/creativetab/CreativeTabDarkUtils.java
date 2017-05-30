package net.darkhax.darkutils.creativetab;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabDarkUtils extends CreativeTabs {

    public CreativeTabDarkUtils () {

        super("darkutils");
        this.setBackgroundImageName("darkutils.png");
    }

    @Override
    public ItemStack getTabIconItem () {

        if (DarkUtils.REGISTRY.getBlocks().size() > 0) {
            return new ItemStack(Item.getItemFromBlock(DarkUtils.REGISTRY.getBlocks().get(0)));
        }

        else if (DarkUtils.REGISTRY.getItems().size() > 0) {
            return new ItemStack(DarkUtils.REGISTRY.getItems().get(0));
        }

        return new ItemStack(Items.DRAGON_BREATH);
    }

    @Override
    public boolean hasSearchBar () {

        return true;
    }
}