package net.darkhax.darkutils.features.monolith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@DUFeature(name = "Monolith", description = "Blocks with chunk based AOE effects.")
public class FeatureMonolith extends Feature {

    public static final Map<WorldServer, List<TileEntityMonolith>> LOADED_MONOLITHS = new HashMap<>();

    public static Block blockMonolith;

    @Override
    public void onPreInit () {

        blockMonolith = new BlockMonolith();
        DarkUtils.REGISTRY.registerBlock(blockMonolith, new ItemBlockBasic(blockMonolith, BlockMonolith.TYPES), "monolith");
        GameRegistry.registerTileEntity(TileEntityMonolithEXP.class, "monolith_exp");
        GameRegistry.registerTileEntity(TileEntityMonolithSpawning.class, "monolith_spawning");
    }

    @Override
    public void onPreRecipe () {
        
        DarkUtils.REGISTRY.addShapedRecipe("monolith_exp", new ItemStack(blockMonolith, 1, 0), "ppp", "pip", "bbb", 'p', "gemPearl", 'b', "blockPearl", 'i', Items.EXPERIENCE_BOTTLE);
        DarkUtils.REGISTRY.addShapedRecipe("monolith_spawn", new ItemStack(blockMonolith, 1, 0), "ppp", "pip", "bbb", 'p', "gemPearl", 'b', "blockPearl", 'i', Items.NETHER_STAR);
    }
    public static void trackMonolith (TileEntityMonolith tile) {

        if (tile.getWorld() instanceof WorldServer) {

            getMonoliths((WorldServer) tile.getWorld()).add(tile);
        }
    }

    public static void stopTrackingMonolith (TileEntityMonolith tile) {

        if (tile.getWorld() instanceof WorldServer) {

            getMonoliths((WorldServer) tile.getWorld()).remove(tile);
        }
    }

    public static List<TileEntityMonolith> getMonoliths (WorldServer world) {

        return LOADED_MONOLITHS.computeIfAbsent(world, key -> {
            return new ArrayList<>();
        });
    }

    public static boolean isTracked (TileEntityMonolith tile) {

        if (tile.getWorld() instanceof WorldServer) {

            return getMonoliths((WorldServer) tile.getWorld()).contains(tile);
        }

        return false;
    }

    @SubscribeEvent
    public static void onWorldUnload (WorldEvent.Unload event) {

        if (event.getWorld() instanceof WorldServer) {

            LOADED_MONOLITHS.remove(event.getWorld());
        }
    }

    @SubscribeEvent
    public void onMobSpawnCheck (CheckSpawn event) {

        if (event.getWorld() instanceof WorldServer) {

            for (final TileEntityMonolith monolith : getMonoliths((WorldServer) event.getWorld())) {

                if (monolith.isInSameChunk(new BlockPos(event.getX(), event.getY(), event.getZ()))) {

                    monolith.onSpawnCheck(event);
                    break;
                }
            }
        }
    }

    @Override
    public boolean usesEvents () {

        return true;
    }
}