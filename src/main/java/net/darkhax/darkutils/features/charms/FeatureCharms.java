package net.darkhax.darkutils.features.charms;

import java.util.List;

import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@DUFeature(name = "Charms", description = "A collection of charms which have unique effects")
public class FeatureCharms extends Feature {

    public static Item itemAgressionCharm;

    public static Item itemFocusSash;

    public static Item itemGluttonyCharm;

    public static Item itemNullCharm;

    public static Item itemPortalCharm;

    public static Item itemSleepCharm;

    @Override
    public void onRegistry () {

        itemAgressionCharm = DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_agression");
        itemFocusSash = DarkUtils.REGISTRY.registerItem(new ItemCharm(), "focus_sash");
        itemGluttonyCharm = DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_gluttony");
        itemNullCharm = DarkUtils.REGISTRY.registerItem(new ItemNullCharm(), "charm_null");
        itemPortalCharm = DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_portal");
        itemSleepCharm = DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_sleep");

    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void onLivingHurt (LivingHurtEvent event) {

        final EntityLivingBase entityBase = event.getEntityLiving();

        // Focus Sash
        if (entityBase instanceof EntityPlayer && PlayerUtils.playerHasItem((EntityPlayer) entityBase, itemFocusSash, 0) && entityBase.getHealth() >= entityBase.getMaxHealth() && event.getAmount() >= entityBase.getHealth()) {

            event.setAmount(entityBase.getHealth() - 1f);
            ((EntityPlayer) entityBase).sendMessage(new TextComponentTranslation("chat.darkutils.focussash", TextFormatting.GREEN));
        }

        // Agression Charm
        if (event.getSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {

            final EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

            if (PlayerUtils.playerHasItem(player, itemAgressionCharm, 0)) {
                for (final EntityLivingBase entity : player.getEntityWorld().getEntitiesWithinAABB(event.getEntityLiving().getClass(), player.getEntityBoundingBox().expand(32, 32, 32))) {
                    entity.setRevengeTarget(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemUse (LivingEntityUseItemEvent.Tick event) {

        // Gluttony Charm
        if (event.getEntityLiving() instanceof EntityPlayer && PlayerUtils.playerHasItem((EntityPlayer) event.getEntityLiving(), itemGluttonyCharm, 0) && event.getItem() != null && event.getItem().getItem() instanceof ItemFood) {
            event.setDuration(0);
        }
    }

    @SubscribeEvent
    public void onItemPickedUp (EntityItemPickupEvent event) {

        // Null Charm
        final List<ItemStack> charms = PlayerUtils.getStacksFromPlayer(event.getEntityPlayer(), itemNullCharm, 0);

        for (final ItemStack charm : charms) {
            if (ItemNullCharm.isBlackListed(event.getItem().getItem(), charm)) {

                event.getItem().setDead();
                event.setCanceled(true);
                return;
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate (LivingUpdateEvent event) {

        // Portal Charm
        final Entity entity = event.getEntity();

        if (entity instanceof EntityPlayer && entity.inPortal) {

            final EntityPlayer player = (EntityPlayer) entity;

            if (PlayerUtils.playerHasItem((EntityPlayer) entity, itemPortalCharm, -1)) {
                player.portalCounter = 100;
            }
        }

        // Sleep Charm
        if (entity instanceof EntityPlayer) {

            final EntityPlayer player = (EntityPlayer) entity;

            if (player.isPlayerSleeping() && PlayerUtils.playerHasItem((EntityPlayer) entity, itemSleepCharm, -1)) {
                player.sleepTimer = 100;
            }
        }
    }
}