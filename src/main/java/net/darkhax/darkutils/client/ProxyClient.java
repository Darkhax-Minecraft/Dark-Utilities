package net.darkhax.darkutils.client;

import net.darkhax.darkutils.blocks.BlockTrapTile;
import net.darkhax.darkutils.client.handler.ContentHandler;
import net.darkhax.darkutils.common.ProxyCommon;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void onPreInit () {
        
        Item itemBlockTrap = Item.getItemFromBlock(ContentHandler.blockTrap);
        for (BlockTrapTile.EnumType varient : BlockTrapTile.EnumType.values())
            ModelLoader.setCustomModelResourceLocation(itemBlockTrap, varient.meta, new ModelResourceLocation("darkutils:trap_" + varient.name()));
    }
    
    @Override
    public void onInit () {
    
    }
    
    @Override
    public void onPostInit () {
    
    }
}
