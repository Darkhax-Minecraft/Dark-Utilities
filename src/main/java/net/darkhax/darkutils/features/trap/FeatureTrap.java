package net.darkhax.darkutils.features.trap;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.vector.FeatureVectorPlate;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

@DUFeature(name = "Trap Blocks", description = "Trap blocks that have certain effects when stepped on")
public class FeatureTrap extends Feature {

    public static Block blockTrap;
    public static Block blockTrapAnchor;

    @Override
    public void onPreInit () {

        blockTrap = new BlockTrap();
        DarkUtils.REGISTRY.registerBlock(blockTrap, new ItemBlockBasic(blockTrap, TrapType.getTypes()), "trap_tile");
        blockTrapAnchor = DarkUtils.REGISTRY.registerBlock(new BlockTrapAnchor(), "trap_anchor");
    }

    @Override
    public void onPreRecipe () {

        for (final TrapType type : TrapType.values()) {

            DarkUtils.REGISTRY.addShapedRecipe("trap_" + type.getName().toLowerCase(), new ItemStack(blockTrap, 1, type.meta), "sis", 's', OreDictUtils.STONE, 'i', type.crafting);
        }
        
        DarkUtils.REGISTRY.addShapedRecipe("trap_anchor", new ItemStack(blockTrapAnchor), "tst", "s s", "tst", 't', FeatureVectorPlate.blockVectorPlate, 's', OreDictUtils.SLIMEBALL);
    }
}
