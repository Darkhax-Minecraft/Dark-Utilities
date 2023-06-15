package net.darkhax.darkutilities.features.charms;

import net.darkhax.bookshelf.api.serialization.Serializers;
import net.darkhax.bookshelf.api.util.MathsHelper;
import net.darkhax.bookshelf.mixin.accessors.effect.AccessorMobEffectInstance;
import net.darkhax.darkutilities.mixin.AccessorPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CharmEffects {

    public static void wardingCharmTick(ItemStack stack, Level world, Entity user, boolean selected) {

        if (user instanceof LivingEntity living && !living.getActiveEffects().isEmpty()) {

            final CompoundTag effectTag = stack.hasTag() ? stack.getTagElement("effect") : null;
            final float chance = Serializers.FLOAT.fromNBT(effectTag, "chance", 1f);

            for (MobEffectInstance effect : living.getActiveEffects()) {

                if (!effect.isAmbient() && effect.getEffect().getCategory() == MobEffectCategory.HARMFUL && !effect.getEffect().isInstantenous() && MathsHelper.tryPercentage(chance) && effect instanceof AccessorMobEffectInstance accessor) {

                    accessor.bookshelf$tickDownDuration();
                }
            }
        }
    }

    public static void sleepCharmTick(ItemStack stack, Level world, Entity user, boolean selected) {

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
}
