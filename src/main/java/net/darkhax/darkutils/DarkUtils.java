package net.darkhax.darkutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.bookshelf.item.ItemGroupBase;
import net.darkhax.bookshelf.network.NetworkHelper;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.registry.RegistryHelperClient;
import net.darkhax.darkutils.features.dust.DustHandler;
import net.darkhax.darkutils.features.slimecrucible.MessageSyncCrucibleType;
import net.darkhax.darkutils.network.NetworkHandlerClient;
import net.darkhax.darkutils.network.NetworkHandlerServer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DarkUtils.MOD_ID)
public class DarkUtils {
    
    public static final String MOD_ID = "darkutils";
    
    public static final String MOD_NAME = "Dark Utilities";
    
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);
    
    public static final ItemGroup ITEM_GROUP = new ItemGroupBase(MOD_ID, () -> new ItemStack(DarkUtils.content.vectorPlate));
    
    public static final NetworkHelper NETWORK = new NetworkHelper(new ResourceLocation(MOD_ID, "main"), "2.0.X");
    
    public static RegistryHelper registry;
    
    public static Content content;
    
    public DarkUtils() {
        
        registry = DistExecutor.runForDist( () -> () -> new RegistryHelperClient(MOD_ID, LOG, ITEM_GROUP), () -> () -> new RegistryHelper(MOD_ID, LOG, ITEM_GROUP));
        content = DistExecutor.runForDist( () -> () -> new ContentClient(registry), () -> () -> new Content(registry));
        NETWORK.registerEnqueuedMessage(MessageSyncCrucibleType.class, NetworkHandlerServer::encodeStageMessage, t -> NetworkHandlerClient.decodeStageMessage(t), (t, u) -> NetworkHandlerClient.processSyncStagesMessage(t, u));
        registry.initialize(FMLJavaModLoadingContext.get().getModEventBus());
        
        MinecraftForge.EVENT_BUS.addListener(DustHandler::onPlayerUseItem);
    }
}