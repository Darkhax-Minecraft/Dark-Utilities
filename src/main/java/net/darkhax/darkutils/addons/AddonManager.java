package net.darkhax.darkutils.addons;

import net.darkhax.bookshelf.util.ModUtils;
import net.darkhax.darkutils.addons.curio.CurioAddon;
import net.darkhax.darkutils.addons.curio.ICurioAddon;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonManager {
    
    private ICurioAddon curio;
    
    public AddonManager() {
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueIMC);
    }
    
    private void onCommonSetup (FMLCommonSetupEvent event) {
        
        this.curio = ModUtils.callIfPresent("curios", () -> () -> new CurioAddon(), () -> () -> ICurioAddon.DEFAULT);
    }
    
    private void enqueIMC (InterModEnqueueEvent event) {
        
        this.curio.enqueCommunication();
    }
    
    public ICurioAddon curios () {
        
        return this.curio;
    }
}