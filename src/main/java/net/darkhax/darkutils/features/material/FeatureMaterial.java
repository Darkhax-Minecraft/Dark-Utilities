package net.darkhax.darkutils.features.material;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.oredict.OreDictionary;

@DUFeature(name = "Crafting Materials", description = "Material items used throughout DarkUtils")
public class FeatureMaterial extends Feature {

    public static Item itemMaterial = new ItemMaterial();

    public static Block blockWitherDust = new BlockWitherDust();

    private static boolean skeletonDropDust = true;

    private static int dustDropWeight = 1;

    @Override
    public void onPreInit () {

        itemMaterial = DarkUtils.REGISTRY.registerItem(itemMaterial, "material");
        DarkUtils.REGISTRY.registerBlock(blockWitherDust, new ItemBlockBasic(blockWitherDust, BlockWitherDust.types, false), "wither_block");
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemMaterial, new BehaviorDispenseWitherDust());
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapelessRecipe("skull_to_witherdust", new ItemStack(itemMaterial, 3, 0), new ItemStack(Items.SKULL, 1, 1));
        DarkUtils.REGISTRY.addShapelessRecipe("dwindlecream", new ItemStack(itemMaterial, 1, 2), new ItemStack(itemMaterial, 1, 0), OreDictUtils.SLIMEBALL);
        DarkUtils.REGISTRY.addShapelessRecipe("unstable_pearl", new ItemStack(itemMaterial, 1, 1), new ItemStack(itemMaterial, 1, 0), OreDictUtils.ENDERPEARL);
        DarkUtils.REGISTRY.addShapedRecipe("dark_sugar", new ItemStack(itemMaterial, 8, 3), "xxx", "xyx", "xxx", 'x', Items.SUGAR, 'y', itemMaterial);
        DarkUtils.REGISTRY.addShapedRecipe("soulsand", new ItemStack(Blocks.SOUL_SAND, 5, 0), "xyx", "yxy", "xyx", 'x', OreDictUtils.SAND, 'y', itemMaterial);

        DarkUtils.REGISTRY.addShapedRecipe("compact_wither", new ItemStack(blockWitherDust, 1, 0), "xx", "xx", 'x', new ItemStack(FeatureMaterial.itemMaterial, 1, 0));
        DarkUtils.REGISTRY.addShapelessRecipe("deconstruct_wither", new ItemStack(itemMaterial, 4, 0), new ItemStack(blockWitherDust, 3, OreDictionary.WILDCARD_VALUE));
        DarkUtils.REGISTRY.addShapedRecipe("convert_wither_1", new ItemStack(blockWitherDust, 4, 1), "xx", "xx", 'x', new ItemStack(blockWitherDust, 1, 0));
        DarkUtils.REGISTRY.addShapedRecipe("convert_wither_2", new ItemStack(blockWitherDust, 4, 2), "xx", "xx", 'x', new ItemStack(blockWitherDust, 1, 1));
        DarkUtils.REGISTRY.addShapedRecipe("convert_wither_3", new ItemStack(blockWitherDust, 4, 3), "xx", "xx", 'x', new ItemStack(blockWitherDust, 1, 2));
        DarkUtils.REGISTRY.addShapedRecipe("convert_wither_4", new ItemStack(blockWitherDust, 4, 4), "xx", "xx", 'x', new ItemStack(blockWitherDust, 1, 3));
        DarkUtils.REGISTRY.addShapedRecipe("convert_wither_5", new ItemStack(blockWitherDust, 4, 5), "xx", "xx", 'x', new ItemStack(blockWitherDust, 1, 4));
        DarkUtils.REGISTRY.addShapedRecipe("convert_wither_6", new ItemStack(blockWitherDust, 4, 0), "xx", "xx", 'x', new ItemStack(blockWitherDust, 1, 5));
    }

    @Override
    public void onInit () {

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
