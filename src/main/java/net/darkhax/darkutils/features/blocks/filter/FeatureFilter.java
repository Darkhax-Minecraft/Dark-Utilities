package net.darkhax.darkutils.features.blocks.filter;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.BLOCK_SLIME;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.BONE;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.CROP_WHEAT;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.EGG;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureFilter extends Feature {
    
    public static Block blockFilter;
    
    private static boolean craftPlayer = true;
    private static boolean craftUndead = true;
    private static boolean craftArthropod = true;
    private static boolean craftMonster = true;
    private static boolean craftAnimal = true;
    private static boolean craftWater = true;
    private static boolean craftBaby = true;
    private static boolean craftPet = true;
    private static boolean craftSlime = true;
    
    @Override
    public void onPreInit () {
        
        blockFilter = new BlockFilter();
        ModUtils.registerBlock(blockFilter, new ItemBlockFilter(blockFilter, FilterType.getTypes()), "filter");
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftPlayer = config.getBoolean("Craft Player Filter", this.configName, true, "Should the player filter be craftable?");
        craftUndead = config.getBoolean("Craft Undead Filter", this.configName, true, "Should the undead filter be craftable?");
        craftArthropod = config.getBoolean("Craft Arthropod Filter", this.configName, true, "Should the arthropod filter be craftable?");
        craftMonster = config.getBoolean("Craft Monster Filter", this.configName, true, "Should the monster filter be craftable?");
        craftAnimal = config.getBoolean("Craft Animal Filter", this.configName, true, "Should the animal filter be craftable?");
        craftWater = config.getBoolean("Craft Water Filter", this.configName, true, "Should the water filter be craftable?");
        craftBaby = config.getBoolean("Craft Baby Filter", this.configName, true, "Should the baby filter be craftable?");
        craftPet = config.getBoolean("Craft Pet Filter", this.configName, true, "Should the pet filter be craftable?");
        craftSlime = config.getBoolean("Craft Slime Filter", this.configName, true, "Should the slime filter be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftPlayer)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 0), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', Items.GOLDEN_PICKAXE }));
            
        if (craftUndead)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 1), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', BONE }));
            
        if (craftArthropod)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 2), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', Items.SPIDER_EYE }));
            
        if (craftMonster)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 3), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', Items.ROTTEN_FLESH }));
            
        if (craftAnimal)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 4), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', CROP_WHEAT }));
            
        if (craftWater)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 5), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', Items.WATER_BUCKET }));
            
        if (craftBaby)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 6), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', EGG }));
            
        if (craftPet)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 7), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', Items.MILK_BUCKET }));
            
        if (craftSlime)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, 8), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGateWood", 's', STONE, 'i', BLOCK_SLIME }));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerBlockInvModel(blockFilter, "filter", FilterType.getTypes());
    }
}
