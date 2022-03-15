package net.darkhax.darkutilities;

import net.fabricmc.api.ModInitializer;

public class DarkUtilsFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        DarkUtilsCommon.getInstance();
    }
}