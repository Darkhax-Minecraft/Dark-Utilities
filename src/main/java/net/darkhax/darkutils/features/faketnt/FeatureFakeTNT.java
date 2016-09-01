package net.darkhax.darkutils.features.faketnt;

import static net.darkhax.bookshelf.lib.util.OreDictUtils.GUNPOWDER;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FeatureFakeTNT extends Feature {
    
    public static Block blockFakeTNT;
    private static boolean craftable;
    
    @Override
    public void onPreInit () {
        
        blockFakeTNT = new BlockFakeTNT();
        ModUtils.registerBlock(blockFakeTNT, "fake_tnt");
        EntityRegistry.registerModEntity(EntityFakeTNT.class, "FakeTNT", 0, DarkUtils.instance, 32, 20, true);
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftable = config.getBoolean("Craftable", this.configName, true, "Should fake TNT be craftable?");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftable)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFakeTNT), new Object[] { "gwg", "wgw", "gwg", 'g', GUNPOWDER, 'w', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) }));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerBlockInvModel(blockFakeTNT);
        RenderingRegistry.registerEntityRenderingHandler(EntityFakeTNT.class, new RenderFactoryTNT());
    }
}
