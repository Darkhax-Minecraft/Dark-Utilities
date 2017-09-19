package net.darkhax.darkutils.features.loretag;

import java.util.HashMap;
import java.util.Map;

import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeLoreTag extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public static final Map<Integer, String> MODIFIERS = new HashMap<>();

    static {

        MODIFIERS.put(0, "black");
        MODIFIERS.put(4, "dark_blue");
        MODIFIERS.put(2, "dark_green");
        MODIFIERS.put(6, "dark_aqua");
        MODIFIERS.put(1, "dark_red");
        MODIFIERS.put(5, "dark_purple");
        MODIFIERS.put(14, "gold");
        MODIFIERS.put(7, "gray");
        MODIFIERS.put(8, "dark_gray");
        // MODIFIERS.put(, "blue");
        MODIFIERS.put(10, "green");
        MODIFIERS.put(12, "aqua");
        MODIFIERS.put(9, "red");
        MODIFIERS.put(13, "light_purple");
        MODIFIERS.put(11, "yellow");
        MODIFIERS.put(15, "white");
    }

    @Override
    public boolean matches (InventoryCrafting inv, World worldIn) {

        int loreTagCount = 0;
        int colorModifierCount = 0;

        for (int k = 0; k < inv.getSizeInventory(); ++k) {

            final ItemStack itemstack = inv.getStackInSlot(k);

            if (!itemstack.isEmpty()) {

                if (itemstack.getItem() instanceof ItemFormatLoreTag) {
                    loreTagCount++;
                }

                else if (itemstack.getItem() instanceof ItemDye && MODIFIERS.containsKey(itemstack.getMetadata())) {

                    colorModifierCount++;
                }

                else {

                    return false;
                }

                if (colorModifierCount > 1 || loreTagCount > 1) {

                    return false;
                }
            }
        }

        return loreTagCount == 1 && colorModifierCount == 1;
    }

    @Override
    public ItemStack getCraftingResult (InventoryCrafting inv) {

        ItemStack tagStack = ItemStack.EMPTY;
        ItemStack modifierStack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {

            final ItemStack slotStack = inv.getStackInSlot(i);

            if (!slotStack.isEmpty()) {

                if (slotStack.getItem() instanceof ItemFormatLoreTag) {
                    tagStack = slotStack;
                }

                else if (slotStack.getItem() instanceof ItemDye && MODIFIERS.containsKey(slotStack.getMetadata())) {

                    modifierStack = slotStack;
                }
            }
        }

        final ItemStack output = new ItemStack(tagStack.getItem());
        final NBTTagCompound tag = StackUtils.prepareStackTag(output);
        tag.setString("format", MODIFIERS.get(modifierStack.getMetadata()));

        return output;
    }

    @Override
    public ItemStack getRecipeOutput () {

        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems (InventoryCrafting inv) {

        final NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack> withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            final ItemStack itemstack = inv.getStackInSlot(i);

            if (itemstack.getItem().hasContainerItem()) {
                nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
            }
        }

        return nonnulllist;
    }

    @Override
    public boolean canFit (int width, int height) {

        return width * height >= 2;
    }

    @Override
    public boolean isDynamic () {

        return true;
    }
}