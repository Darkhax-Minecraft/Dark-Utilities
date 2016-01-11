package net.darkhax.darkutils.handler;

import net.darkhax.darkutils.blocks.BlockDirectionalTrap;
import net.darkhax.darkutils.blocks.BlockDog;
import net.darkhax.darkutils.blocks.BlockEnderTether;
import net.darkhax.darkutils.blocks.BlockTrapTile;
import net.darkhax.darkutils.items.ItemBlockBasic;
import net.darkhax.darkutils.tileentity.TileEntityDog;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ContentHandler {
    
    public static Block blockTrap;
    public static Block blockEnderTether;
    public static Block blockTrapMovement;
    
    public static void initBlocks () {
        
        blockTrap = new BlockTrapTile();
        GameRegistry.registerBlock(blockTrap, ItemBlockBasic.class, "trap_tile", new Object[] { BlockTrapTile.EnumType.getTypes() });
        
        blockEnderTether = new BlockEnderTether();
        GameRegistry.registerBlock(blockEnderTether, "ender_tether");
        GameRegistry.registerTileEntity(TileEntityEnderTether.class, "ender_tether");
        
        blockTrapMovement = new BlockDirectionalTrap();
        GameRegistry.registerBlock(blockTrapMovement, "trap_movement");
        
    }
    
    public static void initItems () {
    
    }
    
    public static void initRecipes () {
    
    }
}
