package net.darkhax.darkutils;

import java.io.File;

import net.darkhax.bookshelf.network.NetworkHandler;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.util.GameUtils;
import net.darkhax.darkutils.addons.AddonHandler;
import net.darkhax.darkutils.creativetab.CreativeTabDarkUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;
import net.darkhax.darkutils.features.timer.PacketSyncTimer;
import net.darkhax.darkutils.handler.ConfigurationHandler;
import net.darkhax.darkutils.handler.GuiHandler;
import net.darkhax.darkutils.libs.Constants;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, dependencies = Constants.DEPENDENCIES)
public class DarkUtils {

    /**
     * The creative tab used for all content added by this mod.
     */
    public static final CreativeTabs TAB = new CreativeTabDarkUtils();

    /**
     * A network wrapper for DarkUtils packets.
     */
    public static final NetworkHandler NETWORK = new NetworkHandler(Constants.MOD_ID);

    /**
     * A handler for registering content.
     */
    public static final RegistryHelper REGISTRY = new RegistryHelper(Constants.MOD_ID).setTab(TAB);

    /**
     * Reference to the mod instance. Useful for mod specific things, such as entities.
     */
    @Mod.Instance(Constants.MOD_ID)
    public static DarkUtils instance;

    @EventHandler
    public void onConstruction (FMLConstructionEvent event) {

        ConfigurationHandler.initConfig(new File("/config/darkutils.cfg"));
        FeatureManager.init(event.getASMHarvestedData());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

        NETWORK.register(PacketSyncTimer.class, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        ConfigurationHandler.syncConfigData();

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.onPreInit();
        }

        AddonHandler.registerAddons();
        AddonHandler.onPreInit();
    }

    @EventHandler
    public void init (FMLInitializationEvent event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.onInit();
        }
        
        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.setupRecipes();
        }
        
        AddonHandler.onInit();
    }

    @EventHandler
    public void postInit (FMLPostInitializationEvent event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.onPostInit();
        }

        AddonHandler.onPostInit();
    }
    
    @EventHandler
    @SideOnly(Side.CLIENT)
    public void clientPreInit (FMLPreInitializationEvent event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
        	feature.onClientPreInit();
        }
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void clientInit (FMLInitializationEvent event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
        	feature.onClientInit();
        }
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void clientPostInit (FMLPostInitializationEvent event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
        	feature.onClientPostInit();
        }
    }

    @SubscribeEvent
    public void registerBlocks (RegistryEvent.Register<Block> event) {

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.onRegistry();
        }

        if (GameUtils.isClient()) {

            for (final Feature feature : FeatureManager.getFeatures()) {

                feature.onClientRegistry();
            }
        }

        for (final Block block : REGISTRY.getBlocks()) {

            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public void registerItems (RegistryEvent.Register<Item> event) {

        for (final Item item : REGISTRY.getItems()) {

            event.getRegistry().register(item);
        }
    }
}