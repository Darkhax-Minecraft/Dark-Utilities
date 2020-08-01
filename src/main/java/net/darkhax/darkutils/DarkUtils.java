package net.darkhax.darkutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.bookshelf.item.ItemGroupBase;
import net.darkhax.bookshelf.network.NetworkHelper;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DarkUtils.MOD_ID)
public class DarkUtils {
    
    public static final String MOD_ID = "darkutils";
    
    public static final String MOD_NAME = "Dark Utilities";
    
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);
    
    public static final ItemGroup ITEM_GROUP = new ItemGroupBase(MOD_ID, () -> new ItemStack(DarkUtils.content.vectorPlate));
    
    public static final NetworkHelper NETWORK = new NetworkHelper(new ResourceLocation(MOD_ID, "main"), "4.0.X");
    
    public static RegistryHelper registry;
    
    public static Content content;
    
    public DarkUtils() {
       
        registry = new RegistryHelper(MOD_ID, LOG).withItemGroup(ITEM_GROUP);
        content = DistExecutor.unsafeRunForDist( () -> () -> new ContentClient(registry), () -> () -> new Content(registry));
        registry.initialize(FMLJavaModLoadingContext.get().getModEventBus());
    }
}