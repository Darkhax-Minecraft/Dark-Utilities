package net.darkhax.darkutils.features.monolith;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import net.darkhax.bookshelf.data.AttributeOperation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class TileEntityMonolithSpawning extends TileEntityMonolith {

    /**
     * Mobs spawned with the monolith are given a 20% increase to health and damage.
     */
    public static final AttributeModifier SPAWN_BONUS = new AttributeModifier(UUID.fromString("4d59c07e-abea-480c-a237-c9bafb5161e2"), "monolith_spawn_bonus", 0.2, AttributeOperation.MULTIPLY.ordinal());

    private final List<EntityLivingBase> trackedEntities = new ArrayList<>();

    @Override
    public void onEntityUpdate () {

        super.onEntityUpdate();

        final ListIterator<EntityLivingBase> itr = this.trackedEntities.listIterator();

        while (itr.hasNext()) {

            final EntityLivingBase entity = itr.next();

            if (entity == null || entity.isDead) {

                itr.remove();
            }

            final ModifiableAttributeInstance attack = (ModifiableAttributeInstance) entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            final ModifiableAttributeInstance health = (ModifiableAttributeInstance) entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

            if (attack != null && health != null) {

                if (!attack.hasModifier(SPAWN_BONUS)) {

                    attack.applyModifier(SPAWN_BONUS);
                }

                if (!health.hasModifier(SPAWN_BONUS)) {

                    health.applyModifier(SPAWN_BONUS);
                    entity.setHealth(entity.getMaxHealth());
                }

                itr.remove();
            }
        }
    }

    @Override
    public void onSpawnCheck (CheckSpawn event) {

        // Spawning Monolith will allways allow mobs to spawn.
        event.setResult(Result.ALLOW);

        // Start tracking the entity, so it can be modified later.
        this.trackedEntities.add(event.getEntityLiving());
    }
}