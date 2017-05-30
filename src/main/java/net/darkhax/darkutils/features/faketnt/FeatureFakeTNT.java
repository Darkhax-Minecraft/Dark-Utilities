package net.darkhax.darkutils.features.faketnt;

import static net.darkhax.bookshelf.util.OreDictUtils.GUNPOWDER;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@DUFeature(name = "Fake TNT", description = "A safe TNT alternative")
public class FeatureFakeTNT extends Feature {

    public static Block blockFakeTNT;

    private static boolean craftable;

    @Override
    public void onRegistry () {

        blockFakeTNT = DarkUtils.REGISTRY.registerBlock(new BlockFakeTNT(), "fake_tnt");
    }

    @Override
    public void onPreInit () {

        EntityRegistry.registerModEntity(new ResourceLocation("darkutils", "fake_tnt"), EntityFakeTNT.class, "FakeTNT", 0, DarkUtils.instance, 32, 20, true);
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftable = config.getBoolean("Craftable", this.configName, true, "Should fake TNT be craftable?");
    }

    @Override
    public void setupRecipes () {

        if (craftable) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFakeTNT), new Object[] { "gwg", "wgw", "gwg", 'g', GUNPOWDER, 'w', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) }));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientRegistry () {

        RenderingRegistry.registerEntityRenderingHandler(EntityFakeTNT.class, new RenderFactoryTNT());
    }
}
