package net.darkhax.darkutils.creativetab;

import net.darkhax.darkutils.handler.ContentHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabDarkUtils extends CreativeTabs {
    
    public CreativeTabDarkUtils() {
        
        super("darkutils");
        this.setBackgroundImageName("darkutils.png");
    }
    
    @Override
    public Item getTabIconItem () {
        
        return Item.getItemFromBlock(ContentHandler.blockTrapMovement);
    }
    
    @Override
    public boolean hasSearchBar () {
        
        return true;
    }
}