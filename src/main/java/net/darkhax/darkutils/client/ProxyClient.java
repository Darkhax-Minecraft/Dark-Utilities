package net.darkhax.darkutils.client;

import net.darkhax.darkutils.blocks.BlockTrapTile;
import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.handler.ContentHandler;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void onPreInit () {
        
        Item itemBlock;
        
        itemBlock = Item.getItemFromBlock(ContentHandler.blockTrap);
        
        for (BlockTrapTile.EnumType varient : BlockTrapTile.EnumType.values())
            ModelLoader.setCustomModelResourceLocation(itemBlock, varient.meta, new ModelResourceLocation("darkutils:trap_" + varient.name(), "inventory"));
            
        itemBlock = Item.getItemFromBlock(ContentHandler.blockEnderTether);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation("darkutils:ender_tether", "inventory"));
        
        itemBlock = Item.getItemFromBlock(ContentHandler.blockTrapMovement);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation("darkutils:trap_move", "inventory"));
        
        itemBlock = Item.getItemFromBlock(ContentHandler.blockGrate);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation("darkutils:grate", "inventory"));
    }
    
    @Override
    public void onInit () {
    
    }
    
    @Override
    public void onPostInit () {
    
    }
}
