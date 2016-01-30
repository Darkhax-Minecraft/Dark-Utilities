package net.darkhax.darkutils.addons.baubles;

import baubles.api.BaublesApi;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DarkUtilsBaublesAddon {
    
    public static boolean isPlayerWearingAmulet (EntityPlayer player, Item item) {
        
        return isPlayerWearingBauble(player, item, 0);
    }
    
    /**
     * A simple check to see if a player is wearing a certain item in either ring slot.
     * 
     * @param player: The Player to check.
     * @param item: The Item being looked for.
     * @return bollean: True if the player is wearing the specified item in either ring slot.
     */
    public static boolean isPlayerWearingRing (EntityPlayer player, Item item) {
        
        return isPlayerWearingBauble(player, item, 1) || isPlayerWearingBauble(player, item, 1);
    }
    
    /**
     * A simple check to see if a player is wearing a certain item in their belt slot.
     * 
     * @param player: The Player to check.
     * @param item: The Item being looked for.
     * @return boolean: True if the player is wearing the specified bauble.
     */
    public static boolean isPlayerWearingBelt (EntityPlayer player, Item item) {
        
        return isPlayerWearingBauble(player, item, 3);
    }
    
    /**
     * A check to see if a player is wearing a certain item in a certain bauble slot.
     * 
     * @param player: The Player to check.
     * @param item: The Item being looked for.
     * @param type: The type being looked for. 0 = Amulet, 1 = Ring 1, 2 = Ring 2, 3 = Belt.
     * @return boolean: True if the player is wearing the specified bauble.
     */
    public static boolean isPlayerWearingBauble (EntityPlayer player, Item item, int type) {
        
        IInventory inv = BaublesApi.getBaubles(player);
        
        if (inv != null) {
            
            ItemStack stack = inv.getStackInSlot(type);
            
            return (ItemStackUtils.isValidStack(stack) && stack.getItem() == item);
        }
        
        return false;
    }
}
