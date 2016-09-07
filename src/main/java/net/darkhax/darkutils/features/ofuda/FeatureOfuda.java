package net.darkhax.darkutils.features.ofuda;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.ofuda.OfudaDataHandler.ICustomData;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureOfuda extends Feature {
    
    public static Item itemOfuda;
    
    @Override
    public void onPreInit () {
        
        itemOfuda = new ItemOfuda();
        ModUtils.registerItem(itemOfuda, "ofuda");
        OfudaDataHandler.init();
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
    
    }
    
    @Override
    public void setupRecipes () {
    
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemOfuda, "ofuda", ItemOfuda.varients);
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @SubscribeEvent
    public void canEntityDespawn (LivingSpawnEvent.AllowDespawn event) {
        
        final ICustomData data = event.getEntityLiving().getCapability(OfudaDataHandler.CUSTOM_DATA, EnumFacing.DOWN);
        
        if (data != null && data.isBound())
            event.setResult(Result.DENY);
    }
    
    public void onEntityKilled (LivingDeathEvent event) {
        
        final ICustomData data = event.getEntityLiving().getCapability(OfudaDataHandler.CUSTOM_DATA, EnumFacing.DOWN);
        
        if (data != null && data.isBound() && event.getSource() != null)
            event.getEntityLiving().getServer().getPlayerList().sendChatMsg(event.getSource().getDeathMessage(event.getEntityLiving()));
    }
}
