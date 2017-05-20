package net.darkhax.darkutils.features.timer;

import net.darkhax.darkutils.network.TileEntityMessage;
import net.minecraft.util.math.BlockPos;

public class PacketSyncTimer extends TileEntityMessage<TileEntityTimer> {
    
    private static final long serialVersionUID = -6538977095087681955L;
    
    public int delayTime;
    
    public PacketSyncTimer () {
        
    }
    
    public PacketSyncTimer (BlockPos pos, int delayTime) {
        
        super(pos);
        this.delayTime = delayTime;
    }
    
    @Override
    public void getAction () {
        
        this.tile.setDelayTime(this.delayTime);
    }
}