package net.darkhax.darkutils.features.witherslime;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EntitySlimeWither extends SlimeEntity {
    
    /**
     * Data for a dust particle that is completely black.
     */
    private static final IParticleData BLACK_DUST_PARTICLE = new RedstoneParticleData(0f, 0f, 0f, 0.65f);
    
    public EntitySlimeWither(EntityType<? extends SlimeEntity> type, World world) {
        
        super(type, world);
    }
    
    @Override
    protected IParticleData getSquishParticle () {
        
        // Spawn black dust instead of green slime.
        return BLACK_DUST_PARTICLE;
    }
    
    @Override
    protected void dealDamage (LivingEntity target) {
        
        super.dealDamage(target);
        
        if (this.isAlive()) {
            
            // Add 1.25 seconds of Witer per size of the slime.
            target.addPotionEffect(new EffectInstance(Effects.WITHER, 25 * this.getSlimeSize()));
        }
    }
    
    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn (IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        
        final ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        
        // Re=roll the size of the slime allowing for slimes up to size 6 to spawn.
        this.setSlimeSize(Math.max(1, worldIn.getRandom().nextInt(6)), true);
        return data;
    }
}