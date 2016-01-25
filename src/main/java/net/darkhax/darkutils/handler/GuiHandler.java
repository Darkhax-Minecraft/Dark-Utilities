package net.darkhax.darkutils.handler;

import net.darkhax.darkutils.client.gui.GuiTimerAmount;
import net.darkhax.darkutils.tileentity.TileEntityTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    
    public static final int TIMER = 0;
    
    @Override
    public Object getServerGuiElement (int id, EntityPlayer player, World world, int x, int y, int z) {
        
        return null;
    }
    
    @Override
    public Object getClientGuiElement (int id, EntityPlayer player, World world, int x, int y, int z) {
        
        if (id == TIMER) {
            
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            
            if (tile instanceof TileEntityTimer)
                return new GuiTimerAmount((TileEntityTimer) tile);
        }
        
        return null;
    }
}
