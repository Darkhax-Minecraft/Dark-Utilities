package net.darkhax.darkutils.features.timer;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("timer", new ItemStack(blockTimer), "sts", "tct", "sts", 's', OreDictUtils.STONE, 't', Blocks.REDSTONE_TORCH, 'c', Items.CLOCK);
    }
}
