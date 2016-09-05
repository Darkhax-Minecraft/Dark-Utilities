package net.darkhax.darkutils.features.focussash;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureFocusSash extends Feature {
    
    public static Item itemFocusSash;
    private static boolean craftable;
    
    @Override
    public void onPreInit () {
        
        itemFocusSash = new ItemFocusSash();
        ModUtils.registerItem(itemFocusSash, "focus_sash");
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftable = config.getBoolean("Craftable", this.configName, true, "Should the focus sash be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftable)
            GameRegistry.addShapedRecipe(new ItemStack(itemFocusSash), new Object[] { " p ", "ycr", " o ", 'p', Items.BLAZE_POWDER, 'y', new ItemStack(Blocks.WOOL, 1, 4), 'c', Items.MAGMA_CREAM, 'r', new ItemStack(Blocks.WOOL, 1, 14), 'o', new ItemStack(Blocks.WOOL, 1, 1) });
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemFocusSash);
    }
    
    @SubscribeEvent
    public void onEntityHurt (LivingHurtEvent event) {
        
        final EntityLivingBase entity = event.getEntityLiving();
        
        if (entity instanceof EntityPlayer && PlayerUtils.playerHasItem((EntityPlayer) entity, itemFocusSash, 0) && entity.getHealth() >= entity.getMaxHealth() && event.getAmount() >= entity.getHealth()) {
            
            event.setAmount(entity.getHealth() - 1f);
            ((EntityPlayer) entity).addChatComponentMessage(new TextComponentTranslation("chat.darkutils.focussash", ChatFormatting.GREEN));
        }
    }
}