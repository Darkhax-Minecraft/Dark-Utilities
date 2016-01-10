package net.darkhax.darkutils.handler;

import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
    
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