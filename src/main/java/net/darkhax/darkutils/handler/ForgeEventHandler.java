package net.darkhax.darkutils.handler;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.MathsUtils;
import net.darkhax.darkutils.items.ItemSourcedSword;
import net.darkhax.darkutils.libs.Constants;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
    
    @SubscribeEvent
    public void onLootTableLoad (LootTableLoadEvent event) {
        
        if (event.getName().equals(LootTableList.ENTITIES_WITHER_SKELETON)) {
            
            final LootPool pool1 = event.getTable().getPool("pool1");
            
            if (pool1 != null)
                pool1.addEntry(new LootEntryItem(ContentHandler.itemMaterial, 1, 0, new LootFunction[0], new LootCondition[0], Constants.MOD_ID + ":wither_dust"));
        }
    }
    
    @SubscribeEvent
    public void onLivingHurt (LivingHurtEvent event) {
        
        if (event.getSource().getEntity() instanceof EntityLivingBase) {
            
            final EntityLivingBase attacker = (EntityLivingBase) event.getSource().getEntity();
            
            if (ItemStackUtils.isValidStack(attacker.getHeldItemMainhand()) && attacker.getHeldItemMainhand().getItem() instanceof ItemSourcedSword) {
                
                final ItemSourcedSword sword = (ItemSourcedSword) attacker.getHeldItemMainhand().getItem();
                event.getEntity().attackEntityFrom(MathsUtils.tryPercentage(sword.effectChance) ? sword.source : DamageSource.generic, sword.attackDamage);
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onEnderTeleport (EnderTeleportEvent event) {
        
        if (event.getEntityLiving() instanceof EntityLivingBase && event.getEntityLiving().getEntityWorld() != null)
            for (final TileEntity tile : event.getEntityLiving().getEntityWorld().loadedTileEntityList)
                if (tile instanceof TileEntityEnderTether && ((TileEntityEnderTether) tile).isEntityCloseEnough(event.getEntityLiving())) {
                    
                    final BlockPos pos = tile.getPos();
                    event.setTargetX(pos.getX());
                    event.setTargetY(pos.getY());
                    event.setTargetZ(pos.getZ());
                    break;
                }
    }
    
    @SubscribeEvent
    public void onEntitySpawn (EntityJoinWorldEvent event) {
        
        if (event.getEntity() instanceof EntitySlime && event.getEntity().getEntityWorld() != null) {
            
            final java.util.Iterator<TileEntity> it = event.getEntity().getEntityWorld().loadedTileEntityList.iterator();
            
            while (it.hasNext()) {
                
                final TileEntity tile = it.next();
                if (tile instanceof TileEntityAntiSlime && !event.getEntity().hasCustomName() && ((TileEntityAntiSlime) tile).shareChunks((EntityLivingBase) event.getEntity())) {
                    
                    event.getEntity().setDead();
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }
}