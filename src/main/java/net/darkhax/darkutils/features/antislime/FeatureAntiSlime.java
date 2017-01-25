package net.darkhax.darkutils.features.antislime;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.STONE;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.libs.ModUtils;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureAntiSlime extends Feature {

    public static Block blockAntiSlime;

    public static boolean craftable;

    @Override
    public void onPreInit () {

        blockAntiSlime = new BlockAntiSlime();
        ModUtils.registerBlock(blockAntiSlime, "anti_slime");
        GameRegistry.registerTileEntity(TileEntityAntiSlime.class, "anti_slime");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the Anti Slime block be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockAntiSlime), new Object[] { "sws", "wcw", "sws", 's', STONE, 'w', Blocks.COBBLESTONE_WALL, 'c', ModUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 2)) }));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockAntiSlime);
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void onEntitySpawn (EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof EntitySlime && event.getEntity().getEntityWorld() != null) {

            final java.util.Iterator<TileEntity> it = event.getEntity().getEntityWorld().loadedTileEntityList.iterator();

            while (it.hasNext()) {

                final TileEntity tile = it.next();
                if (tile instanceof TileEntityAntiSlime && !event.getEntity().hasCustomName() && ((TileEntityAntiSlime) tile).shareChunks((EntityLivingBase) event.getEntity())) {

                    if (tile.getWorld().isBlockPowered(tile.getPos()))
                        continue;

                    event.getEntity().setDead();
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }
}
