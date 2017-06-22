package net.darkhax.darkutils.features.endertether;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@DUFeature(name = "Ender Tether", description = "A block to redirect ender teleportation")
public class FeatureEnderTether extends Feature {

    public static Block blockEnderTether;

    protected static boolean affectPlayers = true;

    public static double tetherRange = 32D;

    @Override
    public void onRegistry () {

        blockEnderTether = DarkUtils.REGISTRY.registerBlock(new BlockEnderTether(), "ender_tether");
    }

    @Override
    public void onPreInit () {

        GameRegistry.registerTileEntity(TileEntityEnderTether.class, "ender_tether");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        tetherRange = config.getFloat("Tether Range", this.configName, 32f, 0.0f, 512f, "The range of the effect given by the tether. Distance is measured in blocks.");
        affectPlayers = config.getBoolean("Affect Players", this.configName, true, "Should the Ender Tether catch players using ender teleportation?");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientRegistry () {

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderTether.class, new RendererEnderTether());
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void onEnderTeleport (EnderTeleportEvent event) {

        if (event.getEntityLiving() instanceof EntityLivingBase && !event.getEntityLiving().isDead && event.getEntityLiving().getEntityWorld() != null && !event.getEntityLiving().getEntityWorld().isRemote) {
            for (final TileEntity tile : event.getEntityLiving().getEntityWorld().loadedTileEntityList) {
                if (tile instanceof TileEntityEnderTether && ((TileEntityEnderTether) tile).isEntityCloseEnough(event.getEntityLiving())) {

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
