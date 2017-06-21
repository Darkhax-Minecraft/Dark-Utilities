package net.darkhax.darkutils.handler;

import java.util.Arrays;

import javax.annotation.Nonnull;

import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHandler {

    /**
     * Creates 9 recipes which allow an ItemStack to be converted into a different one. 9
     * recipes to allow up to 9 at a time.
     *
     * @param input The initial input item.
     * @param output The resulting item.
     */
    public static void createConversionRecipes (ItemStack input, ItemStack output) {

        for (int amount = 1; amount < 10; amount++) {

            final ItemStack[] inputs = new ItemStack[amount];
            Arrays.fill(inputs, input);
            addShapelessRecipe(StackUtils.copyStackWithSize(output, amount), (Object[]) inputs);
        }
    }

    public static IRecipe addShapedRecipe (@Nonnull Item output, Object... params) {

        return addShapedRecipe(new ItemStack(output), params);
    }

    public static void addShapelessRecipe (@Nonnull Item output, Object... params) {

        addShapelessRecipe(new ItemStack(output), params);
    }

    public static void addShapedOreRecipe (@Nonnull Item output, Object... params) {

        addShapedOreRecipe(new ItemStack(output), params);
    }

    public static void addShapelessOreRecipe (@Nonnull Item output, Object... params) {

        addShapelessOreRecipe(new ItemStack(output), params);
    }

    public static IRecipe addShapedRecipe (@Nonnull Block output, Object... params) {

        return addShapedRecipe(new ItemStack(output), params);
    }

    public static void addShapelessRecipe (@Nonnull Block output, Object... params) {

        addShapelessRecipe(new ItemStack(output), params);
    }

    public static void addShapedOreRecipe (@Nonnull Block output, Object... params) {

        addShapedOreRecipe(new ItemStack(output), params);
    }

    public static void addShapelessOreRecipe (@Nonnull Block output, Object... params) {

        addShapelessOreRecipe(new ItemStack(output), params);
    }

    public static IRecipe addShapedRecipe (@Nonnull ItemStack output, Object... params) {

        return GameRegistry.addShapedRecipe(output, params);
    }

    public static void addShapelessRecipe (@Nonnull ItemStack output, Object... params) {

        GameRegistry.addShapelessRecipe(output, params);
    }

    public static void addShapedOreRecipe (@Nonnull ItemStack output, Object... params) {

        GameRegistry.addRecipe(new ShapedOreRecipe(output, params));
    }

    public static void addShapelessOreRecipe (@Nonnull ItemStack output, Object... params) {

        GameRegistry.addRecipe(new ShapelessOreRecipe(output, params));
    }
}
