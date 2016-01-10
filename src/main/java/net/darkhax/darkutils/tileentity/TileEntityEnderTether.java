package net.darkhax.darkutils.tileentity;

import net.darkhax.darkutils.libs.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnderTether extends TileEntity {
    
    public boolean isEntityCloseEnough (EntityLivingBase entity) {
        
        return Utilities.getDistaceFromPos(entity, this.getPos()) <= 32d;
    }
}