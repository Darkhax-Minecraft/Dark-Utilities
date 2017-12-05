package net.darkhax.darkutils.features.attributerings;

import java.util.UUID;

import net.darkhax.bookshelf.data.AttributeOperation;
import net.darkhax.bookshelf.util.MathsUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public enum AttrRingType {

    HEALTH(SharedMonsterAttributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("b6168900-17f8-4079-9355-78a498a86654"), "ring_max_health", 2.0d, AttributeOperation.ADDITIVE.ordinal())),
    KNOCKBACK(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("44df28ca-7317-47b1-90e8-a43999c137be"), "ring_knockback_res", 0.1d, AttributeOperation.MULTIPLY.ordinal())),
    SPEED(SharedMonsterAttributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("868df2f2-bfbe-4567-a26a-cbde8c96dd4a"), "ring_movement_speed", 0.1d, AttributeOperation.MULTIPLY.ordinal())),
    DAMAGE(SharedMonsterAttributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("587b65d5-6e90-4129-8f29-c6e6c23ab99e"), "ring_attack_damage", 2.0d, AttributeOperation.ADDITIVE.ordinal())),
    ARMOR(SharedMonsterAttributes.ARMOR, new AttributeModifier(UUID.fromString("746871b7-c19f-4d86-98a6-3c2c2ce9f9ae"), "ring_armor", 2.0d, AttributeOperation.ADDITIVE.ordinal())),
    LUCK(SharedMonsterAttributes.LUCK, new AttributeModifier(UUID.fromString("0ce2745a-8960-4db4-a770-507e14bdb499"), "ring_luck", 1.0d, AttributeOperation.ADDITIVE.ordinal())),
    ATTACK_SPEED(SharedMonsterAttributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("c9c5001e-6bf6-4812-8748-a42ed04b809b"), "ring_attack_speed", 0.1d, AttributeOperation.MULTIPLY.ordinal())),
    ARMOR_TOUGHNESS(SharedMonsterAttributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("db762e92-9f19-4006-9f53-d9f9a3a17d88"), "ring_armor_toughness", 4.0d, AttributeOperation.ADDITIVE.ordinal())),
    REACH(EntityPlayer.REACH_DISTANCE, new AttributeModifier(UUID.fromString("e6134098-e755-47b4-a43f-4d39609989f5"), "ring_reach", 2.0d, AttributeOperation.ADDITIVE.ordinal()));

    final IAttribute attribute;
    final AttributeModifier modifier;

    AttrRingType (IAttribute attribute, AttributeModifier modifier) {

        this.attribute = attribute;
        this.modifier = modifier;
    }

    public void apply (EntityLivingBase entity) {

        final ModifiableAttributeInstance instance = (ModifiableAttributeInstance) entity.getEntityAttribute(this.attribute);
        instance.applyModifier(this.modifier);
    }

    public void remove (EntityLivingBase entity) {

        final ModifiableAttributeInstance instance = (ModifiableAttributeInstance) entity.getEntityAttribute(this.attribute);
        instance.removeModifier(this.modifier);
    }

    public String getTooltipText () {

        return TextFormatting.BLUE + " " + I18n.translateToLocalFormatted("attribute.modifier.plus." + this.modifier.getOperation(), MathsUtils.round(this.modifier.getAmount(), 1), I18n.translateToLocal("attribute.name." + this.attribute.getName()));
    }
}