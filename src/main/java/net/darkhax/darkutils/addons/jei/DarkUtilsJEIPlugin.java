package net.darkhax.darkutils.addons.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.darkutils.handler.ContentHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class DarkUtilsJEIPlugin extends BlankModPlugin {
    
    @Override
    public void register (IModRegistry registry) {
        
        for (final Block block : ContentHandler.BLOCKS)
            registry.addDescription(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE), "jei." + block.getUnlocalizedName());
            
        for (final Item item : ContentHandler.ITEMS)
            registry.addDescription(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), "jei." + item.getUnlocalizedName());
    }
}