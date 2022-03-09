package net.darkhax.darkutilities;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class DarkUtilsForgeClient {

    public final DarkUtilsCommon common;
    public final ContentClient client;

    public DarkUtilsForgeClient(DarkUtilsCommon common) {

        this.common = common;
        this.client = new ContentClient(common.content);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupClient(FMLClientSetupEvent event) {

        this.client.registerBlockLayers();
    }
}