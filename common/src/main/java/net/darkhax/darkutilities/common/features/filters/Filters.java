package net.darkhax.darkutilities.common.features.filters;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public class Filters {

    public static final Predicate<Entity> PLAYER = e -> e instanceof Player;
    public static final Predicate<Entity> UNDEAD = tagFilter(EntityTypeTags.UNDEAD);
    public static final Predicate<Entity> ZOMBIE = tagFilter(EntityTypeTags.ZOMBIES);
    public static final Predicate<Entity> ARTHROPOD = tagFilter(EntityTypeTags.ARTHROPOD);
    public static final Predicate<Entity> ILLAGER = tagFilter(EntityTypeTags.ILLAGER);
    public static final Predicate<Entity> RAIDER = tagFilter(EntityTypeTags.RAIDERS);
    public static final Predicate<Entity> PIGLIN = e -> e instanceof AbstractPiglin;
    public static final Predicate<Entity> HOSTILE = e -> e instanceof Enemy;
    public static final Predicate<Entity> ANIMAL = e -> e instanceof Animal;
    public static final Predicate<Entity> BABY = e -> e instanceof AgeableMob ageable && ageable.isBaby();
    public static final Predicate<Entity> PET = e -> e instanceof TamableAnimal;
    public static final Predicate<Entity> SLIME = e -> e instanceof Slime;
    public static final Predicate<Entity> VILLAGER = e -> e instanceof AbstractVillager;
    public static final Predicate<Entity> FIRE_IMMUNE = e -> e instanceof LivingEntity living && (living.fireImmune() || living.hasEffect(MobEffects.FIRE_RESISTANCE));
    public static final Predicate<Entity> GOLEM = e -> e instanceof AbstractGolem;
    public static final Predicate<Entity> WATER = e -> e instanceof WaterAnimal || e.is(EntityTypeTags.AQUATIC) || e instanceof Drowned || e instanceof Frog;
    public static final Predicate<Entity> NAMED = Entity::hasCustomName;
    public static final Predicate<Entity> FREEZE_IMMUNE = tagFilter(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES);
    public static final Predicate<Entity> EQUIPMENT = hasEquipment();
    public static final Predicate<Entity> PASSENGER = e -> e.isPassenger() || !e.getPassengers().isEmpty();

    private static Predicate<Entity> tagFilter(TagKey<EntityType<?>> tag) {
        return e -> e.is(tag);
    }

    private static Predicate<Entity> hasEquipment() {
        return e -> {
            if (e instanceof LivingEntity living) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (!living.getItemBySlot(slot).isEmpty()) {
                        return true;
                    }
                }
            }
            return false;
        };
    }
}