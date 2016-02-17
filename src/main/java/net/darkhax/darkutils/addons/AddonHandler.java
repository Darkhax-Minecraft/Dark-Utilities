package net.darkhax.darkutils.addons;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IModRegistry;
import net.darkhax.darkutils.addons.baubles.BaublesAddon;
import net.darkhax.darkutils.addons.thaumcraft.ThaumcraftAddon;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AddonHandler {
    
    public static List<ModAddon> addonRegistry = new ArrayList<ModAddon>();
    
    public static void registerAddons () {
        
        if (Loader.isModLoaded("Waila"))
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.darkutils.addons.waila.DarkUtilsTileProvider.registerAddon");
            
        if (Loader.isModLoaded("Thaumcraft"))
            addonRegistry.add(new ThaumcraftAddon());
            
        if (Loader.isModLoaded("Baubles"))
            addonRegistry.add(new BaublesAddon());
    }
    
    public static void onPreInit () {
        
        for (ModAddon addon : addonRegistry)
            addon.onPreInit();
    }
    
    public static void onInit () {
        
        for (ModAddon addon : addonRegistry)
            addon.onInit();
    }
    
    public static void onPostInit () {
        
        for (ModAddon addon : addonRegistry)
            addon.onPostInit();
    }
    
    @SideOnly(Side.CLIENT)
    public static void onClientPreInit () {
        
        for (ModAddon addon : addonRegistry)
            addon.onClientPreInit();
    }
    
    @Optional.Method(modid = "JEI")
    public static void onJEIReady (IModRegistry registry) {
        
        for (ModAddon addon : addonRegistry)
            addon.onJEIReady(registry);
    }
}
