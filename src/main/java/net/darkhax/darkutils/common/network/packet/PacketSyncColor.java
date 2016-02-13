package net.darkhax.darkutils.common.network.packet;

import io.netty.buffer.ByteBuf;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.darkutils.addons.thaumcraft.ItemFociRecall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSyncColor implements IMessage {
    
    public BlockPos pos;
    public int dimension;
    public int color;
    public String itemName;
    
    public PacketSyncColor() {
    
    }
    
    public PacketSyncColor(BlockPos pos, int dimension, int color, String name) {
        
        this.pos = pos;
        this.dimension = dimension;
        this.color = color;
        this.itemName = name;
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.dimension = buf.readInt();
        this.color = buf.readInt();
        this.itemName = ByteBufUtils.readUTF8String(buf);
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeInt(this.dimension);
        buf.writeInt(this.color);
        ByteBufUtils.writeUTF8String(buf, this.itemName);
    }
    
    public static class PacketHandler implements IMessageHandler<PacketSyncColor, IMessage> {
        
        @Override
        public IMessage onMessage (PacketSyncColor packet, MessageContext ctx) {
            
            EntityPlayer player = (ctx.side == Side.CLIENT) ? PlayerUtils.getClientPlayer() : ctx.getServerHandler().playerEntity;
            ItemStack stack = player.getHeldItem();
            
            if (ItemStackUtils.isValidStack(stack) && stack.getItem() instanceof ItemFociRecall) {
                
                ItemFociRecall.setFocusPosition(stack, packet.pos, packet.dimension);
                stack.getTagCompound().setInteger("colorData", packet.color);
                stack.setStackDisplayName(packet.itemName);
            }
            
            return null;
        }
    }
}