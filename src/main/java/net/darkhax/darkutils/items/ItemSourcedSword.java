package net.darkhax.darkutils.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSourcedSword extends ItemSword {
    
    public DamageSource source;
    public EnumChatFormatting displayColor;
    public float effectChance;
    
    public ItemSourcedSword(ToolMaterial material, DamageSource source) {
        
        this(material, source, EnumChatFormatting.WHITE, 1f);
    }
    
    public ItemSourcedSword(ToolMaterial material, DamageSource source, float effectChance) {
        
        this(material, source, EnumChatFormatting.WHITE, effectChance);
    }
    
    public ItemSourcedSword(ToolMaterial material, DamageSource source, EnumChatFormatting displayColor, float effectChance) {
        
        super(material);
        this.setCreativeTab(DarkUtils.tab);
        this.source = source;
        this.displayColor = displayColor;
        this.effectChance = effectChance;
    }
    
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers () {
        
        return HashMultimap.<String, AttributeModifier> create();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        tooltip.add(this.displayColor + "+" + this.attackDamage + " " + StatCollector.translateToLocal("tooltip.damagetype." + source.damageType));
    }
}