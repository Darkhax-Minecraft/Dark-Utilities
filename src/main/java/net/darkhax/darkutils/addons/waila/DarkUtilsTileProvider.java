package net.darkhax.darkutils.addons.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.features.faketnt.BlockFakeTNT;
import net.darkhax.darkutils.features.feeder.BlockFeeder;
import net.darkhax.darkutils.features.feeder.TileEntityFeeder;
import net.darkhax.darkutils.features.filter.BlockFilter;
import net.darkhax.darkutils.features.filter.FilterType;
import net.darkhax.darkutils.features.sneaky.BlockSneaky;
import net.darkhax.darkutils.features.sneaky.TileEntitySneaky;
import net.darkhax.darkutils.features.timer.BlockTimer;
import net.darkhax.darkutils.features.timer.TileEntityTimer;
import net.darkhax.darkutils.features.updatedetector.BlockUpdateDetector;
import net.darkhax.darkutils.features.vector.BlockVectorPlate;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class DarkUtilsTileProvider implements IWailaDataProvider {

    private static final String CONFIG_FILTER_TYPE = "darkutils.filter.type";

    private static final String CONFIG_TIMER_TIME = "darkutils.timer.time";

    private static final String CONFIG_SNEAKY_OWNERS = "darkutils.sneaky.owner";

    private static final String CONFIG_FEEDER_INFO = "darkutils.feeder.info";

    @Override
    public ItemStack getWailaStack (IWailaDataAccessor data, IWailaConfigHandler cfg) {

        final Block block = Block.getBlockFromItem(data.getStack().getItem());

        if (block instanceof BlockSneaky && !data.getTileEntity().isInvalid()) {

            final TileEntitySneaky tile = (TileEntitySneaky) data.getTileEntity();

            if (cfg.getConfig(CONFIG_SNEAKY_OWNERS) && tile.playerID != null && !tile.playerID.isEmpty() && data.getPlayer().getUniqueID().toString().equals(tile.playerID))
                return data.getStack();

            if (tile.heldState != null)
                return new ItemStack(tile.heldState.getBlock(), 1, tile.heldState.getBlock().getMetaFromState(tile.heldState));
        }

        else if (data.getBlock() instanceof BlockFakeTNT)
            return new ItemStack(Blocks.TNT, 1, 0);

        return new ItemStack(data.getStack().getItem(), 1, 0);
    }

    @Override
    public List<String> getWailaHead (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public List<String> getWailaBody (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        if (data.getBlock() instanceof BlockFilter && cfg.getConfig(CONFIG_FILTER_TYPE) && !(stack.getMetadata() > FilterType.getTypes().length))
            tip.add(I18n.format("tooltip.darkutils.filter.type") + ": " + TextFormatting.AQUA + I18n.format("tooltip.darkutils.filter.type." + FilterType.getTypes()[stack.getMetadata()]));
        else if (data.getBlock() instanceof BlockTimer && cfg.getConfig(CONFIG_TIMER_TIME) && data.getTileEntity() instanceof TileEntityTimer && !data.getTileEntity().isInvalid()) {

            final int delay = data.getNBTData().getInteger("TickRate");
            final int currentTime = data.getNBTData().getInteger("CurrentTime");

            tip.add(I18n.format("gui.darkutils.timer.delay") + ": " + delay);
            tip.add(I18n.format("gui.darkutils.timer.remaining") + ": " + StringUtils.ticksToElapsedTime(delay - currentTime));
        }

        else if (data.getBlock() instanceof BlockFeeder && cfg.getConfig(CONFIG_FEEDER_INFO) && data.getTileEntity() instanceof TileEntityFeeder && !data.getTileEntity().isInvalid()) {

            final String foodType = data.getNBTData().getString("FoodType");

            if (foodType != null && !foodType.equalsIgnoreCase("null")) {

                final ItemStack foodStack = ItemStackUtils.createStackFromString(foodType);

                if (ItemStackUtils.isValidStack(foodStack))
                    tip.add(I18n.format("tooltip.darkutils.feeder.type") + ": " + TextFormatting.BLUE + foodStack.getDisplayName());
            }

            tip.add(I18n.format("tooltip.darkutils.feeder.amount") + ": " + data.getMetadata());
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

        final DarkUtilsTileProvider dataProvider = new DarkUtilsTileProvider();

        register.registerStackProvider(dataProvider, BlockVectorPlate.class);
        register.registerStackProvider(dataProvider, BlockUpdateDetector.class);
        register.registerStackProvider(dataProvider, BlockSneaky.class);
        register.registerStackProvider(dataProvider, BlockFakeTNT.class);

        register.registerBodyProvider(dataProvider, BlockFilter.class);
        register.registerBodyProvider(dataProvider, BlockTimer.class);
        register.registerBodyProvider(dataProvider, BlockFeeder.class);

        register.registerNBTProvider(dataProvider, BlockTimer.class);
        register.registerNBTProvider(dataProvider, BlockSneaky.class);
        register.registerNBTProvider(dataProvider, BlockFeeder.class);

        register.addConfig("DarkUtils", CONFIG_FILTER_TYPE);
        register.addConfig("DarkUtils", CONFIG_TIMER_TIME);
        register.addConfig("DarkUtils", CONFIG_SNEAKY_OWNERS);
        register.addConfig("DarkUtils", CONFIG_FEEDER_INFO);
    }
}