package net.darkhax.darkutils.features.sleepcharm;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.LEATHER;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.STICK_WOOD;

import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureSleepCharm extends Feature {
    
    public static Item itemSleepCharm;
    private static boolean craftable;
    
    @Override
    public void onPreInit () {
        
        itemSleepCharm = new ItemSleepCharm();
        ModUtils.registerItem(itemSleepCharm, "charm_sleep");
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftable = config.getBoolean("Craftable", this.configName, true, "Should the sleep charm be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftable)
            GameRegistry.addRecipe(new ShapedOreRecipe(itemSleepCharm, new Object[] { "lsl", "sbs", "lsl", 's', STICK_WOOD, 'l', LEATHER, 'b', Items.BED }));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemSleepCharm);
    }
    
    @SubscribeEvent
    public void onPlayerUpdate (LivingUpdateEvent event) {
        
        final Entity entity = event.getEntity();
        
        if (entity instanceof EntityPlayer) {
            
            final EntityPlayer player = (EntityPlayer) entity;
            
            if (player.isPlayerSleeping() && PlayerUtils.playerHasItem((EntityPlayer) entity, itemSleepCharm, -1))
                player.sleepTimer = 100;
        }
    }
}