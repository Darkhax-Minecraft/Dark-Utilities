package net.darkhax.darkutils.handler;

import net.darkhax.bookshelf.event.LootingEvent;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.MathsUtils;
import net.darkhax.darkutils.addons.baubles.DarkUtilsBaublesAddon;
import net.darkhax.darkutils.items.ItemSourcedSword;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
    
    @SubscribeEvent
    public void onLooting (LootingEvent event) {
        
        if (event.entityLiving instanceof EntityPlayer) {
            
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            
            if (player.inventory.hasItem(ContentHandler.itemFortuneRing) || (Loader.isModLoaded("Baubles") && DarkUtilsBaublesAddon.isPlayerWearingRing(player, ContentHandler.itemFortuneRing))) {
                
                event.lootingModifier++;
                event.setResult(Result.ALLOW);
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingHurt (LivingHurtEvent event) {
        
        if (event.source.getEntity() instanceof EntityLivingBase) {
            
            EntityLivingBase attacker = (EntityLivingBase) event.source.getEntity();
            
            if (ItemStackUtils.isValidStack(attacker.getHeldItem()) && attacker.getHeldItem().getItem() instanceof ItemSourcedSword) {
                
                ItemSourcedSword sword = (ItemSourcedSword) attacker.getHeldItem().getItem();
                event.entityLiving.attackEntityFrom(MathsUtils.tryPercentage(sword.effectChance) ? sword.source : DamageSource.generic, sword.attackDamage);
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onEnderTeleport (EnderTeleportEvent event) {
        
        if (event.entityLiving instanceof EntityLivingBase && event.entityLiving.getEntityWorld() != null) {
            
            for (TileEntity tile : event.entityLiving.getEntityWorld().loadedTileEntityList) {
                
                if (tile instanceof TileEntityEnderTether && ((TileEntityEnderTether) tile).isEntityCloseEnough(event.entityLiving)) {
                    
                    BlockPos pos = tile.getPos();
                    event.targetX = pos.getX();
                    event.targetY = pos.getY();
                    event.targetZ = pos.getZ();
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onEntitySpawn (EntityJoinWorldEvent event) {
        
        if (event.entity instanceof EntityLivingBase && event.entity.getEntityWorld() != null) {
            
            for (TileEntity tile : event.entity.getEntityWorld().loadedTileEntityList) {
                
                if (tile instanceof TileEntityAntiSlime && event.entity instanceof EntitySlime && !event.entity.hasCustomName() && ((TileEntityAntiSlime) tile).shareChunks((EntityLivingBase) event.entity)) {
                    
                    event.entity.setDead();
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }
}