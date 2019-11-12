package net.darkhax.darkutils.features.dust;

import java.util.Random;

import com.google.gson.JsonObject;

import net.darkhax.bookshelf.crafting.block.BlockIngredient;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeDustChange implements IRecipe<IInventory> {
    
    public static final Serializer SERIALIZER = new Serializer();
    
    private final ResourceLocation id;
    private final Ingredient item;
    private final BlockIngredient input;
    private final BlockIngredient output;
    
    public RecipeDustChange(ResourceLocation id, Ingredient item, BlockIngredient input, BlockIngredient output) {
        
        this.id = id;
        this.item = item;
        this.input = input;
        this.output = output;
    }
    
    @Override
    @Deprecated
    public boolean matches (IInventory inv, World worldIn) {
        
        return false;
    }
    
    @Override
    @Deprecated
    public ItemStack getCraftingResult (IInventory inv) {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    @Deprecated
    public ItemStack getRecipeOutput () {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public ResourceLocation getId () {
        
        return this.id;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer () {
        
        return DarkUtils.content.recipeSerializerDustChange;
    }
    
    @Override
    public IRecipeType<?> getType () {
        
        return DarkUtils.content.recipeTypeDustChange;
    }
    
    @Override
    public ItemStack getIcon () {
        
        return new ItemStack(DarkUtils.content.dustPurify);
    }
    
    @Override
    public boolean isDynamic () {
        
        return true;
    }
    
    @Override
    public boolean canFit (int width, int height) {
        
        return true;
    }
    
    public Block getOutput (Random rand) {
        
        return this.output.getValidBlocks().get(rand.nextInt(this.output.getValidBlocks().size()));
    }
    
    public boolean isValid (BlockState state, ItemStack dustItem) {
        
        return this.item.test(dustItem) && this.input.test(state.getBlock()) && !this.output.getValidBlocks().isEmpty();
    }
    
    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeDustChange> {
        
        @Override
        public RecipeDustChange read (ResourceLocation recipeId, JsonObject json) {
            
            final Ingredient item = Ingredient.deserialize(json.getAsJsonObject("item"));
            final BlockIngredient input = BlockIngredient.deserialize(json.getAsJsonObject("input"));
            final BlockIngredient output = BlockIngredient.deserialize(json.getAsJsonObject("output"));
            return new RecipeDustChange(recipeId, item, input, output);
        }
        
        @Override
        public RecipeDustChange read (ResourceLocation recipeId, PacketBuffer buffer) {
            
            final Ingredient item = Ingredient.read(buffer);
            final BlockIngredient input = BlockIngredient.deserialize(buffer);
            final BlockIngredient output = BlockIngredient.deserialize(buffer);
            return new RecipeDustChange(recipeId, item, input, output);
        }
        
        @Override
        public void write (PacketBuffer buffer, RecipeDustChange recipe) {
            
            recipe.item.write(buffer);
            recipe.input.serialize(buffer);
            recipe.output.serialize(buffer);
        }
    }
}
