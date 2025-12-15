package net.darkhax.darkutilities.common.features.charms;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface CharmEffect {

    void onUserTick(ItemStack stack, Level level, LivingEntity user);
}
