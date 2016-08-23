package net.darkhax.darkutils.features.blocks.timer;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.darkhax.bookshelf.tileentity.TileEntityBasicTickable;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTimer extends TileEntityBasicTickable {
    
    private static final int MAXDELAY = 12000;
    private static final int MINDELAY = 10;
    
    private int currentTime = 0;
    private int delayTime = 100;
    
    @Override
    public void writeNBT (NBTTagCompound dataTag) {
        
        dataTag.setInteger("CurrentTime", this.currentTime);
        dataTag.setInteger("TickRate", this.delayTime);
    }
    
    @Override
    public void readNBT (NBTTagCompound dataTag) {
        
        this.currentTime = dataTag.getInteger("CurrentTime");
        this.delayTime = dataTag.getInteger("TickRate");
    }
    
    @Override
    public void onEntityUpdate () {
        
        if (this.currentTime >= this.delayTime) {
            
            this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(this.pos).withProperty(BlockStates.POWERED, true), 1 | 2);
            this.worldObj.scheduleUpdate(this.pos, this.getBlockType(), this.getBlockType().tickRate(this.worldObj));
            this.currentTime = 0;
        }
        
        else
            this.currentTime++;
    }
    
    public int getCurrentTime () {
        
        return this.currentTime;
    }
    
    public void setCurrentTime (int currentTime) {
        
        this.currentTime = currentTime;
    }
    
    public int getDelayTime () {
        
        return this.delayTime;
    }
    
    public void setDelayTime (int tickRate) {
        
        if (tickRate < 10)
            this.delayTime = 10;
            
        else if (tickRate > 12000)
            this.delayTime = 12000;
            
        else
            this.delayTime = tickRate;
    }
    
    public void addTime (int time) {
        
        final int newTime = this.delayTime + time;
        this.setDelayTime(newTime < MAXDELAY ? newTime : MAXDELAY);
    }
    
    public void removeTime (int time) {
        
        final int newTime = this.delayTime - time;
        this.setDelayTime(newTime > MINDELAY ? newTime : MINDELAY);
    }
}