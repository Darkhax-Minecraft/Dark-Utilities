package net.darkhax.darkutils.features.trap;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
        
        this.craftPoison = config.getBoolean("craftPoison", this.configName, true, "Should the poison trap be craftable?");
        this.craftWeakness = config.getBoolean("craftWeakness", this.configName, true, "Should the weakness trap be craftable?");
        this.craftHarming = config.getBoolean("craftHarming", this.configName, true, "Should the harming trap be craftable?");
        this.craftSlowness = config.getBoolean("craftSlowness", this.configName, true, "Should the slowness trap be craftable?");
        this.craftFire = config.getBoolean("craftFire", this.configName, true, "Should the fire trap be craftable?");
        this.craftWither = config.getBoolean("craftWither", this.configName, true, "Should the wither trap be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (this.craftPoison)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 0), new Object[] { "sis", 's', STONE, 'i', Items.SPIDER_EYE }));
            
        if (this.craftWeakness)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 1), new Object[] { "sis", 's', STONE, 'i', Items.FERMENTED_SPIDER_EYE }));
            
        if (this.craftHarming)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 2), new Object[] { "sis", 's', STONE, 'i', Items.IRON_SWORD }));
            
        if (this.craftSlowness)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 3), new Object[] { "sis", 's', STONE, 'i', Blocks.SOUL_SAND }));
            
        if (this.craftFire)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 4), new Object[] { "sis", 's', STONE, 'i', Items.FLINT_AND_STEEL }));
            
        if (this.craftWither)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTrap, 1, 5), new Object[] { "sis", 's', STONE, 'i', ModUtils.validateCrafting(FeatureMaterial.itemMaterial) }));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerBlockInvModel(blockTrap, "trap", TrapType.getTypes());
    }
}
