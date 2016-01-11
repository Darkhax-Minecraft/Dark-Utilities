package net.darkhax.darkutils.client.audio;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

public class LoopableTileSound extends MovingSound implements ITickableSound {
    
    private TileEntity tileEntity;
    
    public LoopableTileSound(TileEntity tileEntity, ResourceLocation sound) {
        
        super(sound);
        this.tileEntity = tileEntity;
        this.repeat = true;
        this.volume = 1f;
        this.pitch = 1f;
        
        BlockPos position = tileEntity.getPos();
        this.xPosF = position.getX();
        this.yPosF = position.getY();
        this.zPosF = position.getZ();
        this.repeatDelay = 0;
    }
    
    @Override
    public void update () {
        
        if (this.tileEntity == null || this.tileEntity.isInvalid()) {
            
            this.repeat = false;
            this.donePlaying = true;
            System.out.println("Finished");
        }
    }
}
