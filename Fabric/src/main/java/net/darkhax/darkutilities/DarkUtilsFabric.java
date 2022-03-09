package net.darkhax.darkutilities;

import net.darkhax.bookshelf.api.Services;
import net.fabricmc.api.ModInitializer;

public class DarkUtilsFabric implements ModInitializer {

    private final DarkUtilsCommon common = DarkUtilsCommon.getInstance();

    @Override
    public void onInitialize() {

        if (Services.PLATFORM.isPhysicalClient()) {

            new DarkUtilsFabricClient(common);
        }
    }
}