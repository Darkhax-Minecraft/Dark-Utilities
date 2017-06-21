package net.darkhax.darkutils.features.enderhopper;

import net.darkhax.bookshelf.util.TempCraftingUtils;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.handler.RecipeHandler;
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

    public static int hopperRange = 4;

    public static boolean allowBoundsRendering = true;

    private static boolean craftable;

    @Override
    public void onRegistry () {

        blockEnderHopper = DarkUtils.REGISTRY.registerBlock(new BlockEnderHopper(), "ender_hopper");
    }

    @Override
    public void onPreInit () {

        GameRegistry.registerTileEntity(TileEntityEnderHopper.class, "ender_hopper");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should the Ender Hopper be craftable?");
        hopperRange = config.getInt("Range", this.configName, 4, 0, 32, "The detection range of the ender hopper. Distance in blocks outwards, starting at the hopper position, but not including it. A range of 4 does a 9x9x9 area around the hopper.");
        allowBoundsRendering = config.getBoolean("Render Block Bounds", this.configName, true, "Sneak clicking a hopper will render an outline of it's area of effect. This will render for all players. Allow this?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {

            RecipeHandler.addShapedOreRecipe(new ItemStack(blockEnderHopper), " p ", "oho", 'p', TempCraftingUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 1)), 'o', OreDictUtils.OBSIDIAN, 'h', Blocks.HOPPER);
            RecipeHandler.addShapedOreRecipe(new ItemStack(blockEnderHopper), " p ", "oho", 'p', OreDictUtils.ENDERPEARL, 'o', OreDictUtils.OBSIDIAN, 'h', Blocks.HOPPER);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientRegistry () {

        if (allowBoundsRendering) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderHopper.class, new RendererEnderHopper());
        }
    }
}
