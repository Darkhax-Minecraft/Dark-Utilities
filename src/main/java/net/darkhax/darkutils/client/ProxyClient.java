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
        
        Item itemBlockTrap = Item.getItemFromBlock(ContentHandler.blockTrap);
        
        for (BlockTrapTile.EnumType varient : BlockTrapTile.EnumType.values())
            ModelLoader.setCustomModelResourceLocation(itemBlockTrap, varient.meta, new ModelResourceLocation("darkutils:trap_" + varient.name(), "inventory"));
            
        Item itemEnderTether = Item.getItemFromBlock(ContentHandler.blockEnderTether);
        ModelLoader.setCustomModelResourceLocation(itemEnderTether, 0, new ModelResourceLocation("darkutils:ender_tether", "inventory"));
    }
    
    @Override
    public void onInit () {
    
    }
    
    @Override
    public void onPostInit () {
    
    }
}
