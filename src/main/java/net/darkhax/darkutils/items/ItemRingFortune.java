package net.darkhax.darkutils.items;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class ItemRingFortune extends Item implements IBauble {
    
    public ItemRingFortune() {
        
        this.setUnlocalizedName("darkutils.ring.fortune");
        this.setCreativeTab(DarkUtils.tab);
    }
    
    @Override
    public EnumRarity getRarity (ItemStack itemstack) {
        
        return EnumRarity.RARE;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        tooltip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.darkutils.ring.fortune"));
    }
    
    @Override
    @Optional.Method(modid = "Baubles")
    public boolean canEquip (ItemStack stack, EntityLivingBase entity) {
        
        return true;
    }
    
    @Override
    @Optional.Method(modid = "Baubles")
    public boolean canUnequip (ItemStack stack, EntityLivingBase entity) {
        
        return true;
    }
    
    @Override
    @Optional.Method(modid = "Baubles")
    public BaubleType getBaubleType (ItemStack stack) {
        
        return BaubleType.RING;
    }
    
    @Override
    @Optional.Method(modid = "Baubles")
    public void onEquipped (ItemStack stack, EntityLivingBase entity) {
    
    }
    
    @Override
    @Optional.Method(modid = "Baubles")
    public void onUnequipped (ItemStack stack, EntityLivingBase entity) {
    
    }
    
    @Override
    @Optional.Method(modid = "Baubles")
    public void onWornTick (ItemStack stack, EntityLivingBase entity) {
    
    }
}
