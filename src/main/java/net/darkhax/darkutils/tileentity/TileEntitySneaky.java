package net.darkhax.darkutils.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySneaky extends TileEntity {
    
    public IBlockState heldState;
    
    private void readNBT (NBTTagCompound tag) {
        
        Block heldBlock = Block.getBlockFromName(tag.getString("HeldBlockId"));
        
        if (heldBlock != null)
            heldState = heldBlock.getStateFromMeta(tag.getInteger("HeldBlockMetaw"));
    }
    
    private void writeNBT (NBTTagCompound tag) {
        
        if (heldState != null) {
            
            tag.setString("HeldBlockId", Block.blockRegistry.getNameForObject(heldState.getBlock()).toString());
            tag.setInteger("HeldBlockMeta", heldState.getBlock().getMetaFromState(heldState));
        }
    }
    
    @Override
    public void readFromNBT (NBTTagCompound dataTag) {
        
        super.readFromNBT(dataTag);
        readNBT(dataTag);
    }
    
    @Override
    public void writeToNBT (NBTTagCompound dataTag) {
        
        super.writeToNBT(dataTag);
        writeNBT(dataTag);
    }
    
    @Override
    public Packet getDescriptionPacket () {
        
        NBTTagCompound dataTag = new NBTTagCompound();
        writeNBT(dataTag);
        return new S35PacketUpdateTileEntity(pos, -1337, dataTag);
    }
    
    @Override
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet) {
        
        super.onDataPacket(net, packet);
        worldObj.markBlockRangeForRenderUpdate(pos, pos);
        readNBT(packet.getNbtCompound());
    }
}
