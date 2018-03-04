package net.darkhax.darkutils.features.monolith;

import java.util.UUID;

import net.darkhax.bookshelf.data.AttributeOperation;
import net.darkhax.bookshelf.util.WorldUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class TileEntityMonolithSpawning extends TileEntityMonolith {

    /**
     * Mobs spawned with the monolith are given a 20% increase to health and damage.
     */
    private static final AttributeModifier SPAWN_BONUS = new AttributeModifier(UUID.fromString("4d59c07e-abea-480c-a237-c9bafb5161e2"), "monolith_spawn_bonus", 0.2, AttributeOperation.MULTIPLY.ordinal());

    private int spawnCount = 0;

    @Override
    public void onEntityUpdate () {

        super.onEntityUpdate();

        if (this.getWorld() instanceof WorldServer) {

            for (int attempts = 0; attempts < 25; attempts++) {

                WorldUtils.attemptChunkSpawn((WorldServer) this.getWorld(), this.pos, EnumCreatureType.MONSTER, TileEntityMonolithSpawning::buffMob);
            }
        }
    }

    private static void buffMob (EntityLiving living) {

        // System.out.println("Additional Spawn: " + living.getName());
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

        dataTag.setInteger("SpawnCount", this.spawnCount);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        this.spawnCount = dataTag.getInteger("SpawnCount");
    }
}