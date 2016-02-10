package net.darkhax.darkutils.tileentity;

import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySneaky extends TileEntityBasic {
    
    public IBlockState heldState;
    
    public void readNBT (NBTTagCompound tag) {
        
        Block heldBlock = Block.getBlockFromName(tag.getString("HeldBlockId"));
        
        if (heldBlock != null)
            heldState = heldBlock.getStateFromMeta(tag.getInteger("HeldBlockMeta"));
    }
    
    public void writeNBT (NBTTagCompound tag) {
        
        if (heldState != null) {
            
            tag.setString("HeldBlockId", Block.blockRegistry.getNameForObject(heldState.getBlock()).toString());
            tag.setInteger("HeldBlockMeta", heldState.getBlock().getMetaFromState(heldState));
        }
    }
}
