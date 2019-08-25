package net.darkhax.darkutils.features.slimecrucible;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
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
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * This class represents a slime crafting recipe. These recipes are crafted at a slime
 * crucible, using foot to generate slime which can be used in recipes.
 */
public class RecipeSlimeCrafting implements IRecipe<IInventory> {
    
    /**
     * An instance of the serializer for slime crafting recipes.
     */
    public static final Serializer SERIALIZER = new Serializer();
    
    /**
     * The input ingredient. This is the item (or items) required to craft the recipe.
     */
    private final Ingredient input;
    
    /**
     * The amount of items required for the recipe to be valid.
     */
    private final int inputCount;
    
    /**
     * The output item that the player receives when crafting this recipe.
     */
    private final ItemStack output;
    
    /**
     * The amount of slime points required to craft this recipe.
     */
    private final int points;
    
    /**
     * A namespaced Id associated with the recipe.
     */
    private final ResourceLocation id;
    
    /**
     * An array of slime crucible types that are able to craft this recipe.
     */
    private final SlimeCrucibleType[] validTypes;
    
    /**
     * Whether or not this recipe is hidden. Hidden recipes do not show up in the GUI unless
     * players have already provided the valid input item.
     */
    private final boolean isHidden;
    
    public RecipeSlimeCrafting(ResourceLocation id, Ingredient input, int inputCount, ItemStack output, int points, Boolean isHidden, SlimeCrucibleType... types) {
        
        this.id = id;
        this.input = input;
        this.inputCount = inputCount;
        this.output = output;
        this.points = points;
        this.validTypes = types;
        this.isHidden = isHidden;
    }
    
    @Override
    @Deprecated
    public boolean matches (IInventory inv, World worldIn) {
        
        // This method is not intended to be used. Use the various isValid methods instead.
        return this.input.test(inv.getStackInSlot(0));
    }
    
    @Override
    public ItemStack getCraftingResult (IInventory inv) {
        
        return this.output.copy();
    }
    
    @Override
    public ItemStack getRecipeOutput () {
        
        return this.output;
    }
    
    @Override
    public ResourceLocation getId () {
        
        return this.id;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer () {
        
        return DarkUtils.content.recipeSerializerSlimeCrafting;
    }
    
    @Override
    public IRecipeType<?> getType () {
        
        return DarkUtils.content.recipeTypeSlimeCrafting;
    }
    
    @Override
    public ItemStack getIcon () {
        
        return new ItemStack(Items.SLIME_BALL);
    }
    
    @Override
    public boolean isDynamic () {
        
        return true;
    }
    
    @Override
    public boolean canFit (int width, int height) {
        
        return true;
    }
    
    /**
     * Gets the amount of required slime points.
     * 
     * @return The amount of required slime points.
     */
    public int getSlimePoints () {
        
        return this.points;
    }
    
    /**
     * Gets an array of valid input items.
     * 
     * @return An array of valid inputs.
     */
    public ItemStack[] getValidItemStacks () {
        
        return this.input.getMatchingStacks();
    }
    
    /**
     * Checks if the recipe is hidden. Hidden recipes are not shown to the player unless the
     * player has already inserted the valid input.
     * 
     * @return Whether or not the recipe is hidden.
     */
    public boolean isHidden () {
        
        return this.isHidden;
    }
    
    /**
     * Gets the amount of the input item required to craft this recipe.
     * 
     * @return The required amount of the input item.
     */
    public int getInputCount () {
        
        return this.inputCount;
    }
    
    /**
     * Checks if a given input is valid for this recipe.
     * 
     * @param input The input to test.
     * @return Whether or not the input is valid for this recipe.
     */
    public boolean isValid (ItemStack input) {
        
        return input.getCount() >= this.getInputCount() && this.input.test(input);
    }
    
    /**
     * Checks if a slime crucible type is valid for this recipe.
     * 
     * @param slimeCrucibleType The crucible type to check.
     * @return Whether or not the crcuble type is valid.
     */
    public boolean isValid (SlimeCrucibleType slimeCrucibleType) {
        
        return slimeCrucibleType.matchesAny(this.validTypes);
    }
    
    /**
     * Checks if a recipe can be crafted using all required crafting context information.
     * 
     * @param input The input to validate.
     * @param slimeCrucibleType The crucible type to validate.
     * @param containedPoints The amount of available slime points.
     * @return Whether or not the recipe can be crafted in the provided context.
     */
    public boolean isValid (ItemStack input, SlimeCrucibleType slimeCrucibleType, int containedPoints) {
        
        return this.isValid(input) && this.isValid(slimeCrucibleType) && this.points <= containedPoints;
    }
    
    /**
     * A serializer which allows the recipe to be serialized from json files, and minecraft's
     * packet buffer.
     */
    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeSlimeCrafting> {
        
        @Override
        public RecipeSlimeCrafting read (ResourceLocation recipeId, JsonObject json) {
            
            final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
            final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            final Ingredient input = Ingredient.deserialize(inputElement);
            final int inputCount = JSONUtils.getInt(json, "inputCount", 1);
            final int points = JSONUtils.getInt(json, "points");
            final boolean isHidden = JSONUtils.getBoolean(json, "isHidden", false);
            final JsonArray typesArray = JSONUtils.getJsonArray(json, "validTypes");
            final Set<SlimeCrucibleType> types = new HashSet<>();
            
            for (int i = 0; i < typesArray.size(); i++) {
                
                final ResourceLocation id = new ResourceLocation(typesArray.get(i).getAsString());
                final SlimeCrucibleType type = SlimeCrucibleType.getType(id);
                
                if (type != null) {
                    
                    types.add(type);
                }
            }
            
            if (types.isEmpty()) {
                
                DarkUtils.LOG.warn("The slime food recipe {} has no valid types. It will not be obtainable. Some valid vanilla types are {}, {}, and {}.", recipeId, SlimeCrucibleType.ALL, SlimeCrucibleType.GREEN, SlimeCrucibleType.MAGMA);
            }
            
            return new RecipeSlimeCrafting(recipeId, input, inputCount, output, points, isHidden, types.toArray(new SlimeCrucibleType[types.size()]));
        }
        
        @Override
        public RecipeSlimeCrafting read (ResourceLocation recipeId, PacketBuffer buffer) {
            
            final Ingredient input = Ingredient.read(buffer);
            final int inputCount = buffer.readInt();
            final ItemStack output = buffer.readItemStack();
            final int points = buffer.readInt();
            final boolean isHidden = buffer.readBoolean();
            final SlimeCrucibleType[] types = new SlimeCrucibleType[buffer.readInt()];
            
            for (int i = 0; i < types.length; i++) {
                
                types[i] = SlimeCrucibleType.getType(buffer.readResourceLocation());
            }
            
            return new RecipeSlimeCrafting(recipeId, input, inputCount, output, points, isHidden, types);
        }
        
        @Override
        public void write (PacketBuffer buffer, RecipeSlimeCrafting recipe) {
            
            recipe.input.write(buffer);
            buffer.writeInt(recipe.inputCount);
            buffer.writeItemStack(recipe.output);
            buffer.writeInt(recipe.points);
            buffer.writeBoolean(recipe.isHidden);
            buffer.writeInt(recipe.validTypes.length);
            
            for (final SlimeCrucibleType type : recipe.validTypes) {
                
                buffer.writeResourceLocation(type.getRegistryName());
            }
        }
    }
}
