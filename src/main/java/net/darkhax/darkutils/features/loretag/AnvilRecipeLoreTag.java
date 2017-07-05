package net.darkhax.darkutils.features.loretag;

import java.util.List;

import net.darkhax.bookshelf.crafting.IAnvilRecipe;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.item.ItemStack;

public class AnvilRecipeLoreTag implements IAnvilRecipe {

    @Override
    public boolean isValidRecipe (ItemStack leftSlot, ItemStack rightSlot, String name) {

        return rightSlot.getItem() instanceof ItemFormatLoreTag;
    }

    @Override
    public int getExperienceCost (ItemStack leftSlot, ItemStack rightSlot, String name) {

        return 1;
    }

    @Override
    public int getMaterialCost (ItemStack leftSlot, ItemStack rightSlot, String name) {

        return 1;
    }

    @Override
    public ItemStack getOutput (ItemStack leftSlot, ItemStack rightSlot, String name) {

        final List<String> lore = StackUtils.getLore(rightSlot);
        final ItemStack output = leftSlot.copy();

        for (final String loreText : lore) {

            StackUtils.appendLore(output, loreText);
        }
        return output;
    }
}
