package net.darkhax.darkutils.features.antislime;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@DUFeature(name = "Anti Slime Block", description = "Undo slime chunks")
public class FeatureAntiSlime extends Feature {

    public static Block blockAntiSlime;

    @Override
    public void onPreInit () {

        blockAntiSlime = DarkUtils.REGISTRY.registerBlock(new BlockAntiSlime(), "anti_slime");
        GameRegistry.registerTileEntity(TileEntityAntiSlime.class, "anti_slime");
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void checkSpawn (EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof EntitySlime && !event.getEntity().hasCustomName()) {

            final List<TileEntity> tiles = new ArrayList<>(event.getWorld().loadedTileEntityList);

            for (final TileEntity tile : tiles) {
                if (tile instanceof TileEntityAntiSlime && ((TileEntityAntiSlime) tile).shareChunks((EntityLivingBase) event.getEntity())) {

                    if (event.getWorld().isBlockPowered(tile.getPos())) {
                        continue;
                    }

                    event.setCanceled(true);
                    event.getEntity().setDead();
                    break;
                }
            }
        }
    }
}
