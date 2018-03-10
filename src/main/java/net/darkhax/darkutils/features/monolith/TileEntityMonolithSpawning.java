package net.darkhax.darkutils.features.monolith;

import java.util.UUID;

import net.darkhax.bookshelf.data.AttributeOperation;
import net.darkhax.bookshelf.util.WorldUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TileEntityMonolithSpawning extends TileEntityMonolith {

    /**
     * Mobs spawned with the monolith are given a 20% increase to health and damage.
     */
    private static final AttributeModifier SPAWN_BONUS = new AttributeModifier(UUID.fromString("4d59c07e-abea-480c-a237-c9bafb5161e2"), "monolith_spawn_bonus", 0.2, AttributeOperation.MULTIPLY.ordinal());

    private int spawnCount = 0;
    private BlockPos down = null;

    @Override
    public void onEntityUpdate () {

        super.onEntityUpdate();

        if (this.down == null) {

            this.down = this.getPos().down();
        }

        if (this.getWorld() instanceof WorldServer) {

            for (int attempts = 0; attempts < 5; attempts++) {

                WorldUtils.attemptChunkSpawn((WorldServer) this.getWorld(), this.pos, EnumCreatureType.MONSTER, this::buffMob);
            }
        }
    }

    private void buffMob (EntityLiving living) {

        final ModifiableAttributeInstance attack = (ModifiableAttributeInstance) living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        final ModifiableAttributeInstance health = (ModifiableAttributeInstance) living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        if (attack != null && health != null) {

            if (!attack.hasModifier(SPAWN_BONUS)) {

                attack.applyModifier(SPAWN_BONUS);
            }

            if (!health.hasModifier(SPAWN_BONUS)) {

                health.applyModifier(SPAWN_BONUS);
                living.setHealth(living.getMaxHealth());
            }
        }

        this.spawnCount++;
    }

    @Override
    public boolean onBlockActivated (World worldIn, EntityPlayer playerIn) {

        playerIn.sendStatusMessage(new TextComponentTranslation("chat.darkutils.monolith.spawning", this.spawnCount), true);
        return true;
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