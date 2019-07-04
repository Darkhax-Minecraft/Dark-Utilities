package net.darkhax.darkutils.features.slimecrucible;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SlimeFoodRecipe implements IRecipe<IInventory> {

	public static final Serializer SERIALIZER = new Serializer();
	
	private final Ingredient input;
	private final float points;
	private final ResourceLocation id;
	
	public SlimeFoodRecipe(Ingredient input, float points, ResourceLocation id) {
		
		this.input = input;
		this.points = points;
		this.id = id;
	}
	
	@Override
	public boolean matches(IInventory inv, World worldIn) {

		return this.input.test(inv.getStackInSlot(0));
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getRecipeOutput() {

		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId() {
		
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		
		return DarkUtils.content.recipeSerializerSlimeFood;
	}

	@Override
	public IRecipeType<?> getType() {
		
		return DarkUtils.content.recipeTypeSlimeFood;
	}

	@Override
	public ItemStack getIcon() {
		
		return new ItemStack(Items.SLIME_BALL);
	}
	
	public float getSlimePoints() {
		
		return this.points;
	}
	
	@Override
	public boolean isDynamic() {
		
		return true;
	}
	
	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SlimeFoodRecipe> {
		
		@Override
		public SlimeFoodRecipe read(ResourceLocation recipeId, JsonObject json) {

		      final JsonElement inputElement = (JsonElement)(JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input"));
		      final Ingredient input = Ingredient.deserialize(inputElement);
		      final float points = JSONUtils.getFloat(json, "points");
		      return new SlimeFoodRecipe(input, points, recipeId);
		}

		@Override
		public SlimeFoodRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

			final Ingredient input = Ingredient.read(buffer);
			final float points = buffer.readFloat();
			return new SlimeFoodRecipe(input, points, recipeId);
		}

		@Override
		public void write(PacketBuffer buffer, SlimeFoodRecipe recipe) {

			recipe.input.write(buffer);
			buffer.writeFloat(recipe.points);
		}
	}
}
