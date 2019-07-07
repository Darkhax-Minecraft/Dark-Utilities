package net.darkhax.darkutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.bookshelf.item.ItemGroupBase;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.registry.RegistryHelperClient;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DarkUtils.MOD_ID)
public class DarkUtils {
    
    public static final String MOD_ID = "darkutils";
    
    public static final String MOD_NAME = "Dark Utilities";
    
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);
    
    public static final ItemGroup ITEM_GROUP = new ItemGroupBase(MOD_ID, () -> new ItemStack(Items.APPLE));
    
    public static RegistryHelper registry;
    
    public static Content content;
    
    public DarkUtils() {
        
        registry = DistExecutor.runForDist( () -> () -> new RegistryHelperClient(MOD_ID, LOG, ITEM_GROUP), () -> () -> new RegistryHelper(MOD_ID, LOG, ITEM_GROUP));
        registry.initialize(FMLJavaModLoadingContext.get().getModEventBus());
        content = DistExecutor.runForDist( () -> () -> new ContentClient(registry), () -> () -> new Content(registry));
    }
}