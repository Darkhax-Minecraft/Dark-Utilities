package net.darkhax.darkutils.features.nullcharm;

import java.util.List;

import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureNullCharm extends Feature {
    
    public static Item itemNullCharm;
    private static boolean craftable;
    
    @Override
    public void onPreInit () {
        
        itemNullCharm = ModUtils.registerItem(new ItemNullCharm(), "charm_null");
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftable = config.getBoolean("Craftable", this.configName, true, "Should the null charm be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftable) {
            
            GameRegistry.addRecipe(new ShapedOreRecipe(itemNullCharm, new Object[] { "xyz", 'x', ModUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 1)), 'y', OreDictUtils.OBSIDIAN, 'z', Items.ENDER_PEARL }));
            GameRegistry.addRecipe(new ShapedOreRecipe(itemNullCharm, new Object[] { " x ", " y ", " z ", 'x', ModUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 1)), 'y', OreDictUtils.OBSIDIAN, 'z', Items.ENDER_PEARL }));
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemNullCharm);
    }
    
    @SubscribeEvent
    public void onItemPickedUp (EntityItemPickupEvent event) {
        
        final List<ItemStack> charms = PlayerUtils.getStacksFromPlayer(event.getEntityPlayer(), itemNullCharm, 0);
        
        for (ItemStack charm : charms) {
            if (ItemNullCharm.isBlackListed(event.getItem().getEntityItem(), charm)) {
                
                event.getItem().setDead();
                event.setCanceled(true);
                return;
            }
        }
    }
}