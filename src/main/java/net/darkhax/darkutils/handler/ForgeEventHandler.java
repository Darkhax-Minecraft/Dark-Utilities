package net.darkhax.darkutils.handler;

import java.util.UUID;

import net.darkhax.bookshelf.lib.ModifierOperation;
import net.darkhax.darkutils.libs.Constants;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetDamage;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
    
    public static final AttributeModifier SHEEP_ARMOR = new AttributeModifier(UUID.fromString("6e915cea-3f18-485d-a818-373fe4f75f7f"), "sheep_armor", 1.0d, ModifierOperation.ADDITIVE.ordinal());
    
    @SubscribeEvent
    public void onEntityUpdate (LivingUpdateEvent event) {
        
        final EntityLivingBase entity = event.getEntityLiving();
        
        if (entity instanceof EntitySheep) {
            
            final ModifiableAttributeInstance instance = (ModifiableAttributeInstance) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR);
            final boolean hasModifier = instance.hasModifier(SHEEP_ARMOR);
            final boolean isSheared = ((EntitySheep) entity).getSheared();
            
            if (!isSheared && !hasModifier)
                instance.applyModifier(SHEEP_ARMOR);
                
            else if (isSheared && hasModifier)
                instance.removeModifier(SHEEP_ARMOR);
        }
    }
    
    @SubscribeEvent
    public void onLootTableLoad (LootTableLoadEvent event) {
        
        final LootTable table = event.getTable();
        
        if (event.getName().equals(LootTableList.ENTITIES_WITHER_SKELETON)) {
            
            final LootPool pool1 = table.getPool("pool1");
            
            if (pool1 != null)
                pool1.addEntry(new LootEntryItem(ContentHandler.itemMaterial, 1, 0, new LootFunction[0], new LootCondition[0], Constants.MOD_ID + ":wither_dust"));
        }
        
        else if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
            
            final LootPool main = table.getPool("main");
            
            if (main != null)
                main.addEntry(new LootEntryItem(ContentHandler.itemPotion, 5, 0, new LootFunction[] { new SetDamage(new LootCondition[0], new RandomValueRange(0, 1)) }, new LootCondition[0], Constants.MOD_ID + ":mysterious_potion"));
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