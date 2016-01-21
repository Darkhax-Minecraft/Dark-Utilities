package net.darkhax.darkutils.tileentity;

import net.darkhax.bookshelf.lib.util.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnderTether extends TileEntity {
    
    public boolean isEntityCloseEnough (EntityLivingBase entity) {
        
        return EntityUtils.getDistaceFromPos(entity, this.getPos()) <= 32d;
    }
}