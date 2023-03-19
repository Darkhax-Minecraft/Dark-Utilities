package net.darkhax.darkutilities;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.darkutilities.addons.curios.AddonCurios;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class DarkUtilsForge {

    public DarkUtilsForge() {

        DarkUtilsCommon.getInstance();

        if (Services.PLATFORM.isModLoaded("curios")) {

            FMLJavaModLoadingContext.get().getModEventBus().register(AddonCurios.class);
        }
    }
}