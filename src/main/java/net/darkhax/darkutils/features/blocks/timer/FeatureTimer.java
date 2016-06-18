package net.darkhax.darkutils.features.blocks.timer;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureTimer extends Feature {
    
    public static Block blockTimer;
    private static boolean craftable = true;
    
    @Override
    public void onPreInit () {
        
        blockTimer = new BlockTimer();
        ModUtils.registerBlock(blockTimer, "timer");
        GameRegistry.registerTileEntity(TileEntityTimer.class, "timer");
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftable = config.getBoolean("Craftable", this.configName, true, "Should the timer be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftable)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockTimer), new Object[] { "sts", "tct", "sts", 's', STONE, 't', Blocks.REDSTONE_TORCH, 'c', Items.CLOCK }));
    }
    
    @Override
    public void onClientPreInit () {
        
        ModUtils.registerBlockInvModel(blockTimer);
    }
}
