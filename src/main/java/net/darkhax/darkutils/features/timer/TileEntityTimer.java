package net.darkhax.darkutils.features.timer;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.darkhax.bookshelf.data.Blockstates;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTimer extends TileEntityBasicTickable {

    private static final int MAXDELAY = 12000;

    private static final int MINDELAY = 10;

    private int currentTime = 0;

    private int delayTime = 100;
	
	private boolean disabled = false;
	
    @Override
    public void writeNBT (NBTTagCompound dataTag) {

        dataTag.setInteger("CurrentTime", this.currentTime);
        dataTag.setInteger("TickRate", this.delayTime);
        dataTag.setBoolean("Disabled", this.disabled);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        this.currentTime = dataTag.getInteger("CurrentTime");
        this.delayTime = dataTag.getInteger("TickRate");
		this.disabled = dataTag.getBoolean("Disabled");
    }

    @Override
    public void onEntityUpdate () {

        if (this.world.isBlockPowered(this.pos) || this.disabled) {
            return;
        }

        if (this.currentTime >= this.delayTime) {

            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(Blockstates.POWERED, true), 1 | 2);
            this.world.scheduleUpdate(this.pos, this.getBlockType(), this.getBlockType().tickRate(this.world));
            this.currentTime = 0;
        }
        else {
            this.currentTime++;
        }
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
	
	public boolean isDisabled () {
		
		return this.disabled;
	}
	
	public void setDisabled (boolean disable) {
		
	    this.disabled = disable;
	}

    public void setDelayTime (int tickRate) {

        if (tickRate < 10) {
            this.delayTime = 10;
        }
        else if (tickRate > 12000) {
            this.delayTime = 12000;
        }
        else {
            this.delayTime = tickRate;
        }
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
