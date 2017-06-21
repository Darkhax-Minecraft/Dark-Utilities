package net.darkhax.darkutils.features.material;

import static net.darkhax.bookshelf.util.OreDictUtils.ENDERPEARL;
import static net.darkhax.bookshelf.util.OreDictUtils.SLIMEBALL;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.handler.RecipeHandler;
import net.darkhax.darkutils.libs.Constants;
import net.minecraft.block.Block;
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

    public static Item itemMaterial;

    public static Block blockWitherDust;

    private static boolean craftDustFromSkull = true;

    private static boolean craftDwindleCream = true;

    private static boolean craftUnstableEnderPearl = true;

    private static boolean skeletonDropDust = true;

    private static int dustDropWeight = 1;

    private static boolean craftBlocks = true;

    private static boolean craftDarkSugar = true;

    private static boolean craftSoulSand = true;

    @Override
    public void onRegistry () {

        itemMaterial = DarkUtils.REGISTRY.registerItem(new ItemMaterial(), "material");
        blockWitherDust = new BlockWitherDust();
        DarkUtils.REGISTRY.registerBlock(blockWitherDust, new ItemBlockBasic(blockWitherDust, BlockWitherDust.types, false), "wither_block");
        OreDictionary.registerOre("blockWither", new ItemStack(blockWitherDust, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftDustFromSkull = config.getBoolean("Craft Wither Dust", this.configName, true, "Should the Wither Dust be craftable from Wither Skulls?");
        craftDwindleCream = config.getBoolean("Craft Dwindle Cream", this.configName, true, "Should Dwingle Cream be craftable?");
        craftUnstableEnderPearl = config.getBoolean("Craft Unstable Enderpearl", this.configName, true, "Should Unstable Enderpearls be craftable?");
        skeletonDropDust = config.getBoolean("WSkeleton Drop Dust", this.configName, true, "Should wither skeletons drop wither dust?");
        dustDropWeight = config.getInt("Dust Drop Weight", this.configName, 1, 0, 256, "The weighting for Wither Skeletons dropping Wither Dust");
        craftBlocks = config.getBoolean("Craft Blocks", this.configName, true, "Can wither dust blocks be crafted?");
        craftDarkSugar = config.getBoolean("Craft Dark Sugar", this.configName, true, "Should dark sugar be craftable?");
        craftSoulSand = config.getBoolean("Craft Soul Sand", this.configName, true, "Should soul sand be craftable with wither dust?");
    }

    @Override
    public void setupRecipes () {

        if (craftDustFromSkull) {
            RecipeHandler.addShapelessRecipe(new ItemStack(itemMaterial, 3, 0), new ItemStack(Items.SKULL, 1, 1));
        }

        if (craftDwindleCream) {
            RecipeHandler.addShapelessOreRecipe(new ItemStack(itemMaterial, 1, 2), new ItemStack(itemMaterial, 1, 0), SLIMEBALL);
        }

        if (craftUnstableEnderPearl) {
            RecipeHandler.addShapelessOreRecipe(new ItemStack(itemMaterial, 1, 1), new ItemStack(itemMaterial, 1, 0), ENDERPEARL);
        }

        if (craftDarkSugar) {
            RecipeHandler.addShapedRecipe(new ItemStack(itemMaterial, 8, 3), "xxx", "xyx", "xxx", 'x', Items.SUGAR, 'y', itemMaterial);
        }

        if (craftSoulSand) {
            RecipeHandler.addShapedOreRecipe(new ItemStack(Blocks.SOUL_SAND, 5, 0), "xyx", "yxy", "xyx", 'x', OreDictUtils.SAND, 'y', itemMaterial);
        }

        if (craftBlocks) {

            RecipeHandler.addShapedRecipe(new ItemStack(blockWitherDust, 1, 0), "xx ", "xx ", 'x', FeatureMaterial.itemMaterial);
            RecipeHandler.addShapelessRecipe(new ItemStack(itemMaterial, 4, 0), new ItemStack(blockWitherDust, 3, OreDictionary.WILDCARD_VALUE));
            RecipeHandler.addShapedRecipe(new ItemStack(blockWitherDust, 4, 1), "xx ", "xx ", 'x', new ItemStack(blockWitherDust, 1, 0));
            RecipeHandler.addShapedRecipe(new ItemStack(blockWitherDust, 4, 2), "xx ", "xx ", 'x', new ItemStack(blockWitherDust, 1, 1));
            RecipeHandler.addShapedRecipe(new ItemStack(blockWitherDust, 4, 3), "xx ", "xx ", 'x', new ItemStack(blockWitherDust, 1, 2));
            RecipeHandler.addShapedRecipe(new ItemStack(blockWitherDust, 4, 4), "xx ", "xx ", 'x', new ItemStack(blockWitherDust, 1, 3));
            RecipeHandler.addShapedRecipe(new ItemStack(blockWitherDust, 4, 5), "xx ", "xx ", 'x', new ItemStack(blockWitherDust, 1, 4));
            RecipeHandler.addShapedRecipe(new ItemStack(blockWitherDust, 4, 0), "xx ", "xx ", 'x', new ItemStack(blockWitherDust, 1, 5));
        }
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
                pool1.addEntry(new LootEntryItem(itemMaterial, dustDropWeight, 0, new LootFunction[0], new LootCondition[0], Constants.MOD_ID + ":wither_dust"));
            }
        }
    }
}
