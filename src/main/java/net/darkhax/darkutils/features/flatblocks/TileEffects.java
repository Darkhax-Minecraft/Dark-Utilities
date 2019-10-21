package net.darkhax.darkutils.features.flatblocks;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffect;
import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffectImport;
import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffectPush;
import net.darkhax.darkutils.features.flatblocks.tick.TickEffect;
import net.darkhax.darkutils.features.flatblocks.tick.TickEffectExport;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

public class TileEffects {
    
    public static final GameProfile PLAYER_RUNE_PROFILE = new GameProfile(UUID.fromString("adf01ac0-c6c9-4f85-8079-3fc1a758c498"), "DUPlayerDamageRune");
    
    public static final CollisionEffect PUSH_WEAK = new CollisionEffectPush(0.06d);
    public static final CollisionEffect PUSH_NORMAL = new CollisionEffectPush(0.3d);
    public static final CollisionEffect PUSH_STRONG = new CollisionEffectPush(1.5d);
    
    public static final CollisionEffect IMPORT_WEAK = new CollisionEffectImport(0.06d, 1);
    public static final CollisionEffect IMPORT_NORMAL = new CollisionEffectImport(0.3d, 16);
    public static final CollisionEffect IMPORT_STRONG = new CollisionEffectImport(1.5d, 32);
    
    public static final CollisionEffect RUNE_DAMAGE = TileEffects::effectRuneDamage;
    public static final CollisionEffect RUNE_DAMAGE_PLAYER = TileEffects::effectRunePlayerDamage;
    public static final CollisionEffect RUNE_POISON = TileEffects::effectRunePoison;
    public static final CollisionEffect RUNE_WEAKNESS = TileEffects::effectRuneWeakness;
    public static final CollisionEffect RUNE_SLOWNESS = TileEffects::effectRuneSlowness;
    public static final CollisionEffect RUNE_WITHER = TileEffects::effectRuneWither;
    public static final CollisionEffect RUNE_FIRE = TileEffects::effectRuneFire;
    public static final CollisionEffect RUNE_FATIGUE = TileEffects::effectRuneFatigue;
    public static final CollisionEffect RUNE_GLOWING = TileEffects::effectRuneGlowing;
    public static final CollisionEffect RUNE_HUNGER = TileEffects::effectRuneHunger;
    public static final CollisionEffect RUNE_BLINDNESS = TileEffects::effectRuneBlindess;
    public static final CollisionEffect RUNE_NAUSEA = TileEffects::effectRuneNausea;
    
    public static final TickEffect EXPORT_INVENTORY = new TickEffectExport();
    
    private static void effectRunePlayerDamage (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity && world instanceof ServerWorld) {
            
            ((LivingEntity) entity).attackEntityFrom(DamageSource.causePlayerDamage(FakePlayerFactory.get((ServerWorld) world, PLAYER_RUNE_PROFILE)), 4f);
        }
    }
    
    private static void effectRuneDamage (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).attackEntityFrom(DamageSource.MAGIC, 4f);
        }
    }
    
    private static void effectRunePoison (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.POISON, 100));
        }
    }
    
    private static void effectRuneWeakness (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100));
        }
    }
    
    private static void effectRuneSlowness (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100));
        }
    }
    
    private static void effectRuneWither (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.WITHER, 100));
        }
    }
    
    private static void effectRuneFire (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).setFire(4);
        }
    }
    
    private static void effectRuneFatigue (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 500));
        }
    }
    
    private static void effectRuneGlowing (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.GLOWING, 200));
        }
    }
    
    private static void effectRuneHunger (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.HUNGER, 100, 1));
        }
    }
    
    private static void effectRuneBlindess (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 200));
        }
    }
    
    private static void effectRuneNausea (BlockState state, World world, BlockPos pos, Entity entity) {
        
        if (entity instanceof LivingEntity) {
            
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.NAUSEA, 100));
        }
    }
}
