package net.darkhax.darkutils.features.charms;

import java.util.ArrayList;
import java.util.List;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

//TOOD baubles
public class ItemCharm extends Item {

    public ItemCharm () {

        this.setMaxStackSize(1);
    }

    public boolean hasItem (EntityPlayer player) {

        // Search for charm in equipment slots
        for (final EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {

            if (player.getItemStackFromSlot(slot).getItem() == this) {

                return true;
            }
        }

        // Search for charm in player inventory
        for (final ItemStack stack : player.inventory.mainInventory) {

            if (stack.getItem() == this) {

                return true;
            }
        }

        if (Loader.isModLoaded("baubles")) {

            if (this.hasBauble(player)) {

                return true;
            }
        }

        return false;
    }

    @Optional.Method(modid = "baubles")
    public boolean hasBauble (EntityPlayer player) {

        final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);

        for (int slot = 0; slot < baubles.getSlots(); slot++) {

            if (baubles.getStackInSlot(slot).getItem() == this) {

                return true;
            }
        }

        return false;
    }

    public List<ItemStack> getItem (EntityPlayer player) {

        final List<ItemStack> charms = new ArrayList<>();

        // Search for charm in equipment slots
        for (final EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {

            if (player.getItemStackFromSlot(slot).getItem() == this) {

                charms.add(player.getItemStackFromSlot(slot));
            }
        }

        // Search for charm in player inventory
        for (final ItemStack stack : player.inventory.mainInventory) {

            if (stack.getItem() == this) {

                charms.add(stack);
            }
        }

        if (Loader.isModLoaded("baubles")) {

            this.getBauble(player, charms);
        }

        return charms;
    }

    @Optional.Method(modid = "baubles")
    public void getBauble (EntityPlayer player, List<ItemStack> items) {

        final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);

        for (int slot = 0; slot < baubles.getSlots(); slot++) {

            if (baubles.getStackInSlot(slot).getItem() == this) {

                items.add(baubles.getStackInSlot(slot));
            }
        }
    }
}