package net.darkhax.darkutils;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot {
    
    private final Predicate<ItemStack> inputValidator;
    
    @Nullable
    private final Consumer<ItemStack> inputListener;
    
    public SlotInput(IInventory inventory, int index, int xPosition, int yPosition, @Nullable Predicate<ItemStack> inputValidator, Consumer<ItemStack> inputListener) {
        
        super(inventory, index, xPosition, yPosition);
        this.inputValidator = inputValidator;
        this.inputListener = inputListener;
    }
    
    @Override
    public boolean isItemValid (ItemStack stack) {
        
        return this.inputValidator.test(stack);
    }
    
    @Override
    public void putStack (ItemStack stack) {
        
        if (this.inputListener != null) {
            
            this.inputListener.accept(stack);
        }
        
        super.putStack(stack);
    }
}