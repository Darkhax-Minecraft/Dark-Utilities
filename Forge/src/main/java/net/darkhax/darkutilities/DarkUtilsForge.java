package net.darkhax.darkutilities;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.darkutilities.addons.curios.AddonCurios;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class DarkUtilsForge {

    private final DarkUtilsCommon common = DarkUtilsCommon.getInstance();

    public DarkUtilsForge() {

        if (Services.PLATFORM.isPhysicalClient()) {

            new DarkUtilsForgeClient(this.common);
        }

        if (Services.PLATFORM.isModLoaded("curios")) {

            FMLJavaModLoadingContext.get().getModEventBus().register(AddonCurios.class);
        }
    }
}