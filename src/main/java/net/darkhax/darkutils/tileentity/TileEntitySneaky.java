package net.darkhax.darkutils.tileentity;

import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntitySneaky extends TileEntityBasic {
    
    /**
     * A BlockState held by the TileEntity. The block will try it's best to camo into the held
     * state.
     */
    public IBlockState heldState;
    
    @Override
    public void readNBT (NBTTagCompound tag) {
        
        Block heldBlock = Block.getBlockFromName(tag.getString("HeldBlockId"));
        
        if (heldBlock != null)
            heldState = heldBlock.getStateFromMeta(tag.getInteger("HeldBlockMeta"));
    }
    
    @Override
    public void writeNBT (NBTTagCompound tag) {
        
        if (heldState != null) {
            
            tag.setString("HeldBlockId", Block.blockRegistry.getNameForObject(heldState.getBlock()).toString());
            tag.setInteger("HeldBlockMeta", heldState.getBlock().getMetaFromState(heldState));
        }
    }
    
    @Override
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet) {
        
        super.onDataPacket(net, packet);
        readNBT(packet.getNbtCompound());
        worldObj.markBlockRangeForRenderUpdate(pos, pos);
    }
}
