package net.darkhax.darkutils;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;

public class InventoryListener extends Inventory {
    
    @Nullable
    private Consumer<IInventory> listener;
    
    public InventoryListener(int size) {
        
        this(size, null);
    }
    
    public InventoryListener(int size, Consumer<IInventory> listener) {
        
        super(size);
        this.listener = listener;
    }
    
    @Override
    public void markDirty () {
        
        super.markDirty();
        this.listener.accept(this);
    }
    
    @Nullable
    public void setInventoryListener (Consumer<IInventory> listener) {
        
        this.listener = listener;
    }
}