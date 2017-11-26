package net.darkhax.darkutils.features.monolith;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

@DUFeature(name = "Monolith", description = "A block to redirect ender teleportation")
public class FeatureMonolith extends Feature {

    public static Block blockMonolith;

    @Override
    public void onPreInit () {

        blockMonolith = new BlockMonolith();
        DarkUtils.REGISTRY.registerBlock(blockMonolith, new ItemMonolith(blockMonolith, BlockMonolith.TYPES), "monolith");
        GameRegistry.registerTileEntity(TileEntityMonolithEXP.class, "monolith_exp");
        GameRegistry.registerTileEntity(TileEntityMonolithSpawning.class, "monolith_spawning");
    }
}
