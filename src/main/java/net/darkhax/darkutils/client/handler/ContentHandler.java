package net.darkhax.darkutils.client.handler;

import net.darkhax.darkutils.blocks.BlockTrapTile;
import net.darkhax.darkutils.items.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ContentHandler {
    
    public static Block blockTrap;
    
    public static void initBlocks () {
        
        blockTrap = new BlockTrapTile();
        GameRegistry.registerBlock(blockTrap, ItemBlockBasic.class, "trap_tile", new Object[] { BlockTrapTile.EnumType.getTypes() });
        
    }
    
    public static void initItems () {
    
    }
    
    public static void initRecipes () {
    
    }
}
