package net.darkhax.darkutils.addons.curio;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ICurioAddon {
    
    public static final ICurioAddon DEFAULT = new ICurioAddon() {
    };
    
    default boolean isCurioApiAvailable () {
        
        return false;
    }
    
    default boolean hasCurioItem (Item item, LivingEntity user) {
        
        return false;
    }
    
    default ItemStack getCurioOfType (Item item, LivingEntity user) {
        
        return ItemStack.EMPTY;
    }
    
    default void enqueCommunication () {
        
    }
}