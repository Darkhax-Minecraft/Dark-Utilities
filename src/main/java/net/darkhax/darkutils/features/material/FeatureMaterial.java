package net.darkhax.darkutils.features.material;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.ENDERPEARL;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.SLIMEBALL;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.Constants;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class FeatureMaterial extends Feature {
    
    public static Item itemMaterial;
    
    private static boolean craftDustFromSkull = true;
    private static boolean craftDwindleCream = true;
    private static boolean craftUnstableEnderPearl = true;
    private static boolean skeletonDropDust = true;
    private static int dustDropWeight = 1;
    
    @Override
    public void onPreInit () {
        
        itemMaterial = new ItemMaterial();
        ModUtils.registerItem(itemMaterial, "material");
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftDustFromSkull = config.getBoolean("Craft Wither Dust", this.configName, true, "Should the Wither Dust be craftable from Wither Skulls?");
        craftDwindleCream = config.getBoolean("Craft Dwindle Cream", this.configName, true, "Should Dwingle Cream be craftable?");
        craftUnstableEnderPearl = config.getBoolean("Craft Unstable Enderpealr", this.configName, true, "Should Unstable Enderpearls be craftable?");
        skeletonDropDust = config.getBoolean("WSkeleton Drop Dust", this.configName, true, "Should wither skeletons drop wither dust?");
        
        dustDropWeight = config.getInt("Dust Drop Weight", this.configName, 1, 0, 256, "The weighting for Wither Skeletons dropping Wither Dust");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftDustFromSkull)
            GameRegistry.addShapelessRecipe(new ItemStack(itemMaterial, 3, 0), new ItemStack(Items.SKULL, 1, 1));
            
        if (craftDwindleCream)
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(itemMaterial, 1, 2), new ItemStack(itemMaterial, 1, 0), SLIMEBALL));
            
        if (craftUnstableEnderPearl)
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(itemMaterial, 1, 1), new ItemStack(itemMaterial, 1, 0), ENDERPEARL));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemMaterial, "material", ItemMaterial.varients);
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @SubscribeEvent
    public void onLootTableLoad (LootTableLoadEvent event) {
        
        final LootTable table = event.getTable();
        
        if (skeletonDropDust && event.getName().equals(LootTableList.ENTITIES_WITHER_SKELETON)) {
            
            final LootPool pool1 = table.getPool("pool1");
            
            if (pool1 != null)
                pool1.addEntry(new LootEntryItem(itemMaterial, dustDropWeight, 0, new LootFunction[0], new LootCondition[0], Constants.MOD_ID + ":wither_dust"));
        }
    }
}
