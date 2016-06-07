package net.darkhax.darkutils.tileentity;

import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityShulkerPearl extends TileEntity {
    
    private EntityShulkerBullet pearl;
    
    public TileEntityShulkerPearl(World world) {
        
        this.pearl = new EntityShulkerBullet(world);
    }
    
    public EntityShulkerBullet getPearl () {
        
        if (this.pearl == null)
            this.pearl = new EntityShulkerBullet(this.worldObj);
            
        return this.pearl;
    }
}
