package net.darkhax.darkutilities.common.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MobEffectInstance.class)
public interface AccessorMobEffectInstance {

    @Invoker("tickDownDuration")
    int darkutils$tickDownDuration();
}
