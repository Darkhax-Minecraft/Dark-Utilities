package net.darkhax.darkutils.features.monolith;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@DUFeature(name = "Monolith", description = "Blocks with chunk based AOE effects.")
public class FeatureMonolith extends Feature {

    public static Block blockMonolith;

    @Override
    public void onPreInit () {

        blockMonolith = new BlockMonolith();
        DarkUtils.REGISTRY.registerBlock(blockMonolith, new ItemMonolith(blockMonolith, BlockMonolith.TYPES), "monolith");
        GameRegistry.registerTileEntity(TileEntityMonolithEXP.class, "monolith_exp");
        GameRegistry.registerTileEntity(TileEntityMonolithSpawning.class, "monolith_spawning");
    }

    @SubscribeEvent
    public void onMobSpawnCheck (CheckSpawn event) {

        for (final TileEntityMonolith monolith : TileEntityMonolith.LOADED_MONOLITHS) {

            if (monolith.isInSameChunk(new BlockPos(event.getX(), event.getY(), event.getZ()))) {

                monolith.onSpawnCheck(event);
                break;
            }
        }
    }

    @Override
    public boolean usesEvents () {

        return true;
    }
}