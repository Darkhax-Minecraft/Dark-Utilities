package net.darkhax.darkutilities.features.tomes;

import net.darkhax.bookshelf.api.util.ExperienceHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

@FunctionalInterface
public interface TomeEffect<T, R> {

    @Nullable
    R apply(ItemStack usedStack, Player user, InteractionHand hand, T target);

    static <T, R> TomeEffect<T, R> withCooldown(TomeEffect<T, R> effect, int cooldown) {

        return (stack, user, hand, target) -> {

            final ItemCooldowns cooldowns = user.getCooldowns();

            if (!cooldowns.isOnCooldown(stack.getItem())) {

                cooldowns.addCooldown(stack.getItem(), cooldown);
                return effect.apply(stack, user, hand, target);
            }

            return null;
        };
    }

    static <T, R> TomeEffect<T, R> withExpCost(TomeEffect<T, R> effect, int pointCost) {

        return (stack, user, hand, target) -> {

            if (ExperienceHelper.chargeExperiencePoints(user, pointCost)) {

                return effect.apply(stack, user, hand, target);
            }

            return null;
        };
    }
}