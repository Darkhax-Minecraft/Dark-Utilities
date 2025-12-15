package net.darkhax.darkutilities.common.mixin;

import net.darkhax.darkutilities.common.features.charms.CharmEffects;
import net.darkhax.darkutilities.common.features.charms.ItemCharm;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItem {

    @Inject(method = "getUseDuration", at = @At("RETURN"), cancellable = true)
    private void getUseDuration(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (stack.has(DataComponents.FOOD) && cir.getReturnValue() > 5 && CharmEffects.hasCharm(entity, ItemCharm.GLUTTONY.get())) {
            cir.setReturnValue(5);
        }
    }
}