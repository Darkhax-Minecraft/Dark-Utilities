package net.darkhax.darkutils.features.antislime;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
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
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("antislime", new ItemStack(blockAntiSlime), "sws", "wbw", "sws", 's', OreDictUtils.STONE, 'w', Blocks.COBBLESTONE_WALL, 'b', new ItemStack(FeatureMaterial.itemMaterial, 1, 2));
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void checkSpawn (CheckSpawn event) {

        if (event.getEntity() instanceof EntitySlime && !event.getEntity().hasCustomName()) {

            for (final TileEntity tile : event.getWorld().getChunk(event.getEntity().getPosition()).getTileEntityMap().values()) {

                if (tile instanceof TileEntityAntiSlime && !event.getWorld().isBlockPowered(tile.getPos())) {

                    event.setResult(Result.DENY);
                    event.getEntity().setDead();
                    break;
                }
            }
        }
    }
}
