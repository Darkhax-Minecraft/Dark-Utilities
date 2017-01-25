package net.darkhax.darkutils.features.gluttonycharm;

import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureGluttonyCharm extends Feature {

    public static Item itemGluttonyCharm;

    private static boolean craftable;

    @Override
    public void onPreInit () {

        itemGluttonyCharm = ModUtils.registerItem(new ItemGluttonyCharm(), "charm_gluttony");
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the gluttony charm be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable)
            GameRegistry.addRecipe(new ShapedOreRecipe(itemGluttonyCharm, new Object[] { " s ", "waw", " w ", 's', OreDictUtils.STRING, 'w', OreDictUtils.CROP_WHEAT, 'a', Items.GOLDEN_APPLE }));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerItemInvModel(itemGluttonyCharm);
    }

    @SubscribeEvent
    public void onItemUse (LivingEntityUseItemEvent.Tick event) {

        if (event.getEntityLiving() instanceof EntityPlayer && PlayerUtils.playerHasItem((EntityPlayer) event.getEntityLiving(), itemGluttonyCharm, 0) && event.getItem() != null && event.getItem().getItem() instanceof ItemFood)
            event.setDuration(0);
    }
}