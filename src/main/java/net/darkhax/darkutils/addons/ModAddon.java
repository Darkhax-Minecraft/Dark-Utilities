package net.darkhax.darkutils.addons;

import mezz.jei.api.IModRegistry;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ModAddon {
    
    /**
     * Called during the preInit stage of loading. This is done after packets, blocks, items,
     * recipes, events and misc content from the main mod has been loaded, but before the
     * models or client code has been initialized.
     */
    public void onPreInit ();
    
    /**
     * Called during the init stage of loading. This is done after everything else from the
     * main mod has loaded. Currently add-ons are the only thing to make use of this stage.
     */
    public void onInit ();
    
    /**
     * Called during the postInit stage of loading. This is done after everything else from the
     * main mod has loaded. Currently add-ons are the only thing to make use of this stage.
     */
    public void onPostInit ();
    
    /**
     * Called during the preInit stage of loading. This version of the preInit method is only
     * handled on the client side. This method will be completely deleted from the server side
     * version of the code. This is the last thing to be called during the client preInit
     * stage.
     */
    @SideOnly(Side.CLIENT)
    public void onClientPreInit ();
    
    /**
     * Called when the JEI mod is ready to accept input from other mods. This method will be
     * completely deleted if the game starts without JEI installed. The primary use of this
     * method is to add descriptions for items in JEI.
     * 
     * @param registry Access to the JEI registry.
     */
    @Optional.Method(modid = "JEI")
    public void onJEIReady (IModRegistry registry);
}