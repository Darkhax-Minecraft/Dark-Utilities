package net.darkhax.darkutils.creativetab;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabDarkUtils extends CreativeTabs {
    
    /**
     * A List of all items which make use of this creative tab. This list is populated in the
     * same way a normal list is, however rather then generating the list every time the gui is
     * displayed, the entries are cached. This should greatly reduce CPU cycles, especially
     * when the client has many items installed.
     */
    private List<Item> cachedItems = new ArrayList<Item>();
    
    public CreativeTabDarkUtils() {
        
        super("darkutils");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems (List itemList) {
        
        if (this.cachedItems.isEmpty()) {
            
            Iterator iterator = Item.itemRegistry.iterator();
            
            while (iterator.hasNext()) {
                
                Item item = (Item) iterator.next();
                
                if (item != null)
                    for (CreativeTabs tab : item.getCreativeTabs())
                        if (tab == this)
                            this.cachedItems.add(item);
            }
        }
        
        for (Item item : this.cachedItems)
            if (item != null)
                for (CreativeTabs tab : item.getCreativeTabs())
                    if (tab == this)
                        item.getSubItems(item, this, itemList);
                        
        if (this.getRelevantEnchantmentTypes() != null)
            this.addEnchantmentBooksToList(itemList, this.getRelevantEnchantmentTypes());
    }
    
    @Override
    public Item getTabIconItem () {
        
        return Items.redstone;
    }
}