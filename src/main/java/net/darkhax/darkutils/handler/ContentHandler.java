package net.darkhax.darkutils.handler;

import net.darkhax.darkutils.blocks.BlockTrapMovement;
import net.darkhax.darkutils.blocks.BlockEnderTether;
import net.darkhax.darkutils.blocks.BlockGrate;
import net.darkhax.darkutils.blocks.BlockTrapEffect;
import net.darkhax.darkutils.items.ItemBlockBasic;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ContentHandler {
    
    public static Block blockTrap;
    public static Block blockEnderTether;
    public static Block blockTrapMovement;
    public static Block blockGrate;
    
    public static void initBlocks () {
        
        blockTrap = new BlockTrapEffect();
        GameRegistry.registerBlock(blockTrap, ItemBlockBasic.class, "trap_tile", new Object[] { BlockTrapEffect.EnumType.getTypes() });
        
        blockEnderTether = new BlockEnderTether();
        GameRegistry.registerBlock(blockEnderTether, "ender_tether");
        GameRegistry.registerTileEntity(TileEntityEnderTether.class, "ender_tether");
        
        blockTrapMovement = new BlockTrapMovement();
        GameRegistry.registerBlock(blockTrapMovement, "trap_move");
        
        blockGrate = new BlockGrate();
        GameRegistry.registerBlock(blockGrate, "grate");
    }
    
    public static void initItems () {
    
    }
    
    public static void initRecipes () {
    
    }
}
