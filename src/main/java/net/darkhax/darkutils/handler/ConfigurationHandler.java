package net.darkhax.darkutils.handler;

import java.io.File;

import net.darkhax.bookshelf.lib.Constants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    private static final String CATEGORY_MISC = "miscellaneous";
    
    private static Configuration config;
    
    public boolean oredictVanillaFenceGate;
    public boolean oredictVanillaStone;
    
    public ConfigurationHandler(File file) {
        
        config = new Configuration(file);
        MinecraftForge.EVENT_BUS.register(this);
        this.syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (OnConfigChangedEvent event) {
        
        if (event.getModID().equals(Constants.MOD_ID))
            this.syncConfigData();
    }
    
    private void syncConfigData () {
        
        // Misc
        this.oredictVanillaFenceGate = config.getBoolean("oredictVanillaFenceGate", CATEGORY_MISC, true, "Should DarkUtils register the vanilla fence gates with the ore dictionar? Disabling this may break some recipes in DarkUtils.");
        this.oredictVanillaStone = config.getBoolean("oredictVanillaStone", CATEGORY_MISC, true, "Should DarkUtils register the vanilla stone types with the ore dictionary? Disabling this may break some recipes in DarkUtils.");
        
        if (config.hasChanged())
            config.save();
    }
}