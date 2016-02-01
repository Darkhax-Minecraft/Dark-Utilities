package net.darkhax.darkutils.addons.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.darkutils.blocks.BlockFilter;
import net.darkhax.darkutils.blocks.BlockTimer;
import net.darkhax.darkutils.blocks.BlockTrapMovement;
import net.darkhax.darkutils.blocks.BlockUpdateDetector;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
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
        
        if (data.getBlock() instanceof BlockFilter && !(stack.getMetadata() > BlockFilter.EnumType.getTypes().length))
            tip.add(StatCollector.translateToLocal("tooltip.darkutils.filter.type") + ": " + EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.darkutils.filter.type." + BlockFilter.EnumType.getTypes()[stack.getMetadata()]));
            
        else if (data.getBlock() instanceof BlockTimer && data.getTileEntity() != null && !data.getTileEntity().isInvalid()) {
            
            int delay = data.getNBTData().getInteger("TickRate");
            int currentTime = data.getNBTData().getInteger("CurrentTime");
            
            tip.add(StatCollector.translateToLocal("gui.darkutils.timer.delay") + ": " + delay);
            tip.add(StatCollector.translateToLocal("gui.darkutils.timer.remaining") + ": " + StringUtils.ticksToElapsedTime((delay - currentTime)));
        }
        
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
        register.registerStackProvider(dataProvider, BlockUpdateDetector.class);
        register.registerBodyProvider(dataProvider, BlockFilter.class);
        register.registerBodyProvider(dataProvider, BlockTimer.class);
        register.registerNBTProvider(dataProvider, BlockTimer.class);
    }
}
