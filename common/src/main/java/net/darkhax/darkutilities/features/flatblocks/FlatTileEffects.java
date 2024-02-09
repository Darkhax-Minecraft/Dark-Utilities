package net.darkhax.darkutilities.features.flatblocks;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class FlatTileEffects {

    private static final ResourceKey<DamageType> FAKE_PLAYER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("bookshelf", "fake_player"));

    public static final BlockFlatTile.CollisionEffect PUSH_WEAK = new CollisionEffectPush(0.06d);
    public static final BlockFlatTile.CollisionEffect PUSH_NORMAL = new CollisionEffectPush(0.3d);
    public static final BlockFlatTile.CollisionEffect PUSH_STRONG = new CollisionEffectPush(1.5d);
    public static final BlockFlatTile.CollisionEffect PUSH_ULTRA = new CollisionEffectPush(7.5d);

    public static final BlockFlatTile.CollisionEffect DAMAGE_GENERIC = livingEffect(e -> e.hurt(e.level().damageSources().generic(), 6f));
    public static final BlockFlatTile.CollisionEffect DAMAGE_MAIM = livingEffect(e -> {

        if (e.getHealth() >= 2f) {

            e.hurt(e.level().damageSources().generic(), 1f);
        }
    });
    public static final BlockFlatTile.CollisionEffect DAMAGE_PLAYER = livingEffect(e -> e.hurt(getSource(e.level(), FAKE_PLAYER_DAMAGE), 6f));
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

            e.hurt(e.level().damageSources().freeze(), 1f);
            final int existingFrostTicks = e.getTicksFrozen();
            e.setTicksFrozen(Math.min(existingFrostTicks + 20, 80));
        }
    });
    public static final BlockFlatTile.CollisionEffect FLAME = livingEffect(e -> {

        if (!e.fireImmune()) {

            e.hurt(e.level().damageSources().inFire(), 1f);
            e.setSecondsOnFire(4);
        }
    });
    public static final BlockFlatTile.CollisionEffect SMITE = livingEffect(e -> {
        if (e.getMobType() == MobType.UNDEAD) {
            e.hurt(e.level().damageSources().generic(), 12f);
        }
    });
    public static final BlockFlatTile.CollisionEffect BANE = livingEffect(e -> {
        if (e.getMobType() == MobType.ARTHROPOD) {
            e.hurt(e.level().damageSources().generic(), 12f);
            e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 3));
        }
    });

    public static final BlockFlatTile.CollisionEffect ANCHOR = (state, world, pos, entity) -> {

        if (entity instanceof LivingEntity living && !living.isCrouching() && !(living instanceof Player)) {

            final Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            final BlockPos offset = pos.relative(direction.getOpposite(), 5);
            living.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(offset.getX() + 0.5f, offset.getY() + 1.5f, offset.getZ() + 0.5f));
            entity.setPos(pos.getX() + 0.5f, pos.getY() + 0.0625D, pos.getZ() + 0.5f);
        }
    };

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

    private static DamageSource getSource(Level level, ResourceKey<DamageType> id) {

        final Registry<DamageType> registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        final Holder.Reference<DamageType> damage = registry.getHolderOrThrow(id);
        return new DamageSource(damage);
    }
}