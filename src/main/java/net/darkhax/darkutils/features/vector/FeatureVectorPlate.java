package net.darkhax.darkutils.features.vector;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

@DUFeature(name = "Vector Plate", description = "A block that pushes entities around")
public class FeatureVectorPlate extends Feature {

    public static Block blockVectorPlate;

    public static Block blockFastVectorPlate;

    public static Block blockHyperVectorPlate;

    public static Block blockUndergroundVectorPlate;

    public static Block blockUndergroundFastVectorPlate;

    public static Block blockUndergroundHyperVectorPlate;

    protected static boolean preventItemDespawn = true;

    protected static boolean preventItemPickup = true;

    private static double normalSpeed = 0.06d;

    private static double fastSpeed = 0.3d;

    private static double hyperSpeed = 1.5d;

    @Override
    public void onPreInit () {

        blockVectorPlate = new BlockVectorPlate(normalSpeed);
        DarkUtils.REGISTRY.registerBlock(blockVectorPlate, "trap_move");

        blockFastVectorPlate = new BlockVectorPlate(fastSpeed);
        DarkUtils.REGISTRY.registerBlock(blockFastVectorPlate, "trap_move_fast");

        blockHyperVectorPlate = new BlockVectorPlate(hyperSpeed);
        DarkUtils.REGISTRY.registerBlock(blockHyperVectorPlate, "trap_move_hyper");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        preventItemDespawn = config.getBoolean("Prevent Item Despawn", this.configName, true, "Should vector plates prevent item despawn?");
        preventItemPickup = config.getBoolean("Prevent Item Pickup", this.configName, true, "Should vector plates prevent items from being picked up, while they are being pushed?");
        normalSpeed = config.getFloat("Normal Speed", this.configName, 0.06f, 0f, 5f, "Speed modifier for the normal vector plate");
        fastSpeed = config.getFloat("Fast Speed", this.configName, 0.3f, 0f, 5f, "Speed modifier for the fast vector plate");
        hyperSpeed = config.getFloat("Hyper Speed", this.configName, 1.5f, 0f, 5f, "Speed modifier for the hyper vector plate");
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("vector_normal", new ItemStack(blockVectorPlate, 8), "isi", "bfb", 's', OreDictUtils.SLIMEBALL, 'b', OreDictUtils.STONE, 'f', Items.SUGAR, 'i', OreDictUtils.INGOT_IRON);
        DarkUtils.REGISTRY.addShapedRecipe("vector_fast", new ItemStack(blockFastVectorPlate, 8), "xxx", "xyx", "xxx", 'x', blockVectorPlate, 'y', new ItemStack(FeatureMaterial.itemMaterial, 1, 3));
        DarkUtils.REGISTRY.addShapedRecipe("vector_hyper", new ItemStack(blockHyperVectorPlate, 8), "xxx", "xyx", "xxx", 'x', blockFastVectorPlate, 'y', new ItemStack(FeatureMaterial.blockWitherDust, 1, OreDictionary.WILDCARD_VALUE));
    }
}
