package net.darkhax.darkutils.features.agressioncharm;

import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureAgressionCharm extends Feature {

    public static Item itemAgressionCharm;

    private static boolean craftable;

    @Override
    public void onPreInit () {

        itemAgressionCharm = ModUtils.registerItem(new ItemAgressionCharm(), "charm_agression");
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the agression charm be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            GameRegistry.addRecipe(new ShapedOreRecipe(itemAgressionCharm, new Object[] { "sgs", "gfg", "sgs", 's', Items.GOLDEN_SWORD, 'g', OreDictUtils.NUGGET_GOLD, 'f', Items.ROTTEN_FLESH }));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerItemInvModel(itemAgressionCharm);
    }

    @SubscribeEvent
    public void onItemUse (LivingHurtEvent event) {

        if (event.getSource() != null && event.getSource().getEntity() instanceof EntityPlayer) {

            final EntityPlayer player = (EntityPlayer) event.getSource().getEntity();

            if (PlayerUtils.playerHasItem(player, itemAgressionCharm, 0)) {
                for (final EntityLivingBase entity : player.getEntityWorld().getEntitiesWithinAABB(event.getEntityLiving().getClass(), player.getEntityBoundingBox().expand(32, 32, 32))) {
                    entity.setRevengeTarget(player);
                }
            }
        }
    }
}