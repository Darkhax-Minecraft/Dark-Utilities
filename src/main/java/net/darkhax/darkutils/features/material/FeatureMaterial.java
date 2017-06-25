package net.darkhax.darkutils.features.material;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
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
import net.minecraftforge.oredict.OreDictionary;

@DUFeature(name = "Crafting Materials", description = "Material items used throughout DarkUtils")
public class FeatureMaterial extends Feature {

    public static Item itemMaterial;

    public static Block blockWitherDust;

    private static boolean skeletonDropDust = true;

    private static int dustDropWeight = 1;

    @Override
    public void onRegistry () {

        itemMaterial = DarkUtils.REGISTRY.registerItem(new ItemMaterial(), "material");
        blockWitherDust = new BlockWitherDust();
        DarkUtils.REGISTRY.registerBlock(blockWitherDust, new ItemBlockBasic(blockWitherDust, BlockWitherDust.types, false), "wither_block");
        OreDictionary.registerOre("blockWither", new ItemStack(blockWitherDust, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public void setupConfiguration (Configuration config) {

        skeletonDropDust = config.getBoolean("WSkeleton Drop Dust", this.configName, true, "Should wither skeletons drop wither dust?");
        dustDropWeight = config.getInt("Dust Drop Weight", this.configName, 1, 0, 256, "The weighting for Wither Skeletons dropping Wither Dust");
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

            if (pool1 != null) {
                pool1.addEntry(new LootEntryItem(itemMaterial, dustDropWeight, 0, new LootFunction[0], new LootCondition[0], DarkUtils.MOD_ID + ":wither_dust"));
            }
        }
    }
}
