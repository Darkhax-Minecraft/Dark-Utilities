package net.darkhax.darkutils.creativetab;

import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabDarkUtils extends CreativeTabs {
    
    public CreativeTabDarkUtils() {
        
        super("darkutils");
        this.setBackgroundImageName("darkutils.png");
    }
    
    @Override
    public Item getTabIconItem () {
        
        if (ModUtils.BLOCKS.size() > 0)
            return Item.getItemFromBlock(ModUtils.BLOCKS.get(0));
            
        else if (ModUtils.ITEMS.size() > 0)
            return ModUtils.ITEMS.get(0);
            
        return Items.DRAGON_BREATH;
    }
    
    @Override
    public boolean hasSearchBar () {
        
        return true;
    }
}