package net.darkhax.darkutils.items;

import java.util.List;

import com.google.common.collect.Multimap;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSourcedSword extends ItemSword {
    
    public DamageSource source;
    public ChatFormatting displayColor;
    public float effectChance;
    
    public ItemSourcedSword(ToolMaterial material, DamageSource source) {
        
        this(material, source, ChatFormatting.WHITE, 1f);
    }
    
    public ItemSourcedSword(ToolMaterial material, DamageSource source, float effectChance) {
        
        this(material, source, ChatFormatting.WHITE, effectChance);
    }
    
    public ItemSourcedSword(ToolMaterial material, DamageSource source, ChatFormatting displayColor, float effectChance) {
        
        super(material);
        this.setCreativeTab(DarkUtils.TAB);
        this.source = source;
        this.displayColor = displayColor;
        this.effectChance = effectChance;
    }
    
    @Override
    public EnumRarity getRarity (ItemStack itemstack) {
        
        return EnumRarity.RARE;
    }
    
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers (EntityEquipmentSlot slot) {
        
        final Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
        
        if (slot == EntityEquipmentSlot.MAINHAND)
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            
        return multimap;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        tooltip.add(this.displayColor + "+" + this.attackDamage + " " + I18n.translateToLocal("tooltip.darkutils.damagetype." + this.source.damageType));
    }
}