package net.darkhax.darkutils.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.addons.baubles.BaublesAddon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class ItemRingEnchanted extends Item implements IBauble {
    
    public static String[] varients = new String[] { "knockback", "fire", "fortune", "loot", "lure", "luck", "efficiency" };
    public static Enchantment[] enchants = new Enchantment[] { Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.FORTUNE, Enchantments.LOOTING, Enchantments.LURE, Enchantments.LUCK_OF_THE_SEA, Enchantments.EFFICIENCY };
    
    public ItemRingEnchanted() {
        
        this.setUnlocalizedName("darkutils.ring");
        this.setCreativeTab(DarkUtils.TAB);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
    }
    
    @Override
    public EnumRarity getRarity (ItemStack itemstack) {
        
        return EnumRarity.RARE;
    }
    
    @Override
    public int getMetadata (int damage) {
        
        return damage;
    }
    
    @Override
    public String getUnlocalizedName (ItemStack stack) {
        
        final int meta = stack.getMetadata();
        
        if (!(meta >= 0 && meta < varients.length))
            return super.getUnlocalizedName() + "." + varients[0];
            
        return super.getUnlocalizedName() + "." + varients[meta];
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick (ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        
        if (!world.isRemote && !BaublesAddon.equipBauble(player, stack, 1))
            BaublesAddon.equipBauble(player, stack, 2);
            
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        
        for (int meta = 0; meta < varients.length; meta++)
            subItems.add(new ItemStack(this, 1, meta));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        final int meta = stack.getMetadata();
        
        if (meta > -1 && meta < varients.length)
            tooltip.add(ChatFormatting.GOLD + enchants[meta].getTranslatedName(1));
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