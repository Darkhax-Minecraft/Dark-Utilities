package net.darkhax.darkutils.features.faketnt;

import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@DUFeature(name = "Fake TNT", description = "A safe TNT alternative")
public class FeatureFakeTNT extends Feature {

    public static Block blockFakeTNT;

    @Override
    public void onPreInit () {

        blockFakeTNT = DarkUtils.REGISTRY.registerBlock(new BlockFakeTNT(), "fake_tnt");
        EntityRegistry.registerModEntity(new ResourceLocation("darkutils", "fake_tnt"), EntityFakeTNT.class, "FakeTNT", 0, DarkUtils.instance, 32, 20, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        RenderingRegistry.registerEntityRenderingHandler(EntityFakeTNT.class, new RenderFactoryTNT());
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("fake_tnt", new ItemStack(blockFakeTNT), "gwg", "wgw", "gwg", 'g', OreDictUtils.GUNPOWDER, 'w', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));
    }
}
