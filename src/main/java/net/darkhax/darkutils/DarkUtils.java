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
    
    public static SimpleNetworkWrapper network;
    public static CreativeTabs tab = new CreativeTabDarkUtils();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        network = NetworkRegistry.INSTANCE.newSimpleChannel("DarkUtils");
        network.registerMessage(PacketSyncTimer.PacketHandler.class, PacketSyncTimer.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        
        ContentHandler.initBlocks();
        ContentHandler.initItems();
        ContentHandler.initMisc();
        ContentHandler.initRecipes();
        proxy.onPreInit();
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        AddonHandler.preInit();
    }
    
    @EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        
        AddonHandler.postInit();
    }
}