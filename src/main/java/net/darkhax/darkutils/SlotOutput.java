package net.darkhax.darkutils;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot {
    
    private final Predicate<ItemStack> inputValidator;
    
    private final BiConsumer<PlayerEntity, ItemStack> takeListener;
    
    public SlotOutput(IInventory inventory, int index, int xPosition, int yPosition, BiConsumer<PlayerEntity, ItemStack> takeListener) {
        
        this(inventory, index, xPosition, yPosition, stack -> false, takeListener);
    }
    
    public SlotOutput(IInventory inventory, int index, int xPosition, int yPosition, Predicate<ItemStack> inputValidator, BiConsumer<PlayerEntity, ItemStack> takeListener) {
        
        super(inventory, index, xPosition, yPosition);
        this.inputValidator = inputValidator;
        this.takeListener = takeListener;
    }
    
    @Override
    public boolean isItemValid (ItemStack stack) {
        
        return this.inputValidator.test(stack);
    }
    
    @Override
    public ItemStack onTake (PlayerEntity player, ItemStack stack) {
        
        this.takeListener.accept(player, stack);
        return super.onTake(player, stack);
    }
}