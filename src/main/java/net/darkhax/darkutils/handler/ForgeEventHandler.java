package net.darkhax.darkutils.handler;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.MathsUtils;
import net.darkhax.darkutils.items.ItemSourcedSword;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
    
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
    public void onLivingDrops (PotionBrewEvent.Pre event) {
        
        for (int pos = 0; pos < event.getLength(); pos++) {
            
            ItemStack stack = event.getItem(pos);
            System.out.println("Slot: " + pos + " " + (ItemStackUtils.isValidStack(stack) ? stack.getDisplayName() : "null"));
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
}