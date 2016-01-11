package net.darkhax.darkutils.tileentity;

import net.darkhax.darkutils.client.audio.LoopableTileSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class TileEntityDog extends TileEntity implements ITickable {
    
    boolean hasStarted = false;
    
    @Override
    public void update () {
        
        if (this.hasWorldObj() && this.worldObj.isRemote && !this.hasStarted) {
            
            ISound dogSong = new LoopableTileSound(this, new ResourceLocation("darkutils:dogsong"));
            Minecraft.getMinecraft().getSoundHandler().playSound(dogSong);
            hasStarted = true;
        }
    }
}
