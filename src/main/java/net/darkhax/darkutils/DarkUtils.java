package net.darkhax.darkutils;

import net.darkhax.bookshelf.network.NetworkHandler;
import net.darkhax.darkutils.addons.AddonHandler;
import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.creativetab.CreativeTabDarkUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;
import net.darkhax.darkutils.features.timer.PacketSyncTimer;
import net.darkhax.darkutils.handler.ConfigurationHandler;
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
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, dependencies = Constants.DEPENDENCIES)
public class DarkUtils {

    /**
     * A network wrapper for DarkUtils packets.
     */
    public static final NetworkHandler NETWORK = new NetworkHandler(Constants.MOD_ID);

    /**
     * The creative tab used for all content added by this mod.
     */
    public static final CreativeTabs TAB = new CreativeTabDarkUtils();

    /**
     * Reference to the proxy system. This will be the client proxy on the client side, and
     * common proxy on the server side.
     */
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static ProxyCommon proxy;

    /**
     * Reference to the mod instance. Useful for mod specific things, such as entities.
     */
    @Mod.Instance(Constants.MOD_ID)
    public static DarkUtils instance;

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

        NETWORK.register(PacketSyncTimer.class, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        ConfigurationHandler.initConfig(event.getSuggestedConfigurationFile());

        FeatureManager.init(event.getAsmData());

        ConfigurationHandler.syncConfigData();

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.onPreInit();
        }

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.setupRecipes();
        }

        proxy.onPreInit();

        AddonHandler.registerAddons();
        AddonHandler.onPreInit();
    }

    @EventHandler
    public void init (FMLInitializationEvent event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.onInit();
        }

        proxy.onInit();
        AddonHandler.onInit();
    }

    @EventHandler
    public void postInit (FMLPostInitializationEvent event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.onPostInit();
        }

        proxy.onPostInit();
        AddonHandler.onPostInit();
    }
}