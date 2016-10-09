package net.darkhax.darkutils.libs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.misc.FeatureDisabled;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModUtils {
    
    /**
     * A list of all items from DarkUtils.
     */
    public static final List<Item> ITEMS = new ArrayList<>();
    
    /**
     * A list of all blocks from DarkUtils.
     */
    public static final List<Block> BLOCKS = new ArrayList<>();
    
    /**
     * Adds a basic conversion recipe. A conversion recipe converts one item directly into
     * another. Adds support for converting up to 9 items a time at a 1 to 1 ratio.
     * 
     * @param input: The input item.
     * @param output: The output item.
     */
    public static void addConversionRecipes (ItemStack input, ItemStack output) {
        
        for (int amount = 1; amount < 10; amount++) {
            
            final ItemStack[] inputs = new ItemStack[amount];
            Arrays.fill(inputs, input);
            GameRegistry.addShapelessRecipe(ItemStackUtils.copyStackWithSize(output, amount), (Object[]) inputs);
        }
    }
    
    /**
     * Provides the same functionality as older forge tile registration.
     * 
     * @param block The block to register.
     * @param ID The ID to register the block with.
     */
    public static Block registerBlock (Block block, String ID) {
        
        block.setRegistryName(ID);
        block.setCreativeTab(DarkUtils.TAB);
        block.setUnlocalizedName("darkutils." + ID.toLowerCase().replace("_", "."));
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), block.getRegistryName());
        BLOCKS.add(block);
        return block;
    }
    
    /**
     * Provides the same functionality as older forge tile registration.
     * 
     * @param block The block to register.
     * @param ID The ID to register the block with.
     */
    public static Block registerBlock (Block block, ItemBlock item, String ID) {
        
        block.setRegistryName(ID);
        block.setCreativeTab(DarkUtils.TAB);
        block.setUnlocalizedName("darkutils." + ID.toLowerCase().replace("_", "."));
        GameRegistry.register(block);
        GameRegistry.register(item, block.getRegistryName());
        BLOCKS.add(block);
        return block;
    }
    
    /**
     * Provides the same functionality as older forge item registration.
     * 
     * @param item The item to register.
     * @param ID The ID to register the item with.
     */
    public static Item registerItem (Item item, String ID) {
        
        if (item.getRegistryName() == null)
            item.setRegistryName(ID);
        
        item.setCreativeTab(DarkUtils.TAB);
        item.setUnlocalizedName("darkutils." + ID.toLowerCase().replace("_", "."));
        GameRegistry.register(item);
        ITEMS.add(item);
        return item;
    }
    
    public static Object validateCrafting (Object object) {
        
        if (object instanceof ItemStack)
            return ItemStackUtils.isValidStack((ItemStack) object) ? object : FeatureDisabled.itemDisabled;
        
        return object != null ? object : FeatureDisabled.itemDisabled;
    }
    
    /**
     * Registers inventory models for a block that uses meta data.
     * 
     * @param block The block to register models for.
     * @param variants The names of the models to use in order of meta data.
     */
    @SideOnly(Side.CLIENT)
    public static void registerBlockInvModel (Block block, String[] variants) {
        
        registerItemInvModel(Item.getItemFromBlock(block), variants);
    }
    
    /**
     * Registers inventory models for a block that uses meta data.
     * 
     * @param block The block to register models for.
     * @param prefix A prefix for the model names.
     * @param variants The names of the models to use in order of meta data.
     */
    @SideOnly(Side.CLIENT)
    public static void registerBlockInvModel (Block block, String prefix, String[] variants) {
        
        registerItemInvModel(Item.getItemFromBlock(block), prefix, variants);
    }
    
    /**
     * Registers inventory models for a block.
     * 
     * @param block The block to register models for.
     */
    @SideOnly(Side.CLIENT)
    public static void registerBlockInvModel (Block block) {
        
        registerItemInvModel(Item.getItemFromBlock(block), 0);
    }
    
    /**
     * Registers inventory models for a block.
     * 
     * @param block The block to register models for.
     * @param meta The meta data to register the model for.
     */
    @SideOnly(Side.CLIENT)
    public static void registerBlockInvModel (Block block, int meta) {
        
        registerItemInvModel(Item.getItemFromBlock(block), meta);
    }
    
    /**
     * Registers inventory models for an item.
     * 
     * @param item The item to register a model for.
     * @param meta The meta data to register the model for.
     * @param model The name of the model to register.
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemInvModel (Item item, int meta, String model) {
        
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(model, "inventory"));
    }
    
    /**
     * Registers inventory models for an item that uses meta data.
     * 
     * @param item The item to register a model for.
     * @param prefix A prefix to use on the variant names.
     * @param variants The names of the models to use, in order of meta data.
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemInvModel (Item item, String prefix, String[] variants) {
        
        for (int meta = 0; meta < variants.length; meta++)
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().getResourceDomain() + ":" + prefix + "_" + variants[meta], "inventory"));
    }
    
    /**
     * Registers inventory models for an item that uses meta data.
     * 
     * @param item The item to register a model for.
     * @param variants The names of the models to use, in order of meta data.
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemInvModel (Item item, String[] variants) {
        
        for (int meta = 0; meta < variants.length; meta++)
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().getResourceDomain() + ":" + variants[meta], "inventory"));
    }
    
    /**
     * Registers inventory models for an item.
     * 
     * @param item The item to registers a model for.
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemInvModel (Item item) {
        
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
    }
    
    /**
     * Registers inventory models for an item.
     * 
     * @param item The item to registers a model for.
     * @param meta The meta data to register the model for.
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemInvModel (Item item, int meta) {
        
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
    }
}
