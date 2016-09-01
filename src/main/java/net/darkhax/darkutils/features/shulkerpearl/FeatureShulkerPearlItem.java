package net.darkhax.darkutils.features.shulkerpearl;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.shulkerpearl.ShulkerDataHandler.ICustomData;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureShulkerPearlItem extends Feature {
    
    public static Item itemShulkerPearl;
    public static Block blockShulkerPearl;
    
    private boolean harvestablePearls = true;
    private boolean craftEndRods = true;
    private int maxCooldown = 6000;
    
    @Override
    public void onPreInit () {
        
        itemShulkerPearl = ModUtils.registerItem(new ItemShulkerPearl(), "shulker_pearl");
        
        if (harvestablePearls)
            ShulkerDataHandler.init();
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        harvestablePearls = config.getBoolean("Harvest Pearls", this.configName, true, "Should pearls be harvestable from shulkers?");
        craftEndRods = config.getBoolean("Craft End Rods", this.configName, true, "Can end rods be crafted?");
        maxCooldown = config.getInt("Shulker Cooldown", this.configName, 6000, 0, Integer.MAX_VALUE, "The pearl harvest cooldown tile, in ticks");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftEndRods)
            GameRegistry.addShapelessRecipe(new ItemStack(Blocks.END_ROD), Items.CHORUS_FRUIT, itemShulkerPearl);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemShulkerPearl);
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @SubscribeEvent
    public void onEntityInteract(EntityInteract event) {
        
        if (event.getSide().equals(Side.SERVER) && harvestablePearls && event.getTarget() instanceof EntityShulker) {
            
            ICustomData data = ShulkerDataHandler.getData(event.getTarget());
            
            if (data != null && data.getCooldown() <= 0) {
                
                event.getTarget().entityDropItem(new ItemStack(itemShulkerPearl), 0.5f);
                data.setCooldown(maxCooldown);
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event) {
        
        if (harvestablePearls && event.getEntity() instanceof EntityShulker) {
            
            final ICustomData data = ShulkerDataHandler.getData(event.getEntity());
            final int current = data.getCooldown();
            
            if (data != null && current > 0)
                data.setCooldown(current - 1);
        }
    }
}
