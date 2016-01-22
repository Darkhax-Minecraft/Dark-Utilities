package net.darkhax.darkutils.addons;

import net.darkhax.darkutils.addons.thaumcraft.DarkUtilsThaumcraftAddon;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class AddonHandler {
    
    /**
     * A special pre-init method used to handle all addon specific things.
     */
    public static void preInit () {
        
        if (Loader.isModLoaded("Waila"))
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.darkutils.addons.waila.DarkUtilsTileProvider.registerAddon");
            
        if (Loader.isModLoaded("Thaumcraft"))
            DarkUtilsThaumcraftAddon.preInit();
    }
}
