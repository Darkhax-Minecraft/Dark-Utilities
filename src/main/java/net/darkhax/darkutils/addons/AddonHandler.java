package net.darkhax.darkutils.addons;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.darkutils.addons.thaumcraft.DarkUtilsThaumcraftAddon;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class AddonHandler {
    
    public static void preInit () {
        
        if (Loader.isModLoaded("Waila"))
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.darkutils.addons.waila.DarkUtilsTileProvider.registerAddon");
            
        if (Loader.isModLoaded("Thaumcraft"))
            DarkUtilsThaumcraftAddon.preInit();
    }
    
    public static void init () {
    
    }
    
    public static void postInit () {
        
        if (Loader.isModLoaded("Thaumcraft"))
            DarkUtilsThaumcraftAddon.postInit();
    }
}
