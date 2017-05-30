package net.darkhax.darkutils.features.vector;

import static net.darkhax.bookshelf.util.OreDictUtils.SLIMEBALL;
import static net.darkhax.bookshelf.util.OreDictUtils.STONE;

import net.darkhax.bookshelf.util.CraftingUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@DUFeature(name = "Vector Plate", description = "A block that pushes entities around")
public class FeatureVectorPlate extends Feature {

    public static Block blockVectorPlate;

    public static Block blockFastVectorPlate;

    public static Block blockHyperVectorPlate;

    public static Block blockUndergroundVectorPlate;

    public static Block blockUndergroundFastVectorPlate;

    public static Block blockUndergroundHyperVectorPlate;

    private static boolean craftVectorPlate = true;

    protected static boolean preventItemDespawn = true;

    protected static boolean preventItemPickup = true;

    private static double normalSpeed = 0.06d;

    private static double fastSpeed = 0.3d;

    private static double hyperSpeed = 1.5d;

    @Override
    public void onRegistry () {

        blockVectorPlate = new BlockVectorPlate(normalSpeed);
        DarkUtils.REGISTRY.registerBlock(blockVectorPlate, "trap_move");

        blockFastVectorPlate = new BlockVectorPlate(fastSpeed);
        DarkUtils.REGISTRY.registerBlock(blockFastVectorPlate, "trap_move_fast");

        blockHyperVectorPlate = new BlockVectorPlate(hyperSpeed);
        DarkUtils.REGISTRY.registerBlock(blockHyperVectorPlate, "trap_move_hyper");
        /**
         * blockUndergroundVectorPlate = new BlockUndergroundVectorPlate(normalSpeed);
         * DarkUtils.REGISTRY.registerBlock(blockUndergroundVectorPlate,
         * "trap_underground_move");
         *
         * blockUndergroundFastVectorPlate = new BlockUndergroundVectorPlate(fastSpeed);
         * DarkUtils.REGISTRY.registerBlock(blockUndergroundFastVectorPlate,
         * "trap_underground_move_fast");
         *
         * blockUndergroundHyperVectorPlate = new BlockUndergroundVectorPlate(hyperSpeed);
         * DarkUtils.REGISTRY.registerBlock(blockUndergroundHyperVectorPlate,
         * "trap_underground_move_hyper");
         **/
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftVectorPlate = config.getBoolean("Craft Vector Plate", this.configName, true, "Should the Vector Plate be craftable?");
        preventItemDespawn = config.getBoolean("Prevent Item Despawn", this.configName, true, "Should vector plates prevent item despawn?");
        preventItemPickup = config.getBoolean("Prevent Item Pickup", this.configName, true, "Should vector plates prevent items from being picked up, while they are being pushed?");
        normalSpeed = config.getFloat("Normal Speed", this.configName, 0.06f, 0f, 5f, "Speed modifier for the normal vector plate");
        fastSpeed = config.getFloat("Fast Speed", this.configName, 0.3f, 0f, 5f, "Speed modifier for the fast vector plate");
        hyperSpeed = config.getFloat("Hyper Speed", this.configName, 1.5f, 0f, 5f, "Speed modifier for the hyper vector plate");
    }

    @Override
    public void setupRecipes () {

        if (craftVectorPlate) {

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockVectorPlate, 8), new Object[] { "isi", "bfb", 's', SLIMEBALL, 'b', STONE, 'f', Items.SUGAR }));
            GameRegistry.addShapedRecipe(new ItemStack(blockFastVectorPlate, 8), "xxx", "xyx", "xxx", 'x', blockVectorPlate, 'y', CraftingUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 3)));
            GameRegistry.addShapedRecipe(new ItemStack(blockHyperVectorPlate, 8), "xxx", "xyx", "xxx", 'x', blockFastVectorPlate, 'y', CraftingUtils.validateCrafting(new ItemStack(FeatureMaterial.blockWitherDust, 1, OreDictionary.WILDCARD_VALUE)));
        }
    }
}
