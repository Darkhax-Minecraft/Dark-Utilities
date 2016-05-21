package net.darkhax.darkutils;

import net.darkhax.darkutils.addons.AddonHandler;
import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.common.network.packet.PacketSyncTimer;
import net.darkhax.darkutils.creativetab.CreativeTabDarkUtils;
import net.darkhax.darkutils.handler.ContentHandler;
import net.darkhax.darkutils.handler.ForgeEventHandler;
import net.darkhax.darkutils.handler.GuiHandler;
import net.darkhax.darkutils.libs.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, dependencies = Constants.DEPENDENCIES, acceptedMinecraftVersions = Constants.MCVERSION)
public class DarkUtils {
    
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Constants.MOD_ID)
    public static DarkUtils instance;
    
    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("DarkUtils");
    public static final CreativeTabs TAB = new CreativeTabDarkUtils();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        network.registerMessage(PacketSyncTimer.PacketHandler.class, PacketSyncTimer.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        
        ContentHandler.initBlocks();
        ContentHandler.initItems();
        ContentHandler.initMisc();
        ContentHandler.initRecipes();
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        proxy.onPreInit();
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
        
        proxy.onInit();
    }
    
    @EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        
        AddonHandler.onPostInit();
    }
    
    public static void printDebugMessage (String message) {
        
        // TODO config to turn off.
        Constants.LOGGER.info(message);
    }
}