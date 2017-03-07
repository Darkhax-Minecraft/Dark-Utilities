package net.darkhax.darkutils.features.sneaky;

import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class TileEntitySneaky extends TileEntityBasic {

    /**
     * A BlockState held by the TileEntity. The block will try it's best to camo into the held
     * state.
     */
    public IBlockState heldState;

    /**
     * A String which holds the UUID of the player that placed the block.
     */
    public String playerID;

    @Override
    public void readNBT (NBTTagCompound tag) {

        final Block heldBlock = Block.getBlockFromName(tag.getString("HeldBlockId"));

        if (heldBlock != null) {
            this.heldState = heldBlock.getStateFromMeta(tag.getInteger("HeldBlockMeta"));
        }

        if (tag.hasKey("PlayerID")) {
            this.playerID = tag.getString("PlayerID");
        }
    }

    @Override
    public void writeNBT (NBTTagCompound tag) {

        if (this.heldState != null && this.heldState.getBlock() != null && this.heldState.getBlock().getRegistryName() != null) {

            tag.setString("HeldBlockId", this.heldState.getBlock().getRegistryName().toString());
            tag.setInteger("HeldBlockMeta", this.heldState.getBlock().getMetaFromState(this.heldState));
        }

        if (this.playerID != null && !this.playerID.isEmpty()) {
            tag.setString("PlayerID", this.playerID);
        }
    }

    @Override
    public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity packet) {

        super.onDataPacket(net, packet);
        this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
    }
}
