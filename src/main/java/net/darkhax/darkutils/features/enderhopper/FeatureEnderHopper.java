package net.darkhax.darkutils.features.enderhopper;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@DUFeature(name = "Ender Hopper", description = "A hopper which can pick up blocks within range")
public class FeatureEnderHopper extends Feature {

    public static Block blockEnderHopper;

    public static Block blockEnderPearlHopper;

    public static int hopperRange = 4;

    public static boolean allowBoundsRendering = true;

    @Override
    public void onPreInit () {

        GameRegistry.registerTileEntity(TileEntityEnderHopper.class, "ender_hopper");
        blockEnderHopper = DarkUtils.REGISTRY.registerBlock(new BlockEnderHopper(), "ender_hopper");

        blockEnderPearlHopper = DarkUtils.REGISTRY.registerBlock(new BlockEnderHopper(), "ender_pearl_hopper");
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("ender_hopper", new ItemStack(blockEnderHopper), " p ", "oho", 'p', new ItemStack(FeatureMaterial.itemMaterial, 1, 1), 'o', OreDictUtils.OBSIDIAN, 'h', Blocks.HOPPER);
        DarkUtils.REGISTRY.addShapedRecipe("ender_hopper_upgrade", new ItemStack(blockEnderPearlHopper), "ppp", "php", "ppp", 'p', "gemPearl", 'h', blockEnderHopper);
    }

    @Override
    public void setupConfiguration (Configuration config) {

        hopperRange = config.getInt("Range", this.configName, 4, 0, 32, "The detection range of the ender hopper. Distance in blocks outwards, starting at the hopper position, but not including it. A range of 4 does a 9x9x9 area around the hopper.");
        allowBoundsRendering = config.getBoolean("Render Block Bounds", this.configName, true, "Sneak clicking a hopper will render an outline of it's area of effect. This will render for all players. Allow this?");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        if (allowBoundsRendering) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderHopper.class, new RendererEnderHopper());
        }
    }
}
