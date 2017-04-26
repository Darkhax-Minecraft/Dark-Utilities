package net.darkhax.darkutils.features.portalcharm;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.OBSIDIAN;
import static net.darkhax.bookshelf.lib.util.OreDictUtils.STRING;

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

public class FeaturePortalCharm extends Feature {

    public static Item itemPortalCharm;

    private static boolean craftable;

    @Override
    public void onPreInit () {

        itemPortalCharm = ModUtils.registerItem(new ItemPortalCharm(), "charm_portal");
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the portal charm be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
        	GameRegistry.addRecipe(new ShapedOreRecipe(itemPortalCharm, new Object[] { " s ", "oco", " o ", 's', STRING, 'o', OBSIDIAN, 'c', Items.ENDER_EYE }));
            GameRegistry.addRecipe(new ShapedOreRecipe(itemPortalCharm, new Object[] { " s ", "oco", " o ", 's', STRING, 'o', OBSIDIAN, 'c', Items.END_CRYSTAL }));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerItemInvModel(itemPortalCharm);
    }

    @SubscribeEvent
    public void onPlayerUpdate (LivingUpdateEvent event) {

        final Entity entity = event.getEntity();

        if (entity instanceof EntityPlayer && entity.inPortal) {

            final EntityPlayer player = (EntityPlayer) entity;

            if (PlayerUtils.playerHasItem((EntityPlayer) entity, itemPortalCharm, -1)) {
                player.portalCounter = 100;
            }
        }
    }
}