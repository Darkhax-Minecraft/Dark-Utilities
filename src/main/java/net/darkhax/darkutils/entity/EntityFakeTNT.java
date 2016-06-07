package net.darkhax.darkutils.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityFakeTNT extends EntityTNTPrimed {
    
    public EntityFakeTNT(World worldIn) {
        
        super(worldIn);
    }
    
    public EntityFakeTNT(World worldIn, double x, double y, double z, EntityLivingBase igniter) {
        
        super(worldIn, x, y, z, igniter);
    }
    
    @Override
    public void onUpdate () {
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        
        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }
        
        this.setFuse(this.getFuse() - 1);
        
        if (this.getFuse() <= 0) {
            this.setDead();
            
            if (!this.worldObj.isRemote)
                this.worldObj.createExplosion(this, this.posX, this.posY + this.height / 16.0F, this.posZ, 0.0f, true);
        }
        else {
            this.handleWaterMovement();
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
}