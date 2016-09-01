package net.darkhax.darkutils.client.gui;

import net.darkhax.bookshelf.inventory.SlotFake;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFilter extends Container {
    
    private final IInventory inventory;
    private final int invStart;
    private final int invEnd;
    private final int hotbarStart;
    private final int hotbarEnd;
    
    public ContainerFilter(EntityPlayer player, IInventory inventory) {
        
        this.inventory = inventory;
        this.invStart = inventory.getSizeInventory();
        this.invEnd = this.invStart + 26;
        this.hotbarStart = this.invEnd + 1;
        this.hotbarEnd = this.hotbarStart + 8;
        inventory.openInventory(player);
        
        for (int invIndex = 0; invIndex < inventory.getSizeInventory(); ++invIndex)
            this.addSlotToContainer(new SlotFake(inventory, invIndex, 44 + invIndex * 18, 20));
            
        for (int playerInvX = 0; playerInvX < 3; ++playerInvX)
            for (int playerInvY = 0; playerInvY < 9; ++playerInvY)
                this.addSlotToContainer(new Slot(player.inventory, playerInvY + playerInvX * 9 + 9, 8 + playerInvY * 18, playerInvX * 18 + 51));
                
        for (int hotbarIndex = 0; hotbarIndex < 9; ++hotbarIndex)
            this.addSlotToContainer(new Slot(player.inventory, hotbarIndex, 8 + hotbarIndex * 18, 109));
    }
    
    @Override
    public ItemStack transferStackInSlot (EntityPlayer par1EntityPlayer, int index) {
        
        ItemStack itemstack = null;
        final Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (index < this.invStart) {
                if (!this.mergeItemStack(itemstack1, this.invStart, this.hotbarEnd + 1, true))
                    return null;
                    
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= this.invStart) {
                if (!this.mergeItemStack(itemstack1, 0, this.invStart, false))
                    return null;
            }
            
            else if (index >= this.hotbarStart && index < this.hotbarEnd + 1)
                if (!this.mergeItemStack(itemstack1, this.invStart, this.invEnd + 1, false))
                    return null;
                    
            if (itemstack1.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();
                
            if (itemstack1.stackSize == itemstack.stackSize)
                return null;
                
            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }
        
        return itemstack;
    }
    
    @Override
    public ItemStack slotClick (int slotId, int dragType, ClickType type, EntityPlayer player) {
        
        if (slotId >= 0) {
            
            final Slot slot = this.getSlot(slotId);
            
            if (slot != null && slot.getStack() == player.getHeldItemMainhand())
                return null;
                
            if (slot instanceof SlotFake)
                return ((SlotFake) slot).slotClicked(this, slotId, dragType, type, player);
        }
        
        return super.slotClick(slotId, dragType, type, player);
    }
    
    @Override
    public boolean canInteractWith (EntityPlayer player) {
        
        return true;
    }
    
    @Override
    public void onContainerClosed (EntityPlayer player) {
        
        super.onContainerClosed(player);
        this.inventory.closeInventory(player);
    }
    
    @Override
    protected boolean mergeItemStack (ItemStack stack, int start, int end, boolean backwards) {
        
        boolean flag1 = false;
        int k = backwards ? end - 1 : start;
        Slot slot;
        ItemStack itemstack1;
        
        if (stack.isStackable())
            while (stack.stackSize > 0 && (!backwards && k < end || backwards && k >= start)) {
                slot = this.inventorySlots.get(k);
                itemstack1 = slot.getStack();
                
                if (!slot.isItemValid(stack)) {
                    k += backwards ? -1 : 1;
                    continue;
                }
                
                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {
                    final int l = itemstack1.stackSize + stack.stackSize;
                    
                    if (l <= stack.getMaxStackSize() && l <= slot.getSlotStackLimit()) {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        this.inventory.markDirty();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < stack.getMaxStackSize() && l < slot.getSlotStackLimit()) {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        this.inventory.markDirty();
                        flag1 = true;
                    }
                }
                
                k += backwards ? -1 : 1;
            }
        if (stack.stackSize > 0) {
            k = backwards ? end - 1 : start;
            while (!backwards && k < end || backwards && k >= start) {
                slot = this.inventorySlots.get(k);
                itemstack1 = slot.getStack();
                
                if (!slot.isItemValid(stack)) {
                    k += backwards ? -1 : 1;
                    continue;
                }
                
                if (itemstack1 == null) {
                    final int l = stack.stackSize;
                    if (l <= slot.getSlotStackLimit()) {
                        slot.putStack(stack.copy());
                        stack.stackSize = 0;
                        this.inventory.markDirty();
                        flag1 = true;
                        break;
                    }
                    else {
                        this.putStackInSlot(k, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
                        stack.stackSize -= slot.getSlotStackLimit();
                        this.inventory.markDirty();
                        flag1 = true;
                    }
                }
                
                k += backwards ? -1 : 1;
            }
        }
        
        return flag1;
    }
}