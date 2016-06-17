package net.darkhax.darkutils;

import net.darkhax.darkutils.addons.AddonHandler;
import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.common.network.packet.PacketSyncTimer;
import net.darkhax.darkutils.creativetab.CreativeTabDarkUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;
import net.darkhax.darkutils.features.misc.FeatureOreDict;
import net.darkhax.darkutils.handler.ConfigurationHandler;
import net.darkhax.darkutils.handler.ContentHandler;
import net.darkhax.darkutils.handler.GuiHandler;
import net.darkhax.darkutils.libs.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, dependencies = Constants.DEPENDENCIES)
public class DarkUtils {
    
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("DarkUtils");
    public static final CreativeTabs TAB = new CreativeTabDarkUtils();
    
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Constants.MOD_ID)
    public static DarkUtils instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        NETWORK.registerMessage(PacketSyncTimer.PacketHandler.class, PacketSyncTimer.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        
        ConfigurationHandler.initConfig(event.getSuggestedConfigurationFile());
        
        FeatureManager.registerFeature(new FeatureOreDict(), "Vanilla Ore Dictionary", "Adds several vanilla items and blocks to Forge's Ore Dictionary");
        
        ConfigurationHandler.syncConfigData();
        
        for (Feature feature : FeatureManager.FEATURES)
            feature.onPreInit();
            
        for (Feature feature : FeatureManager.FEATURES)
            feature.setupRecipes();
            
        ContentHandler.initBlocks();
        ContentHandler.initItems();
        ContentHandler.initEntities();
        ContentHandler.initRecipes();
        
        proxy.onPreInit();
        
        AddonHandler.registerAddons();
        AddonHandler.onPreInit();
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
        
        for (Feature feature : FeatureManager.FEATURES)
            feature.onInit();
            
        proxy.onInit();
        AddonHandler.onInit();
    }
    
    @EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        
        for (Feature feature : FeatureManager.FEATURES)
            feature.onPostInit();
            
        proxy.onPostInit();
        AddonHandler.onPostInit();
    }
    
    public static void printDebugMessage (String message) {
        
        // TODO config to turn off.
        Constants.LOGGER.info(message);
    }
}