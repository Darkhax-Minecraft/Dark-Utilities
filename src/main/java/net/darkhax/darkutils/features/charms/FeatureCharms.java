package net.darkhax.darkutils.features.charms;

import static net.darkhax.bookshelf.util.OreDictUtils.LEATHER;
import static net.darkhax.bookshelf.util.OreDictUtils.OBSIDIAN;
import static net.darkhax.bookshelf.util.OreDictUtils.STICK_WOOD;
import static net.darkhax.bookshelf.util.OreDictUtils.STRING;

import java.util.List;

import net.darkhax.bookshelf.util.CraftingUtils;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

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

    @Override
    public void setupRecipes () {

        GameRegistry.addRecipe(new ShapedOreRecipe(itemAgressionCharm, new Object[] { "rgr", "gfg", "sgs", 's', Items.GOLDEN_SWORD, 'g', OreDictUtils.NUGGET_GOLD, 'f', Items.ROTTEN_FLESH, 'r', OreDictUtils.STRING }));
        GameRegistry.addShapedRecipe(new ItemStack(itemFocusSash), new Object[] { " p ", "ycr", " o ", 'p', Items.BLAZE_POWDER, 'y', new ItemStack(Blocks.WOOL, 1, 4), 'c', Items.MAGMA_CREAM, 'r', new ItemStack(Blocks.WOOL, 1, 14), 'o', new ItemStack(Blocks.WOOL, 1, 1) });
        GameRegistry.addRecipe(new ShapedOreRecipe(itemGluttonyCharm, new Object[] { " s ", "waw", " w ", 's', OreDictUtils.STRING, 'w', OreDictUtils.CROP_WHEAT, 'a', Items.GOLDEN_APPLE }));
        GameRegistry.addRecipe(new ShapedOreRecipe(itemNullCharm, new Object[] { " s ", "xyz", 'x', CraftingUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 1)), 'y', OreDictUtils.OBSIDIAN, 'z', Items.ENDER_PEARL, 's', OreDictUtils.STRING }));
        GameRegistry.addRecipe(new ShapedOreRecipe(itemPortalCharm, new Object[] { " s ", "oco", " o ", 's', STRING, 'o', OBSIDIAN, 'c', Items.END_CRYSTAL }));
        GameRegistry.addRecipe(new ShapedOreRecipe(itemPortalCharm, new Object[] { " s ", "oco", " o ", 's', STRING, 'o', OBSIDIAN, 'c', Items.ENDER_EYE }));
        GameRegistry.addRecipe(new ShapedOreRecipe(itemSleepCharm, new Object[] { "lsl", "sbs", "lsl", 's', STICK_WOOD, 'l', LEATHER, 'b', OreDictUtils.BED }));

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
        if (event.getSource() != null && event.getSource().getEntity() instanceof EntityPlayer) {

            final EntityPlayer player = (EntityPlayer) event.getSource().getEntity();

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
            if (ItemNullCharm.isBlackListed(event.getItem().getEntityItem(), charm)) {

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