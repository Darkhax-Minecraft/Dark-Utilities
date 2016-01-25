package net.darkhax.darkutils.addons.jei;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.darkutils.addons.thaumcraft.DarkUtilsThaumcraftAddon;
import net.darkhax.darkutils.blocks.BlockFilter;
import net.darkhax.darkutils.blocks.BlockTrapEffect;
import net.darkhax.darkutils.handler.ContentHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

@JEIPlugin
public class DarkUtilsJEIPlugin implements IModPlugin {
    
    public static IJeiHelpers jeiHelper;
    
    @Override
    public void register (IModRegistry registry) {
        
        for (BlockTrapEffect.EnumType type : BlockTrapEffect.EnumType.values())
            registry.addDescription(new ItemStack(ContentHandler.blockTrap, 1, type.meta), "jei.darkutils.trap." + type.type + ".desc");
            
        registry.addDescription(new ItemStack(ContentHandler.blockEnderTether), "jei.darkutils.endertether.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockTrapMovement), "jei.darkutils.trap.vector.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockGrate), "jei.darkutils.grate.desc");
        
        for (BlockFilter.EnumType type : BlockFilter.EnumType.values())
            registry.addDescription(new ItemStack(ContentHandler.blockFilter, 1, type.meta), "jei.darkutils.filter." + type.type + ".desc");
            
        if (Loader.isModLoaded("Thaumcraft"))
            DarkUtilsThaumcraftAddon.jeiRegisterHook(registry);
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
}