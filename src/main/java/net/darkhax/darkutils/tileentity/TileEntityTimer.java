package net.darkhax.darkutils.tileentity;

import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.darkhax.darkutils.blocks.BlockTimer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTimer extends TileEntityBasic {
    
    private static final int MAXDELAY = 12000;
    private static final int MINDELAY = 10;
    
    private int currentTime = 0;
    private int delayTime = 100;
    
    @Override
    public void writeNBT (NBTTagCompound dataTag) {
        
        dataTag.setInteger("CurrentTime", currentTime);
        dataTag.setInteger("TickRate", delayTime);
    }
    
    @Override
    public void readNBT (NBTTagCompound dataTag) {
        
        currentTime = dataTag.getInteger("CurrentTime");
        delayTime = dataTag.getInteger("TickRate");
    }
    
    @Override
    public void onEntityUpdate () {
        
        if (currentTime >= delayTime) {
            
            worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockTimer.POWERED, true), 1 | 2);
            worldObj.scheduleUpdate(pos, getBlockType(), getBlockType().tickRate(worldObj));
            currentTime = 0;
        }
        
        else
            currentTime++;
    }
    
    public int getCurrentTime () {
        
        return currentTime;
    }
    
    public void setCurrentTime (int currentTime) {
        
        this.currentTime = currentTime;
    }
    
    public int getDelayTime () {
        
        return delayTime;
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
        
        final int newTime = delayTime + time;
        setDelayTime((newTime < MAXDELAY) ? newTime : MAXDELAY);
    }
    
    public void removeTime (int time) {
        
        final int newTime = delayTime - time;
        setDelayTime((newTime > MINDELAY) ? newTime : MINDELAY);
    }
}