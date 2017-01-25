package net.darkhax.darkutils.handler;

import net.darkhax.bookshelf.inventory.InventoryItem;
import net.darkhax.darkutils.client.gui.ContainerFilter;
import net.darkhax.darkutils.client.gui.GuiFilter;
import net.darkhax.darkutils.features.timer.GuiTimerAmount;
import net.darkhax.darkutils.features.timer.TileEntityTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int TIMER = 0;

    public static final int FILTER = 1;

    @Override
    public Object getServerGuiElement (int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == FILTER)
            return new ContainerFilter(player.inventory, new InventoryItem(player.getHeldItemMainhand(), 5, "container.darkutils.charm.null"));

        return null;
    }

    @Override
    public Object getClientGuiElement (int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == TIMER) {

            final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

            if (tile instanceof TileEntityTimer)
                return new GuiTimerAmount((TileEntityTimer) tile);
        }

        else if (id == FILTER)
            return new GuiFilter(player.inventory, new InventoryItem(player.getHeldItemMainhand(), 5, "container.darkutils.charm.null"));

        return null;
    }
}