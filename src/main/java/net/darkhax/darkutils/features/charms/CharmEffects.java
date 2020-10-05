package net.darkhax.darkutils.features.charms;

import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.addons.curios.CuriosAddon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class CharmEffects {
    
    public static void applySleepCharmEffect (Entity user, ItemStack item) {
        
        if (user instanceof PlayerEntity) {
            
            // Set sleep timer above threshold for instant sleeping.
            if (((PlayerEntity) user).isSleeping()) {
                
                ((PlayerEntity) user).sleepTimer = 100;
            }
            
            // Passively decrease the time since last rest stat. This is what phantom
            // spawning looks at.
            if (user instanceof ServerPlayerEntity) {
                
                final Stat<?> sleepStat = Stats.CUSTOM.get(Stats.TIME_SINCE_REST);
                final ServerStatisticsManager statsManager = ((ServerPlayerEntity) user).getStats();
                final int timeSinceLastSlept = statsManager.getValue(sleepStat);
                statsManager.setValue((PlayerEntity) user, sleepStat, Math.max(timeSinceLastSlept - 100, 0));
            }
        }
    }
    
    public static void applyPortalCharmEffect (Entity user, ItemStack item) {
        
        if (user instanceof PlayerEntity) {
            
            // Set portal counter above the threshold for instant entry.
            ((user).portalCounter = 100;
        }
    }
    
    public static void applyExperienceCharmTickEffect (Entity user, ItemStack item) {
        
        if (user instanceof PlayerEntity) {
            
            // Completely disable the xp cooldown counter.
            ((PlayerEntity) user).xpCooldown = 0;
        }
    }
    
    public static void handleExpCharmEntity (LivingExperienceDropEvent event) {
        
        if (event.getAttackingPlayer() != null) {
            
            final Item charm = DarkUtils.content.experienceCharm;
            final PlayerEntity player = event.getAttackingPlayer();
            
            if (getStacksFromPlayer(player, charm).size() > 0) {
                
                event.setDroppedExperience(event.getDroppedExperience() + event.getEntity().world.rand.nextInt(5));
            }
        }
    }
    
    public static void handleExpCharmBlock (BlockEvent.BreakEvent event) {
        
        if (event.getExpToDrop() > 0 && event.getPlayer() != null) {
            
            final Item charm = DarkUtils.content.experienceCharm;
            final PlayerEntity player = event.getPlayer();
            
            if (getStacksFromPlayer(player, charm).size() > 0) {
                
                event.setExpToDrop(event.getExpToDrop() + event.getWorld().getRandom().nextInt(5));
            }
        }
    }
    
    public static void handleGluttonCharm (LivingEntityUseItemEvent.Tick event) {
        
        if (event.getEntityLiving() instanceof PlayerEntity) {
            
            final Item charm = DarkUtils.content.gluttonyCharm;
            final PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            
            if (event.getItem().isFood() && getStacksFromPlayer(player, charm).size() > 0) {
                
                event.setDuration(1);
            }
        }
    }

    public static List<ItemStack> getStacksFromPlayer(PlayerEntity player, Item item) {

        List<ItemStack> stacks = new ArrayList<>();

        if (ModList.get().isLoaded("curios")) {

            stacks.addAll(CuriosAddon.getStacksFromPlayer(player, item));
        }

        stacks.addAll(PlayerUtils.getStacksFromPlayer(player, item));

        return stacks;
    }
}
