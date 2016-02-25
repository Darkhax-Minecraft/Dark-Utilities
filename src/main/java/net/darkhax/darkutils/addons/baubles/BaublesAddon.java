package net.darkhax.darkutils.addons.baubles;

import baubles.api.BaublesApi;
import mezz.jei.api.IModRegistry;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.addons.ModAddon;
import net.darkhax.darkutils.handler.ContentHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class BaublesAddon implements ModAddon {
    
    @Override
    public void onPreInit () {
    
    }
    
    @Override
    public void onInit () {
    
    }
    
    @Override
    public void onPostInit () {
    
    }
    
    @Override
    public void onClientPreInit () {
    
    }
    
    @Override
    public void onJEIReady (IModRegistry registry) {
        
        registry.addDescription(new ItemStack(ContentHandler.itemEnchantedRing), "jei.darkutils.baubles.valid.desc");
    }
    
    /**
     * A simple check to see if the player is wearing a certain item in the amulet slot.
     * 
     * @param player The Player to check.
     * @param item The Item being looked for.
     * @return boolean True if the player is wearing the specified bauble.
     */
    public static boolean isPlayerWearingAmulet (EntityPlayer player, ItemStack item) {
        
        return isPlayerWearingBauble(player, item, 0);
    }
    
    /**
     * A simple check to see if a player is wearing a certain item in either ring slot.
     * 
     * @param player The Player to check.
     * @param item The Item being looked for.
     * @return boolean True if the player is wearing the specified item in either ring slot.
     */
    public static boolean isPlayerWearingRing (EntityPlayer player, ItemStack item) {
        
        return isPlayerWearingBauble(player, item, 1) || isPlayerWearingBauble(player, item, 2);
    }
    
    /**
     * A simple check to see if a player is wearing a certain item in their belt slot.
     * 
     * @param player The Player to check.
     * @param item The Item being looked for.
     * @return boolean True if the player is wearing the specified bauble.
     */
    public static boolean isPlayerWearingBelt (EntityPlayer player, ItemStack item) {
        
        return isPlayerWearingBauble(player, item, 3);
    }
    
    /**
     * A check to see if a player is wearing a certain item in a certain bauble slot.
     * 
     * @param player The Player to check.
     * @param item The Item being looked for.
     * @param type The type being looked for. 0 = Amulet, 1 = Ring 1, 2 = Ring 2, 3 = Belt.
     * @return boolean True if the player is wearing the specified bauble.
     */
    public static boolean isPlayerWearingBauble (EntityPlayer player, ItemStack item, int type) {
        
        IInventory inv = BaublesApi.getBaubles(player);
        
        if (inv != null) {
            
            ItemStack stack = inv.getStackInSlot(type);
            
            return (ItemStackUtils.isValidStack(stack) && ItemStackUtils.areStacksSimilar(stack, item));
        }
        
        return false;
    }
    
    /**
     * Attempts to equip a ItemStack in a bauble slot. An item can only be equipped into the
     * slot if the slot is empty.
     * 
     * @param player The player to equipped the item onto.
     * @param item The item to equip.
     * @param type The bauble type or slot index. 0 = Amulet, 1 = Ring 1, 2 = Ring 2, 3 = Belt.
     * @return boolean Whether or not the bauble was successfully equipped.
     */
    public static boolean equipBauble (EntityPlayer player, ItemStack item, int type) {
        
        IInventory inv = BaublesApi.getBaubles(player);
        
        if (inv != null) {
            
            ItemStack existing = inv.getStackInSlot(type);
            
            if (existing == null) {
                
                System.out.println("true: " + type);
                inv.setInventorySlotContents(type, item.copy());
                item.stackSize = 0;
                return true;
            }
        }
        
        System.out.println("false: " + type);
        return false;
    }
}