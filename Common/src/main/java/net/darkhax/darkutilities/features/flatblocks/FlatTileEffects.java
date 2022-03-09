package net.darkhax.darkutilities.features.flatblocks;

import net.darkhax.bookshelf.api.damagesource.FakePlayerDamageSource;
import net.darkhax.darkutilities.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class FlatTileEffects {

    private static final FakePlayerDamageSource DAMAGE_PLAYER_SOURCE = new FakePlayerDamageSource(new ResourceLocation(Constants.MOD_ID, "damage_plate_player"));

    public static final BlockFlatTile.CollisionEffect PUSH_WEAK = new CollisionEffectPush(0.06d);
    public static final BlockFlatTile.CollisionEffect PUSH_NORMAL = new CollisionEffectPush(0.3d);
    public static final BlockFlatTile.CollisionEffect PUSH_STRONG = new CollisionEffectPush(1.5d);
    public static final BlockFlatTile.CollisionEffect PUSH_ULTRA = new CollisionEffectPush(7.5d);

    public static final BlockFlatTile.CollisionEffect DAMAGE_GENERIC = livingEffect(e -> e.hurt(DamageSource.GENERIC, 6f));
    public static final BlockFlatTile.CollisionEffect DAMAGE_MAIM = livingEffect(e -> {

        final float damage = Math.min(e.getHealth() - 1f, 8f);

        if (e.getHealth() - damage >= 2f) {

            e.hurt(DamageSource.GENERIC, damage);
        }
    });
    public static final BlockFlatTile.CollisionEffect DAMAGE_PLAYER = livingEffect(e -> DAMAGE_PLAYER_SOURCE.causeDamage(e, 6f));

    public static final BlockFlatTile.CollisionEffect SLOWNESS = statusEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 1);
    public static final BlockFlatTile.CollisionEffect FATIGUE = statusEffect(MobEffects.DIG_SLOWDOWN, 100, 1);
    public static final BlockFlatTile.CollisionEffect DARKNESS = statusEffect(MobEffects.BLINDNESS, 100, 1);
    public static final BlockFlatTile.CollisionEffect HUNGER = statusEffect(MobEffects.HUNGER, 100, 0);
    public static final BlockFlatTile.CollisionEffect WEAKNESS = statusEffect(MobEffects.WEAKNESS, 100, 0);
    public static final BlockFlatTile.CollisionEffect POISON = statusEffect(MobEffects.POISON, 100, 0);
    public static final BlockFlatTile.CollisionEffect WITHER = statusEffect(MobEffects.WITHER, 100, 0);
    public static final BlockFlatTile.CollisionEffect GLOWING = statusEffect(MobEffects.GLOWING, 500, 1);
    public static final BlockFlatTile.CollisionEffect LEVITATION = statusEffect(MobEffects.LEVITATION, 100, 1);
    public static final BlockFlatTile.CollisionEffect UNLUCK = statusEffect(MobEffects.UNLUCK, 666, 1);
    public static final BlockFlatTile.CollisionEffect SLOWFALL = statusEffect(MobEffects.SLOW_FALLING, 100, 1);
    public static final BlockFlatTile.CollisionEffect OMEN = statusEffect(MobEffects.BAD_OMEN, 500, 0);
    public static final BlockFlatTile.CollisionEffect FROST = livingEffect(e -> {

        if (e.canFreeze()) {

            e.hurt(DamageSource.FREEZE, 1f);
            final int existingFrostTicks = e.getTicksFrozen();
            e.setTicksFrozen(Math.min(existingFrostTicks + 20, 80));
        }
    });
    public static final BlockFlatTile.CollisionEffect FLAME = livingEffect(e -> {

        if (!e.fireImmune()) {

            e.hurt(DamageSource.IN_FIRE, 1f);
            e.setSecondsOnFire(4);
        }
    });

    private static BlockFlatTile.CollisionEffect statusEffect(MobEffect effect, int duration, int amplifier) {

        return livingEffect(living -> living.addEffect(new MobEffectInstance(effect, duration, amplifier)));
    }

    private static BlockFlatTile.CollisionEffect livingEffect(Consumer<LivingEntity> effect) {

        return (s, w, p, e) -> {

            if (e instanceof LivingEntity living) {

                effect.accept(living);
            }
        };
    }
}