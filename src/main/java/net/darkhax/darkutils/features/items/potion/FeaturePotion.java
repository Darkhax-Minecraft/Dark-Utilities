package net.darkhax.darkutils.features.items.potion;

import net.darkhax.darkutils.client.renderer.color.PotionColorHandler;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.Constants;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetDamage;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeaturePotion extends Feature {
    
    public static Item itemPotion;
    
    private static boolean dungeonLoot;
    private static int weight;
    
    @Override
    public void onPreInit () {
        
        itemPotion = new ItemMysteriousPotion();
        ModUtils.registerItem(itemPotion, "mystery_potion");
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        dungeonLoot = config.getBoolean("Dungeon Loot", this.configName, true, "Should mysterious potions show up in dungeon chests?");
        weight = config.getInt("Dungeon Weight", this.configName, 5, 0, 10, "Weight for potions in dungeon chests");
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @Override
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemPotion, 0, "bottle_drinkable");
        ModUtils.registerItemInvModel(itemPotion, 1, "bottle_drinkable");
    }
    
    @Override
    public void onClientInit () {
        
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new PotionColorHandler(), itemPotion);
    }
    
    @SubscribeEvent
    public void onLootTableLoad (LootTableLoadEvent event) {
        
        if (dungeonLoot) {
            
            final LootTable table = event.getTable();
            
            if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
                
                final LootPool main = table.getPool("main");
                
                if (main != null)
                    main.addEntry(new LootEntryItem(itemPotion, weight, 0, new LootFunction[] { new SetDamage(new LootCondition[0], new RandomValueRange(0, 1)) }, new LootCondition[0], Constants.MOD_ID + ":mysterious_potion"));
            }
        }
    }
}
