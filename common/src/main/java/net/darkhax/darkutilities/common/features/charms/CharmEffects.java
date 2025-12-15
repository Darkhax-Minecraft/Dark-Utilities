package net.darkhax.darkutilities.common.features.charms;

import net.darkhax.darkutilities.common.mixin.AccessorMobEffectInstance;
import net.darkhax.darkutilities.common.mixin.AccessorPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public enum CharmEffects {

    PRIDE(CharmEffects::prideCharmTick),
    Sloth(CharmEffects::slothCharmTick),
    Gluttony(CharmEffects::gluttonyCharmTick);

    public final CharmEffect effect;

    CharmEffects(CharmEffect effect) {
        this.effect = effect;
    }

    private static void prideCharmTick(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof LivingEntity living && !living.getActiveEffects().isEmpty()) {
            for (MobEffectInstance effectInstance : living.getActiveEffects()) {
                final MobEffect effect = effectInstance.getEffect().value();
                if (!effectInstance.isAmbient() && effect.getCategory() == MobEffectCategory.HARMFUL && !effect.isInstantenous() && effectInstance instanceof AccessorMobEffectInstance accessor) {
                    accessor.darkutils$tickDownDuration();
                }
            }
        }
    }

    private static void slothCharmTick(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof Player player) {
            if (player.isSleeping() && player instanceof AccessorPlayer accessor && player.getSleepTimer() < 90) {
                // Allow the player to skip the bed timer and instantly go to sleep.
                accessor.darkutils$setSleepTimer(90);
            }
            if (player instanceof ServerPlayer splayer) {
                // Phantoms and other mods use this stat to handle negative insomnia effects. Setting it to 0 ensures the player is always considered well rested.
                splayer.getStats().setValue(player, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), 0);
            }
        }
    }

    private static void gluttonyCharmTick(ItemStack stack, Level level, LivingEntity user) {
    }

    public static boolean hasCharm(LivingEntity entity, Item charm) {
        if (entity instanceof Player player) {
            for (ItemStack stack : player.getInventory().items) {
                if (stack.is(charm)) {
                    return true;
                }
            }
        }
        else {
            for (ItemStack stack : entity.getAllSlots()) {
                if (stack.is(charm)) {
                    return true;
                }
            }
        }
        return false;
    }
}