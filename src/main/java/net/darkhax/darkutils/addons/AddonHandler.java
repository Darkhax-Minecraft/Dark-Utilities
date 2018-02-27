package net.darkhax.darkutils.addons;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class AddonHandler {

    /**
     * A registry for holding all mod addons between DarkUtils and other mods.
     */
    public static final List<ModAddon> ADDONS = new ArrayList<>();

    /**
     * Registers the standard handlers with the registry.
     */
    public static void registerAddons () {

    }

    /**
     * Triggers the onPreInit method in all registered addons.
     */
    public static void onPreInit () {

        for (final ModAddon addon : ADDONS) {
            addon.onPreInit();
        }
    }

    /**
     * Triggers the onInit method in all registered addons.
     */
    public static void onInit () {

        if (Loader.isModLoaded("Waila")) {
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.darkutils.addons.waila.DarkUtilsTileProvider.registerAddon");
        }

        for (final ModAddon addon : ADDONS) {
            addon.onInit();
        }
    }

    /**
     * Triggers the onPostInit method in all registered addons.
     */
    public static void onPostInit () {

        for (final ModAddon addon : ADDONS) {
            addon.onPostInit();
        }
    }
}