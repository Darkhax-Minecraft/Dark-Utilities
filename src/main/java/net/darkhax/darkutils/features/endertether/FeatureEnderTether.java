package net.darkhax.darkutils.features.endertether;

import static net.darkhax.bookshelf.util.OreDictUtils.INGOT_IRON;
import static net.darkhax.bookshelf.util.OreDictUtils.OBSIDIAN;

import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

@DUFeature(name = "Ender Tether", description = "A block to redirect ender teleportation")
public class FeatureEnderTether extends Feature {

    public static Block blockEnderTether;

    private static boolean craftable = true;

    protected static boolean affectPlayers = true;

    public static double tetherRange = 32D;

    @Override
    public void onPreInit () {

        blockEnderTether = new BlockEnderTether();
        ModUtils.registerBlock(blockEnderTether, "ender_tether");
        GameRegistry.registerTileEntity(TileEntityEnderTether.class, "ender_tether");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the Ender Tether be craftable?");
        tetherRange = config.getFloat("Tether Range", this.configName, 32f, 0.0f, 512f, "The range of the effect given by the tether. Distance is measured in blocks.");
        affectPlayers = config.getBoolean("Affect Players", this.configName, true, "Should the Ender Tether catch players using ender teleportation?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockEnderTether), new Object[] { " u ", "oto", 'u', ModUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 1)), 'o', OBSIDIAN, 't', Blocks.REDSTONE_TORCH, 'i', INGOT_IRON }));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        ModUtils.registerBlockInvModel(blockEnderTether);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderTether.class, new RendererEnderTether());
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SubscribeEvent
    public void onEnderTeleport (EnderTeleportEvent event) {

        if (event.getEntityLiving() instanceof EntityLivingBase && !event.getEntityLiving().isDead && event.getEntityLiving().getEntityWorld() != null && !event.getEntityLiving().getEntityWorld().isRemote) {
            for (final TileEntity tile : event.getEntityLiving().getEntityWorld().loadedTileEntityList)
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
