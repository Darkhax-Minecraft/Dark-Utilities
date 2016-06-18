package net.darkhax.darkutils.features.blocks.vector;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.SLIMEBALL;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureVectorPlate extends Feature {
    
    public static Block blockVectorPlate;
    public static Block blockFastVectorPlate;
    public static Block blockHyperVectorPlate;
    
    private static boolean craftVectorPlate = true;
    private static boolean convertNormalFast = true;
    private static boolean convertFastHyper = true;
    private static boolean convertHyperNormal = true;
    protected static boolean preventItemDespawn = true;
    protected static boolean preventItemPickup = true;
    private static double normalSpeed = 0.06d;
    private static double fastSpeed = 0.3d;
    private static double hyperSpeed = 1.5d;
    private static int craftAmount = 8;
    
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
        convertNormalFast = config.getBoolean("Convert Normal to Fast", this.configName, true, "Should normal vector plates be convertable to fast vector plates?");
        convertFastHyper = config.getBoolean("Convert Fast to Hyper", this.configName, true, "Should fast vector plates be convertable to hyper vector plates?");
        convertHyperNormal = config.getBoolean("Convert Hyper to Fast", this.configName, true, "Should hyper vector plates be convertable to normal vector plates?");
        preventItemDespawn = config.getBoolean("Prevent Item Despawn", this.configName, true, "Should vector plates prevent item despawn?");
        preventItemPickup = config.getBoolean("Prevent Item Pickup", this.configName, true, "Should vector plates prevent items from being picked up, while they are being pushed?");
        normalSpeed = config.getFloat("Normal Speed", this.configName, 0.06f, 0f, 5f, "Speed modifier for the normal vector plate");
        fastSpeed = config.getFloat("Fast Speed", this.configName, 0.3f, 0f, 5f, "Speed modifier for the fast vector plate");
        hyperSpeed = config.getFloat("Hyper Speed", this.configName, 1.5f, 0f, 5f, "Speed modifier for the hyper vector plate");
        craftAmount = config.getInt("Crafting Amount", this.configName, 8, 1, 64, "The amount of vector plates to give per recipe set");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftVectorPlate)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockVectorPlate, craftAmount), new Object[] { "isi", "bfb", 's', SLIMEBALL, 'b', STONE, 'f', Items.SUGAR }));
            
        if (convertNormalFast)
            ModUtils.addConversionRecipes(new ItemStack(blockVectorPlate), new ItemStack(blockFastVectorPlate, 1, 0));
            
        if (convertFastHyper)
            ModUtils.addConversionRecipes(new ItemStack(blockFastVectorPlate), new ItemStack(blockHyperVectorPlate, 1, 0));
            
        if (convertHyperNormal)
            ModUtils.addConversionRecipes(new ItemStack(blockHyperVectorPlate), new ItemStack(blockVectorPlate, 1, 0));
    }
    
    @Override
    public void onClientPreInit () {
        
        ModUtils.registerBlockInvModel(blockVectorPlate);
        ModUtils.registerBlockInvModel(blockFastVectorPlate);
        ModUtils.registerBlockInvModel(blockHyperVectorPlate);
    }
}
