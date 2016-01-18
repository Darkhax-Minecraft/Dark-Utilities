package net.darkhax.darkutils.addons.jei;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.darkutils.blocks.BlockTrapTile;
import net.darkhax.darkutils.handler.ContentHandler;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class DarkUtilsJEIPlugin implements IModPlugin {
    
    public static IJeiHelpers jeiHelper;
    
    @Override
    public void register (IModRegistry registry) {
        
        registry.addDescription(new ItemStack(ContentHandler.blockEnderTether), "jei.darkutils.endertether.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockTrapMovement), "jei.darkutils.trap.vector.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockGrate), "jei.darkutils.grate.desc");
        
        for (BlockTrapTile.EnumType type : BlockTrapTile.EnumType.values())
            registry.addDescription(new ItemStack(ContentHandler.blockTrap, 1, type.meta), "jei.darkutils.trap." + type.type + ".desc");
    }
    
    @Override
    public void onJeiHelpersAvailable (IJeiHelpers jeiHelpers) {
        
        jeiHelper = jeiHelpers;
    }
    
    @Override
    public void onItemRegistryAvailable (IItemRegistry itemRegistry) {
    
    }
    
    @Override
    public void onRecipeRegistryAvailable (IRecipeRegistry recipeRegistry) {
    
    }
    
    @Override
    public boolean isModLoaded () {
        
        return true;
    }
}