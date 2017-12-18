package net.darkhax.darkutils.features.charms;

import net.darkhax.bookshelf.inventory.SlotFake;
import net.darkhax.bookshelf.item.ItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNullCharm extends Container {

    private final ItemInventory itemInv;

    public ContainerNullCharm (InventoryPlayer playerInv, ItemInventory inventory) {

        // Charm inventory
        for (int charmRow = 0; charmRow < 3; ++charmRow) {
            for (int charmColumn = 0; charmColumn < 9; ++charmColumn) {
                this.addSlotToContainer(new SlotFake(inventory, charmColumn + charmRow * 9, 8 + charmColumn * 18, 18 + charmRow * 18));
            }
        }

        // Player inventory
        for (int invRow = 0; invRow < 3; ++invRow) {
            for (int invColumn = 0; invColumn < 9; ++invColumn) {
                this.addSlotToContainer(new Slot(playerInv, invColumn + invRow * 9 + 9, 8 + invColumn * 18, 84 + invRow * 18));
            }
        }

        // Hotbar
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            this.addSlotToContainer(new Slot(playerInv, hotbarSlot, 8 + hotbarSlot * 18, 142));
        }

        this.itemInv = inventory;
    }

    @Override
    public ItemStack slotClick (int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {

        // Go away evil slots
        if (slotId < 0 || slotId >= this.inventorySlots.size()) {
            
            return ItemStack.EMPTY;
        }
        
        final Slot slot = this.inventorySlots.get(slotId);

        // Prevent the player from moving null charms in the null charm inventory.
        if (!(slot instanceof SlotFake) && slot.getHasStack() && slot.getStack().getItem() instanceof ItemNullCharm) {

            return ItemStack.EMPTY;
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canDragIntoSlot (Slot slot) {

        return false;
    }

    @Override
    public boolean canInteractWith (EntityPlayer player) {

        return true;
    }

    @Override
    public ItemStack transferStackInSlot (EntityPlayer player, int index) {

        final Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack() && !slot.getStack().isEmpty()) {

            for (int fakeSlotID = 0; fakeSlotID < this.itemInv.getSizeInventory(); fakeSlotID++) {

                final Slot fakeSlot = this.getSlot(fakeSlotID);

                // Slot doesn't have an item. Add the copy.
                if (!fakeSlot.getHasStack()) {

                    fakeSlot.putStack(slot.getStack());
                    return ItemStack.EMPTY;
                }
            }
        }

        return ItemStack.EMPTY;
    }
}