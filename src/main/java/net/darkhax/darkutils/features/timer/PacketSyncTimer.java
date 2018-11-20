package net.darkhax.darkutils.features.timer;

import net.darkhax.bookshelf.network.TileEntityMessage;
import net.minecraft.util.math.BlockPos;

public class PacketSyncTimer extends TileEntityMessage<TileEntityTimer> {

    private static final long serialVersionUID = -6538977095087681955L;

    public int delayTime;
    public boolean disabled;

    public PacketSyncTimer () {

    }

    public PacketSyncTimer (BlockPos pos, int delayTime, boolean disabled) {

        super(pos);
        this.delayTime = delayTime;
        this.disabled = disabled;
    }
    

    @Override
    public void getAction () {

        this.tile.setDelayTime(this.delayTime);
        this.tile.setDisabled(this.disabled);
    }
}