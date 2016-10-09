package net.darkhax.darkutils.features.misc;

import java.util.UUID;

import net.darkhax.bookshelf.lib.ModifierOperation;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureSheepArmor extends Feature {
    
    public static AttributeModifier sheepArmor = new AttributeModifier(UUID.fromString("6e915cea-3f18-485d-a818-373fe4f75f7f"), "sheep_armor", 1.0d, ModifierOperation.ADDITIVE.ordinal());
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        sheepArmor = new AttributeModifier(UUID.fromString("6e915cea-3f18-485d-a818-373fe4f75f7f"), "sheep_armor", config.getFloat("Armor Points", this.configName, 1f, 0f, 512f, "How many armor points should sheep have while they have wool?"), ModifierOperation.ADDITIVE.ordinal());
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @SubscribeEvent
    public void onEntityUpdate (LivingUpdateEvent event) {
        
        final EntityLivingBase entity = event.getEntityLiving();
        
        if (entity instanceof EntitySheep) {
            
            final ModifiableAttributeInstance instance = (ModifiableAttributeInstance) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR);
            final boolean hasModifier = instance.hasModifier(sheepArmor);
            final boolean isSheared = ((EntitySheep) entity).getSheared();
            
            if (!isSheared && !hasModifier)
                instance.applyModifier(sheepArmor);
            
            else if (isSheared && hasModifier)
                instance.removeModifier(sheepArmor);
        }
    }
}
