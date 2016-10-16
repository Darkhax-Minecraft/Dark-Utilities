package net.darkhax.darkutils.features.enchrings;

import net.darkhax.bookshelf.events.EnchantmentModifierEvent;
import net.darkhax.bookshelf.lib.modutils.baubles.BaublesUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureEnchantedRing extends Feature {
    
    public static Item itemRing;
    private static boolean dungeonLoot;
    private static int dungeonWeight;
    
    @Override
    public void onPreInit () {
        
        itemRing = ModUtils.registerItem(new ItemRing(), "ring");
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemRing, "ring", ItemRing.varients);
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @SubscribeEvent
    public void getEnchantmentLevel (EnchantmentModifierEvent event) {
        
        this.handleRing(event.getEntity().getHeldItemOffhand(), event);
        
        if (Loader.isModLoaded("Baubles") && event.getEntity() instanceof EntityPlayer) {
            
            this.handleRing(BaublesUtils.getFirstRing((EntityPlayer) event.getEntity()), event);
            this.handleRing(BaublesUtils.getSecondRing((EntityPlayer) event.getEntity()), event);
        }
    }
    
    private void handleRing (ItemStack stack, EnchantmentModifierEvent event) {
        
        if (stack != null && stack.getItem() instanceof ItemRing && event.getEnchantment() == ItemRing.getEnchantmentFromMeta(stack.getMetadata())) {
            
            event.levels++;
            event.setCanceled(true);
        }
    }
}
