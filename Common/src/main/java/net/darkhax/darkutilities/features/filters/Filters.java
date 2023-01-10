package net.darkhax.darkutilities.features.filters;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class Filters {

    public static final Predicate<Entity> PLAYER = e -> e instanceof Player;
    public static final Predicate<Entity> UNDEAD = attributeFilter(MobType.UNDEAD);
    public static final Predicate<Entity> ARTHROPOD = attributeFilter(MobType.ARTHROPOD);
    public static final Predicate<Entity> ILLAGER = attributeFilter(MobType.ILLAGER);
    public static final Predicate<Entity> RAIDER = tagFilter(EntityTypeTags.RAIDERS);
    public static final Predicate<Entity> HOSTILE = e -> e instanceof Enemy;
    public static final Predicate<Entity> ANIMAL = e -> e instanceof Animal;
    public static final Predicate<Entity> BABY = e -> e instanceof AgeableMob ageable && ageable.isBaby();
    public static final Predicate<Entity> PET = e -> e instanceof TamableAnimal;
    public static final Predicate<Entity> SLIME = e -> e instanceof Slime;
    public static final Predicate<Entity> BOSS = null; // No boss check?
    public static final Predicate<Entity> VILLAGER = e -> e instanceof AbstractVillager;
    public static final Predicate<Entity> FIRE_IMMUNE = e -> e instanceof LivingEntity living && (living.fireImmune() || living.hasEffect(MobEffects.FIRE_RESISTANCE));
    public static final Predicate<Entity> GOLEM = e -> e instanceof AbstractGolem;
    public static final Predicate<Entity> WATER = e -> e instanceof WaterAnimal || (e instanceof LivingEntity living && living.getMobType() == MobType.WATER) || e instanceof Drowned;
    public static final Predicate<Entity> NAMED = Entity::hasCustomName;
    public static final Predicate<Entity> FREEZE_IMMUNE = tagFilter(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES);
    public static final Predicate<Entity> EQUIPMENT = hasEquipment();
    public static final Predicate<Entity> PASSENGER = e -> e.isPassenger() || !e.getPassengers().isEmpty();

    private static Predicate<Entity> attributeFilter(MobType type) {

        return e -> e instanceof LivingEntity living && living.getMobType() == type;
    }

    private static Predicate<Entity> tagFilter(TagKey<EntityType<?>> tag) {

        return e -> e.getType().is(tag);
    }

    private static Predicate<Entity> hasEquipment() {

        return e -> {

            for (ItemStack armor : e.getArmorSlots()) {

                if (!armor.isEmpty()) {

                    return true;
                }
            }

            for (ItemStack held : e.getHandSlots()) {

                if (!held.isEmpty()) {

                    return true;
                }
            }

            return false;
        };
    }
}