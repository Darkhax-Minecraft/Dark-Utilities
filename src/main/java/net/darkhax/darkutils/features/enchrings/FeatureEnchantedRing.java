package net.darkhax.darkutils.features.enchrings;

import net.darkhax.bookshelf.events.EnchantmentModifierEvent;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetDamage;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@DUFeature(name = "Enchanted Rings", description = "Adds rings which can increase enchantment levels")
public class FeatureEnchantedRing extends Feature {

    public static Item itemRing;

    protected static boolean allowBaubles;

    private static boolean allowDungeonLoot;

    private static boolean allowStacking;

    private static int weight;

    @Override
    public void onRegistry () {

        itemRing = DarkUtils.REGISTRY.registerItem(new ItemRing(), "ring");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        allowDungeonLoot = config.getBoolean("RingLoot", this.configName, true, "Allow rings to show up in nether bridge chests?");
        allowStacking = config.getBoolean("RingStacking", this.configName, true, "Should players be allowed to use multiple of the same rings? IE Baubles");
        allowBaubles = config.getBoolean("RingBaubles", this.configName, true, "Allow rings in the bauble slots?");
        weight = config.getInt("DungeonWeight", this.configName, 1, 1, 1000, "The weight of the rings in a loot chest");
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void getEnchantmentLevel (EnchantmentModifierEvent event) {

        int levels = this.handleRing(event.getEntity().getHeldItemOffhand(), event.getEnchantment());

        //TOOD baubles

        if (levels > 0) {

            event.setCanceled(true);
            event.setLevels(event.getLevels() + (allowStacking ? levels : 1));
        }
    }

    @SubscribeEvent
    public void onLootTableLoad (LootTableLoadEvent event) {

        if (allowDungeonLoot && event.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)) {

            final LootPool main = event.getTable().getPool("main");

            if (main != null) {
                main.addEntry(new LootEntryItem(itemRing, weight, 0, new LootFunction[] { new SetDamage(new LootCondition[0], new RandomValueRange(0, ItemRing.varients.length - 1)) }, new LootCondition[0], "darkutils:nether_rings"));
            }
        }
    }

    private int handleRing (ItemStack stack, Enchantment enchant) {

        return stack != null && stack.getItem() instanceof ItemRing && enchant == ItemRing.getEnchantmentFromMeta(stack.getMetadata()) ? 1 : 0;
    }
}
