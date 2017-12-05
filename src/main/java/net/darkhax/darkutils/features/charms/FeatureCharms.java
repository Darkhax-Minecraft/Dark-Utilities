package net.darkhax.darkutils.features.charms;

import java.util.List;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@DUFeature(name = "Charms", description = "A collection of charms which have unique effects")
public class FeatureCharms extends Feature {

    public static ItemCharm itemAgressionCharm;

    public static ItemCharm itemFocusSash;

    public static ItemCharm itemGluttonyCharm;

    public static ItemCharm itemNullCharm;

    public static ItemCharm itemPortalCharm;

    public static ItemCharm itemSleepCharm;

    @Override
    public void onPreInit () {

        itemAgressionCharm = (ItemCharm) DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_agression");
        itemFocusSash = (ItemCharm) DarkUtils.REGISTRY.registerItem(new ItemCharm(), "focus_sash");
        itemGluttonyCharm = (ItemCharm) DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_gluttony");
        itemNullCharm = (ItemCharm) DarkUtils.REGISTRY.registerItem(new ItemNullCharm(), "charm_null");
        itemPortalCharm = (ItemCharm) DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_portal");
        itemSleepCharm = (ItemCharm) DarkUtils.REGISTRY.registerItem(new ItemCharm(), "charm_sleep");
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("agression_charm", new ItemStack(itemAgressionCharm), "sgs", "gfg", "wgw", 's', OreDictUtils.STRING, 'g', OreDictUtils.NUGGET_GOLD, 'f', Items.ROTTEN_FLESH, 'w', Items.GOLDEN_SWORD);
        DarkUtils.REGISTRY.addShapedRecipe("focus_sash", new ItemStack(itemFocusSash), " a ", "bcd", " e ", 'a', Items.BLAZE_POWDER, 'b', new ItemStack(Blocks.WOOL, 1, 4), 'c', Items.MAGMA_CREAM, 'd', new ItemStack(Blocks.WOOL, 1, 14), 'e', new ItemStack(Blocks.WOOL, 1, 1));
        DarkUtils.REGISTRY.addShapedRecipe("gluttony_charm", new ItemStack(itemGluttonyCharm), " s ", "wgw", " w ", 's', OreDictUtils.STRING, 'w', OreDictUtils.CROP_WHEAT, 'g', Items.GOLDEN_APPLE);
        DarkUtils.REGISTRY.addShapedRecipe("null_charm", new ItemStack(itemNullCharm), " s ", "uop", 's', OreDictUtils.STRING, 'u', new ItemStack(FeatureMaterial.itemMaterial, 1, 1), 'o', OreDictUtils.OBSIDIAN, 'p', OreDictUtils.ENDERPEARL);
        DarkUtils.REGISTRY.addShapedRecipe("portal_charm_1", new ItemStack(itemPortalCharm), " s ", "oio", " o ", 's', OreDictUtils.STRING, 'o', OreDictUtils.OBSIDIAN, 'i', Items.ENDER_EYE);
        DarkUtils.REGISTRY.addShapedRecipe("portal_charm_2", new ItemStack(itemPortalCharm), " s ", "oio", " o ", 's', OreDictUtils.STRING, 'o', OreDictUtils.OBSIDIAN, 'i', Items.END_CRYSTAL);
        DarkUtils.REGISTRY.addShapedRecipe("sleep_charm", new ItemStack(itemSleepCharm), "lsl", "sbs", "lsl", 'l', OreDictUtils.LEATHER, 's', OreDictUtils.STICK_WOOD, 'b', OreDictUtils.BED);
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    @Optional.Method(modid = "baubles")
    public void onItemCapability (AttachCapabilitiesEvent<ItemStack> event) {

        if (event.getObject().getItem() instanceof ItemCharm) {

            event.addCapability(new ResourceLocation("darkutils", "baubles_charm"), BaubleCapabilityHandler.INSTANCE);
        }
    }

    @SubscribeEvent
    public void onLivingHurt (LivingHurtEvent event) {

        if (event.getEntityLiving() instanceof EntityPlayer) {

            final EntityPlayer entityBase = (EntityPlayer) event.getEntityLiving();

            // Focus Sash
            if (entityBase instanceof EntityPlayer && itemFocusSash.hasItem(entityBase) && entityBase.getHealth() >= entityBase.getMaxHealth() && event.getAmount() >= entityBase.getHealth()) {

                event.setAmount(entityBase.getHealth() - 1f);
                entityBase.sendMessage(new TextComponentTranslation("chat.darkutils.focussash", TextFormatting.GREEN));
            }
        }

        // Agression Charm
        if (event.getSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {

            final EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

            if (itemAgressionCharm.hasItem(player)) {
                for (final EntityLivingBase entity : player.getEntityWorld().getEntitiesWithinAABB(event.getEntityLiving().getClass(), player.getEntityBoundingBox().expand(32, 32, 32))) {
                    entity.setRevengeTarget(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemUse (LivingEntityUseItemEvent.Tick event) {

        // Gluttony Charm
        if (event.getEntityLiving() instanceof EntityPlayer && itemGluttonyCharm.hasItem((EntityPlayer) event.getEntityLiving()) && !event.getItem().isEmpty() && event.getItem().getItem() instanceof ItemFood) {
            event.setDuration(0);
        }
    }

    @SubscribeEvent
    public void onItemPickedUp (EntityItemPickupEvent event) {

        // Null Charm
        final List<ItemStack> charms = itemNullCharm.getItem(event.getEntityPlayer());

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

            if (itemPortalCharm.hasItem(player)) {
                player.portalCounter = 100;
            }
        }

        // Sleep Charm
        if (entity instanceof EntityPlayer) {

            final EntityPlayer player = (EntityPlayer) entity;

            if (player.isPlayerSleeping() && itemSleepCharm.hasItem(player)) {
                player.sleepTimer = 100;
            }
        }
    }
}