package net.darkhax.darkutils.features.vector;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.SLIMEBALL;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureVectorPlate extends Feature {

    public static Block blockVectorPlate;

    public static Block blockFastVectorPlate;

    public static Block blockHyperVectorPlate;

    private static boolean craftVectorPlate = true;

    protected static boolean preventItemDespawn = true;

    protected static boolean preventItemPickup = true;

    private static double normalSpeed = 0.06d;

    private static double fastSpeed = 0.3d;

    private static double hyperSpeed = 1.5d;

    @Override
    public void onPreInit () {

        blockVectorPlate = new BlockVectorPlate(normalSpeed);
        ModUtils.registerBlock(blockVectorPlate, "trap_move");

        blockFastVectorPlate = new BlockVectorPlate(fastSpeed);
        ModUtils.registerBlock(blockFastVectorPlate, "trap_move_fast");

        blockHyperVectorPlate = new BlockVectorPlate(hyperSpeed);
        ModUtils.registerBlock(blockHyperVectorPlate, "trap_move_hyper");
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
            GameRegistry.addShapedRecipe(new ItemStack(blockFastVectorPlate, 8), "xxx", "xyx", "xxx", 'x', blockVectorPlate, 'y', ModUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 3)));
            GameRegistry.addShapedRecipe(new ItemStack(blockHyperVectorPlate, 8), "xxx", "xyx", "xxx", 'x', blockFastVectorPlate, 'y', ModUtils.validateCrafting(new ItemStack(FeatureMaterial.blockWitherDust, 1, OreDictionary.WILDCARD_VALUE)));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockVectorPlate);
        ModUtils.registerBlockInvModel(blockFastVectorPlate);
        ModUtils.registerBlockInvModel(blockHyperVectorPlate);
    }
}
