package net.darkhax.darkutils.features.ofuda;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureOfuda extends Feature {
    
    public static Item itemOfuda;
    
    @Override
    public void onPreInit () {
        
        itemOfuda = new ItemOfuda();
        ModUtils.registerItem(itemOfuda, "ofuda");
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
    
    @SubscribeEvent
    public void attatchCapabilities (AttachCapabilitiesEvent.Entity event) {
    
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
}
