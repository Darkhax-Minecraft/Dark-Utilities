package net.darkhax.darkutils.features.slimecrucible;

import net.minecraft.item.crafting.Ingredient;

public interface ISlimeRecipe {

    boolean canCraft (Ingredient catalyst);
}