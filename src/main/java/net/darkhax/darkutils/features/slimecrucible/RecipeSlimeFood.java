package net.darkhax.darkutils.features.slimecrucible;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
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

public class RecipeSlimeFood implements IRecipe<IInventory> {
    
    public static final Serializer SERIALIZER = new Serializer();
    
    private final Ingredient input;
    private final int points;
    private final ResourceLocation id;
    private final SlimeCrucibleType[] validTypes;
    
    public RecipeSlimeFood(ResourceLocation id, Ingredient input, int points, SlimeCrucibleType... types) {
        
        this.id = id;
        this.input = input;
        this.points = points;
        this.validTypes = types;
    }
    
    @Override
    public boolean matches (IInventory inv, World worldIn) {
        
        return this.input.test(inv.getStackInSlot(0));
    }
    
    @Override
    public ItemStack getCraftingResult (IInventory inv) {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public ItemStack getRecipeOutput () {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public ResourceLocation getId () {
        
        return this.id;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer () {
        
        return DarkUtils.content.recipeSerializerSlimeFood;
    }
    
    @Override
    public IRecipeType<?> getType () {
        
        return DarkUtils.content.recipeTypeSlimeFood;
    }
    
    @Override
    public ItemStack getIcon () {
        
        return new ItemStack(Items.SLIME_BALL);
    }
    
    public int getSlimePoints () {
        
        return this.points;
    }
    
    @Override
    public boolean isDynamic () {
        
        return true;
    }
    
    public boolean isValid (ItemStack input, Block block) {
        
        if (block instanceof ISlimeCrucibleBlock) {
            
            final SlimeCrucibleType slimeCrucibleType = ((ISlimeCrucibleBlock) block).getCrucibleType();
            
            return slimeCrucibleType.matchesAny(this.validTypes) && this.input.test(input);
        }
        
        return false;
    }
    
    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeSlimeFood> {
        
        @Override
        public RecipeSlimeFood read (ResourceLocation recipeId, JsonObject json) {
            
            final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
            final Ingredient input = Ingredient.deserialize(inputElement);
            final int points = JSONUtils.getInt(json, "points");
            
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
            
            return new RecipeSlimeFood(recipeId, input, points, types.toArray(new SlimeCrucibleType[types.size()]));
        }
        
        @Override
        public RecipeSlimeFood read (ResourceLocation recipeId, PacketBuffer buffer) {
            
            final Ingredient input = Ingredient.read(buffer);
            final int points = buffer.readInt();
            final SlimeCrucibleType[] types = new SlimeCrucibleType[buffer.readInt()];
            
            for (int i = 0; i < types.length; i++) {
                
                types[i] = SlimeCrucibleType.getType(buffer.readResourceLocation());
            }
            
            return new RecipeSlimeFood(recipeId, input, points, types);
        }
        
        @Override
        public void write (PacketBuffer buffer, RecipeSlimeFood recipe) {
            
            recipe.input.write(buffer);
            buffer.writeInt(recipe.points);
            buffer.writeInt(recipe.validTypes.length);
            
            for (final SlimeCrucibleType type : recipe.validTypes) {
                
                buffer.writeResourceLocation(type.getRegistryName());
            }
        }
    }
}
