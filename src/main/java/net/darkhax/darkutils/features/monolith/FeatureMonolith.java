package net.darkhax.darkutils.features.monolith;

import java.util.List;
import java.util.stream.Collectors;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

@DUFeature(name = "Monolith", description = "Blocks with chunk based AOE effects.")
public class FeatureMonolith extends Feature {

    public static Block blockMonolith;

    @Override
    public void onPreInit () {

        blockMonolith = new BlockMonolith();
        DarkUtils.REGISTRY.registerBlock(blockMonolith, new ItemBlockBasic(blockMonolith, BlockMonolith.TYPES), "monolith");
        GameRegistry.registerTileEntity(TileEntityMonolithEXP.class, "monolith_exp");
        GameRegistry.registerTileEntity(TileEntityMonolithSpawning.class, "monolith_spawning");
    }

    @Override
    public void onPreRecipe () {

        DarkUtils.REGISTRY.addShapedRecipe("monolith_exp", new ItemStack(blockMonolith, 1, 0), "ppp", "pip", "bbb", 'p', "gemPearl", 'b', "blockPearl", 'i', Items.EXPERIENCE_BOTTLE);
        DarkUtils.REGISTRY.addShapedRecipe("monolith_spawn", new ItemStack(blockMonolith, 1, 0), "ppp", "pip", "bbb", 'p', "gemPearl", 'b', "blockPearl", 'i', Items.NETHER_STAR);
    }

    public static List<TileEntityMonolith> getMonolithInChunk (World world, BlockPos pos) {

        return world.getChunkFromBlockCoords(pos).getTileEntityMap().values().stream().filter(TileEntityMonolith.class::isInstance).map(TileEntityMonolith.class::cast).collect(Collectors.toList());
    }

    @Override
    public boolean usesEvents () {

        return true;
    }
}