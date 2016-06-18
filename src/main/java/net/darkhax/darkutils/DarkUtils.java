package net.darkhax.darkutils;

import net.darkhax.darkutils.addons.AddonHandler;
import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.common.network.packet.PacketSyncTimer;
import net.darkhax.darkutils.creativetab.CreativeTabDarkUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;
import net.darkhax.darkutils.features.blocks.antislime.FeatureAntiSlime;
import net.darkhax.darkutils.features.blocks.endertether.FeatureEnderTether;
import net.darkhax.darkutils.features.blocks.faketnt.FeatureFakeTNT;
import net.darkhax.darkutils.features.blocks.feeder.FeatureFeeder;
import net.darkhax.darkutils.features.blocks.filter.FeatureFilter;
import net.darkhax.darkutils.features.blocks.grate.FeatureItemGrate;
import net.darkhax.darkutils.features.blocks.sneaky.FeatureSneaky;
import net.darkhax.darkutils.features.blocks.timer.FeatureTimer;
import net.darkhax.darkutils.features.blocks.trap.FeatureTrap;
import net.darkhax.darkutils.features.blocks.updatedetector.FeatureUpdateDetector;
import net.darkhax.darkutils.features.blocks.vector.FeatureVectorPlate;
import net.darkhax.darkutils.features.items.material.FeatureMaterial;
import net.darkhax.darkutils.features.items.potion.FeaturePotion;
import net.darkhax.darkutils.features.misc.FeatureOreDict;
import net.darkhax.darkutils.features.misc.FeatureSheepArmor;
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
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, dependencies = Constants.DEPENDENCIES)
public class DarkUtils {
    
    /**
     * A network wrapper for DarkUtils packets.
     */
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("DarkUtils");
    
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
        
        NETWORK.registerMessage(PacketSyncTimer.PacketHandler.class, PacketSyncTimer.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        
        ConfigurationHandler.initConfig(event.getSuggestedConfigurationFile());
        
        FeatureManager.registerFeature(new FeatureOreDict(), "Vanilla Ore Dictionary", "Adds several vanilla items and blocks to Forge's Ore Dictionary");
        FeatureManager.registerFeature(new FeatureSheepArmor(), "Sheep Armor", "Gives sheep armor when they have wool");
        
        FeatureManager.registerFeature(new FeatureVectorPlate(), "Vector Plate", "A block that pushes entities around");
        FeatureManager.registerFeature(new FeatureTrap(), "Trap Blocks", "Trap blocks that have certain effects when stepped on");
        FeatureManager.registerFeature(new FeatureEnderTether(), "Ender Tether", "A block to redirect ender teleportation");
        FeatureManager.registerFeature(new FeatureItemGrate(), "Item Grate", "A block that allows items through");
        FeatureManager.registerFeature(new FeatureFilter(), "Mob Filters", "Blocks for filtering mobs");
        FeatureManager.registerFeature(new FeatureTimer(), "Redstone Timer", "A block for timing redstone");
        FeatureManager.registerFeature(new FeatureAntiSlime(), "Anti Slime Block", "Undo slime chunks");
        FeatureManager.registerFeature(new FeatureUpdateDetector(), "Update Detector", "A block for detecting block updates");
        FeatureManager.registerFeature(new FeatureSneaky(), "Sneaky Blocks", "Blocks that can hide as other blocks");
        FeatureManager.registerFeature(new FeatureFeeder(), "Animal Feeder", "A block for auto breeding");
        FeatureManager.registerFeature(new FeatureFakeTNT(), "Fake TNT", "A safe TNT alternative");
        
        FeatureManager.registerFeature(new FeatureMaterial(), "Crafting Materials", "Material items used throughout DarkUtils");
        FeatureManager.registerFeature(new FeaturePotion(), "Mysterious Potion", "Strange potions with abnormal effects");
        
        ConfigurationHandler.syncConfigData();
        
        for (Feature feature : FeatureManager.FEATURES)
            feature.onPreInit();
            
        for (Feature feature : FeatureManager.FEATURES)
            feature.setupRecipes();
            
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
}