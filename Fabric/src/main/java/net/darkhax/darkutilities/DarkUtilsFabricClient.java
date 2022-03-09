package net.darkhax.darkutilities;

public class DarkUtilsFabricClient {

    public final DarkUtilsCommon common;
    public final ContentClient client;

    public DarkUtilsFabricClient(DarkUtilsCommon common) {

        this.common = common;
        this.client = new ContentClient(common.content);

        this.client.registerBlockLayers();
    }
}