package net.darkhax.darkutils.addons.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.darkutils.blocks.BlockEnderTether;
import net.darkhax.darkutils.blocks.BlockTrapMovement;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class DarkUtilsTileProvider implements IWailaDataProvider {
    
    @Override
    public ItemStack getWailaStack (IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        return new ItemStack(data.getStack().getItem(), 1, 0);
    }
    
    @Override
    public List<String> getWailaHead (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        
        if (te != null && !te.isInvalid())
            te.writeToNBT(tag);
            
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
        
        DarkUtilsTileProvider dataProvider = new DarkUtilsTileProvider();
        register.registerStackProvider(dataProvider, BlockTrapMovement.class);
    }
}
