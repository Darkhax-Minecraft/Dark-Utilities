package net.darkhax.darkutils.features.filters;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Filters {
    
    public static boolean filterPlayer (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof PlayerEntity;
    }
    
    public static boolean filterUndead (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof LivingEntity && ((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.UNDEAD;
    }
    
    public static boolean filterArthropod (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof LivingEntity && ((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.ARTHROPOD;
    }
    
    public static boolean filterIllager (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof LivingEntity && ((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.ILLAGER;
    }
    
    public static boolean filterRaid (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return EntityTypeTags.RAIDERS.contains(entity.getType());
    }
    
    public static boolean filterHostile (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof MonsterEntity;
    }
    
    public static boolean filterAnimal (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof AnimalEntity;
    }
    
    public static boolean filterBaby (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof AgeableEntity && ((LivingEntity) entity).isChild();
    }
    
    public static boolean filterPet (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof TameableEntity;
    }
    
    public static boolean filterSlime (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof SlimeEntity;
    }
    
    public static boolean filterBoss (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return !entity.isNonBoss();
    }
    
    public static boolean filterVillager (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof AbstractVillagerEntity;
    }
    
    public static boolean filterFireImmune (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof LivingEntity && (((LivingEntity) entity).isImmuneToFire() || ((LivingEntity) entity).isPotionActive(Effects.FIRE_RESISTANCE));
    }
    
    public static boolean filterExplosionImmune (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof LivingEntity && ((LivingEntity) entity).isImmuneToExplosions();
    }
    
    public static boolean filterGolem (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof GolemEntity;
    }
    
    public static boolean filterWater (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity instanceof WaterMobEntity || entity instanceof LivingEntity && ((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.WATER;
    }
    
    public static boolean filterNamed (BlockState state, BlockPos pos, IBlockReader world, Entity entity) {
        
        return entity.hasCustomName();
    }
}