package net.darkhax.darkutils.handler;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.MathsUtils;
import net.darkhax.darkutils.items.ItemSourcedSword;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
    
    @SubscribeEvent
    public void onLivingDrops (LivingDropsEvent event) {
        
        Entity entity = event.getEntity();
        
        if (entity instanceof EntitySkeleton && ((EntitySkeleton) entity).getSkeletonType() == 1 && MathsUtils.tryPercentage(0.05 + (0.05d * event.getLootingLevel())))
            event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(ContentHandler.itemMaterial, MathsUtils.nextIntInclusive(1, 3), 0)));
    }
    
    @SubscribeEvent
    public void onLivingHurt (LivingHurtEvent event) {
        
        if (event.getSource().getEntity() instanceof EntityLivingBase) {
            
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getEntity();
            
            if (ItemStackUtils.isValidStack(attacker.getHeldItemMainhand()) && attacker.getHeldItemMainhand().getItem() instanceof ItemSourcedSword) {
                
                ItemSourcedSword sword = (ItemSourcedSword) attacker.getHeldItemMainhand().getItem();
                event.getEntity().attackEntityFrom(MathsUtils.tryPercentage(sword.effectChance) ? sword.source : DamageSource.generic, sword.attackDamage);
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onEnderTeleport (EnderTeleportEvent event) {
        
        if (event.getEntityLiving() instanceof EntityLivingBase && event.getEntityLiving().getEntityWorld() != null) {
            
            for (TileEntity tile : event.getEntityLiving().getEntityWorld().loadedTileEntityList) {
                
                if (tile instanceof TileEntityEnderTether && ((TileEntityEnderTether) tile).isEntityCloseEnough(event.getEntityLiving())) {
                    
                    BlockPos pos = tile.getPos();
                    event.setTargetX(pos.getX());
                    event.setTargetY(pos.getY());
                    event.setTargetZ(pos.getZ());
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onEntitySpawn (EntityJoinWorldEvent event) {
        
        if (event.getEntity() instanceof EntitySlime && event.getEntity().getEntityWorld() != null) {
            
            java.util.Iterator<TileEntity> it = event.getEntity().getEntityWorld().loadedTileEntityList.iterator();
            
            while (it.hasNext()) {
                
                TileEntity tile = it.next();
                if (tile instanceof TileEntityAntiSlime && !event.getEntity().hasCustomName() && ((TileEntityAntiSlime) tile).shareChunks((EntityLivingBase) event.getEntity())) {
                    
                    event.getEntity().setDead();
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }
}