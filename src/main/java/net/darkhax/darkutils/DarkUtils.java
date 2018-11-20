package net.darkhax.darkutils;

import java.io.File;
import java.util.Random;

import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.network.NetworkHandler;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.darkutils.creativetab.CreativeTabDarkUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;
import net.darkhax.darkutils.handler.ConfigurationHandler;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = DarkUtils.MOD_ID, name = DarkUtils.MOD_NAME, version = DarkUtils.VERSION_NUMBER, dependencies = DarkUtils.DEPENDENCIES, certificateFingerprint = "@FINGERPRINT@")
public class DarkUtils {

    public static final String MOD_ID = "darkutils";

    public static final String MOD_NAME = "Dark Utilities";

    public static final String VERSION_NUMBER = "@VERSION@";

    public static final String CLIENT_PROXY_CLASS = "net.darkhax.darkutils.DarkUtilsClient";

    public static final String SERVER_PROXY_CLASS = "net.darkhax.darkutils.DarkUtilsServer";

    public static final String DEPENDENCIES = "required-after:bookshelf@[2.3.537,);after:waila;after:jei;after:baubles;";

    public static final Random RANDOM = new Random();

    public static final LoggingHelper LOGGER = new LoggingHelper(MOD_NAME);

    public static final CreativeTabs TAB = new CreativeTabDarkUtils();

    public static final NetworkHandler NETWORK = new NetworkHandler(DarkUtils.MOD_ID);

    public static final RegistryHelper REGISTRY = new RegistryHelper(DarkUtils.MOD_ID).setTab(TAB).enableAutoRegistration();

    @SidedProxy(clientSide = DarkUtils.CLIENT_PROXY_CLASS, serverSide = DarkUtils.SERVER_PROXY_CLASS)
    public static DarkUtilsServer proxy;

    @Mod.Instance(DarkUtils.MOD_ID)
    public static DarkUtils instance;

    @EventHandler
    public void onConstruction (FMLConstructionEvent event) {

        ConfigurationHandler.initConfig(new File("config/darkutils.cfg"));
        FeatureManager.init(event.getASMHarvestedData());
    }

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        ConfigurationHandler.syncConfigData();

        proxy.preInit();
    }

    @EventHandler
    public void init (FMLInitializationEvent event) {

        proxy.init();
    }

    @EventHandler
    public void postInit (FMLPostInitializationEvent event) {

        proxy.postInit();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void registerRecipes (RegistryEvent.Register<IRecipe> event) {

        for (final Feature feature : FeatureManager.getFeatures()) {

            feature.onPreRecipe();
        }
    }

    @EventHandler
    public void onFingerprintViolation (FMLFingerprintViolationEvent event) {

        LOGGER.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!");
    }
}