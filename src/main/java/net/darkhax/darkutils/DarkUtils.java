package net.darkhax.darkutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.bookshelf.item.ItemGroupBase;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.darkutils.features.slimecrucible.ContainerSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.RenderSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.ScreenSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DarkUtils.MOD_ID)
public class DarkUtils {
    
    public static final String MOD_ID = "darkutils";
    
    public static final String MOD_NAME = "Dark Utilities";
    
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);
    
    public static final ItemGroup ITEM_GROUP = new ItemGroupBase(MOD_ID, () -> new ItemStack(Items.APPLE));
    
    public static final RegistryHelper REGISTRY = new RegistryHelper(MOD_ID, LOG, ITEM_GROUP);
    
    public static Content content;
    
    public static ContainerType<ContainerSlimeCrucible> containerType = new ContainerType<>(ContainerSlimeCrucible::new);
    
    public DarkUtils() {
        
        REGISTRY.initialize(FMLJavaModLoadingContext.get().getModEventBus());
        content = new Content(REGISTRY);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);
    }
    
    private void onClientSetup (FMLClientSetupEvent event) {
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySlimeCrucible.class, new RenderSlimeCrucible());
    }
    
    private void registerContainers (Register<ContainerType<?>> event) {
        
        containerType.setRegistryName("darkutils:slime_crucible");
        ScreenManager.registerFactory(containerType, ScreenSlimeCrucible::new);
        event.getRegistry().register(containerType);
        System.out.println("Registered");
    }
}