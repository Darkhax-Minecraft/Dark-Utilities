package net.darkhax.darkutils.features.timer;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@DUFeature(name = "Redstone Timer", description = "A block for timing redstone")
public class FeatureTimer extends Feature {

    public static Block blockTimer;

    @Override
    public void onPreInit () {

        DarkUtils.NETWORK.register(PacketSyncTimer.class, Side.SERVER);
        blockTimer = new BlockTimer();
        DarkUtils.REGISTRY.registerBlock(blockTimer, "timer");
        GameRegistry.registerTileEntity(TileEntityTimer.class, "timer");
    }
}
