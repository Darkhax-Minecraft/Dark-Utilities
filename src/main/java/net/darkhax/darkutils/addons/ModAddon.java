package net.darkhax.darkutils.addons;

public interface ModAddon {

    /**
     * Called during the preInit stage of loading. This is done after packets, blocks, items,
     * recipes, events and misc content from the main mod has been loaded, but before the
     * models or client code has been initialized.
     */
    void onPreInit ();

    /**
     * Called during the init stage of loading. This is done after everything else from the
     * main mod has loaded. Currently add-ons are the only thing to make use of this stage.
     */
    void onInit ();

    /**
     * Called during the postInit stage of loading. This is done after everything else from the
     * main mod has loaded. Currently add-ons are the only thing to make use of this stage.
     */
    void onPostInit ();
}