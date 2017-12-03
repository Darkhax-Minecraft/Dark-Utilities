package net.darkhax.darkutils.features.endertether;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@DUFeature(name = "Ender Tether", description = "A block to redirect ender teleportation")
public class FeatureEnderTether extends Feature {

    public static final Map<WorldServer, List<TileEntityEnderTether>> LOADED_TETHERS = new HashMap<>();

    public static Block blockEnderTether;

    protected static boolean affectPlayers = true;

    public static double tetherRange = 32D;

    @Override
    public void onPreInit () {

        blockEnderTether = DarkUtils.REGISTRY.registerBlock(new BlockEnderTether(), "ender_tether");
        GameRegistry.registerTileEntity(TileEntityEnderTether.class, "ender_tether");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        tetherRange = config.getFloat("Tether Range", this.configName, 32f, 0.0f, 512f, "The range of the effect given by the tether. Distance is measured in blocks.");
        affectPlayers = config.getBoolean("Affect Players", this.configName, true, "Should the Ender Tether catch players using ender teleportation?");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderTether.class, new RendererEnderTether());
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    public static void trackTether (TileEntityEnderTether tile) {

        if (tile.getWorld() instanceof WorldServer) {

            getTethers((WorldServer) tile.getWorld()).add(tile);
        }
    }

    public static void stopTrackingTether (TileEntityEnderTether tile) {

        if (tile.getWorld() instanceof WorldServer) {

            getTethers((WorldServer) tile.getWorld()).remove(tile);
        }
    }

    public static List<TileEntityEnderTether> getTethers (WorldServer world) {

        return LOADED_TETHERS.computeIfAbsent(world, key -> {
            return new ArrayList<>();
        });
    }

    public static boolean isTracked (TileEntityEnderTether tile) {

        if (tile.getWorld() instanceof WorldServer) {

            return getTethers((WorldServer) tile.getWorld()).contains(tile);
        }

        return false;
    }

    @SubscribeEvent
    public static void onWorldUnload (WorldEvent.Unload event) {

        if (event.getWorld() instanceof WorldServer) {

            LOADED_TETHERS.remove(event.getWorld());
        }
    }

    @SubscribeEvent
    public void onEnderTeleport (EnderTeleportEvent event) {

        if (event.getEntityLiving() instanceof EntityLivingBase && !event.getEntityLiving().isDead && event.getEntityLiving().getEntityWorld() instanceof WorldServer) {
            for (final TileEntityEnderTether tile : getTethers((WorldServer) event.getEntityLiving().getEntityWorld())) {
                if (tile.isEntityCloseEnough(event.getEntityLiving())) {

                    final BlockPos pos = tile.getPos();
                    event.setTargetX(pos.getX() + 0.5f);
                    event.setTargetY(pos.getY());
                    event.setTargetZ(pos.getZ() + 0.5f);
                    break;
                }
            }
        }
    }
}
