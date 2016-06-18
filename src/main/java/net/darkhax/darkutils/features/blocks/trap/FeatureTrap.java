package net.darkhax.darkutils.features.blocks.trap;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.items.material.FeatureMaterial;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureTrap extends Feature {
    
    public static Block blockTrap;
    
    public boolean craftPoison = true;
    public boolean craftWeakness = true;
    public boolean craftHarming = true;
    public boolean craftSlowness = true;
    public boolean craftFire = true;
    public boolean craftWither = true;
    
    @Override
    public void onPreInit () {
        
        blockTrap = new BlockTrap();
        ModUtils.registerBlock(blockTrap, new ItemBlockBasic(blockTrap, TrapType.getTypes()), "trap_tile");
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftPoison = config.getBoolean("craftPoison", this.configName, true, "Should the poison trap be craftable?");
        craftWeakness = config.getBoolean("craftWeakness", this.configName, true, "Should the weakness trap be craftable?");
        craftHarming = config.getBoolean("craftHarming", this.configName, true, "Should the harming trap be craftable?");
        craftSlowness = config.getBoolean("craftSlowness", this.configName, true, "Should the slowness trap be craftable?");
        craftFire = config.getBoolean("craftFire", this.configName, true, "Should the fire trap be craftable?");
        craftWither = config.getBoolean("craftWither", this.configName, true, "Should the wither trap be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftPoison)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 0), new Object[] { "sis", 's', STONE, 'i', Items.SPIDER_EYE }));
            
        if (craftWeakness)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 1), new Object[] { "sis", 's', STONE, 'i', Items.FERMENTED_SPIDER_EYE }));
            
        if (craftHarming)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 2), new Object[] { "sis", 's', STONE, 'i', Items.IRON_SWORD }));
            
        if (craftSlowness)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 3), new Object[] { "sis", 's', STONE, 'i', Blocks.SOUL_SAND }));
            
        if (craftFire)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 4), new Object[] { "sis", 's', STONE, 'i', Items.FLINT_AND_STEEL }));
            
        if (craftWither)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 5), new Object[] { "sis", 's', STONE, 'i', ModUtils.validateCrafting(FeatureMaterial.itemMaterial) }));
    }
    
    @Override
    public void onClientPreInit () {
        
        ModUtils.registerBlockInvModel(blockTrap, "trap", TrapType.getTypes());
    }
}
