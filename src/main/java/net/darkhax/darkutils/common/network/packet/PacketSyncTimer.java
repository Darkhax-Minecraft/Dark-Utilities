package net.darkhax.darkutils.common.network.packet;

import io.netty.buffer.ByteBuf;
import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.darkutils.tileentity.TileEntityTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSyncTimer implements IMessage {
    
    public BlockPos pos;
    public int delayTime;
    
    public PacketSyncTimer() {
    
    }
    
    public PacketSyncTimer(BlockPos pos, int delayTime) {
        
        this.pos = pos;
        this.delayTime = delayTime;
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.delayTime = buf.readInt();
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeInt(this.delayTime);
    }
    
    public static class PacketHandler implements IMessageHandler<PacketSyncTimer, IMessage> {
        
        @Override
        public IMessage onMessage (PacketSyncTimer packet, MessageContext ctx) {
            
            final EntityPlayer player = ctx.side == Side.CLIENT ? PlayerUtils.getClientPlayer() : ctx.getServerHandler().playerEntity;
            final TileEntityTimer tile = (TileEntityTimer) player.worldObj.getTileEntity(packet.pos);
            
            if (!tile.isInvalid())
                tile.setDelayTime(packet.delayTime);
                
            return null;
        }
    }
}