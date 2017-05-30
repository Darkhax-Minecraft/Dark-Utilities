package net.darkhax.darkutils.features.antislime;

import static net.darkhax.bookshelf.util.OreDictUtils.STONE;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.bookshelf.util.CraftingUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@DUFeature(name = "Anti Slime Block", description = "Undo slime chunks")
public class FeatureAntiSlime extends Feature {

    public static Block blockAntiSlime;

    public static boolean craftable;

    @Override
    public void onRegistry () {

        blockAntiSlime = DarkUtils.REGISTRY.registerBlock(new BlockAntiSlime(), "anti_slime");
    }

    @Override
    public void onPreInit () {

        GameRegistry.registerTileEntity(TileEntityAntiSlime.class, "anti_slime");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the Anti Slime block be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntiSlime), new Object[] { "sws", "wcw", "sws", 's', STONE, 'w', Blocks.COBBLESTONE_WALL, 'c', CraftingUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 2)) }));
        }
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
