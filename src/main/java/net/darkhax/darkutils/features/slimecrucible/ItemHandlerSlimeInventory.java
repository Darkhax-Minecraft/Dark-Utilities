package net.darkhax.darkutils.features.slimecrucible;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandlerSlimeInventory extends ItemStackHandler {

    private int slimePoints = 0;

    public ItemHandlerSlimeInventory () {

        super(2);
    }

    public int getSlimePoints () {
        
        return slimePoints;
    }
    
    @Override
    public CompoundNBT serializeNBT () {

        final CompoundNBT dataTag = super.serializeNBT();

        dataTag.putInt("SlimePoints", this.slimePoints);
        return dataTag;
    }

    @Override
    public void deserializeNBT (CompoundNBT nbt) {

        super.deserializeNBT(nbt);
        this.slimePoints = nbt.getInt("SlimePoints");
    }

    @Override
    public ItemStack extractItem (int slot, int amount, boolean simulate) {

        return slot == 1 ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack insertItem (int slot, @Nonnull ItemStack stack, boolean simulate) {

//        if (this.isItemValid(slot, stack) && slot == 0) {
//
//            ItemFood food = (ItemFood) stack.getItem();
//            
//            final ItemStack result = stack.copy();
//            result.shrink(1);
//            slimePoints += Math.max(food.getHealAmount(stack) * food.getSaturationModifier(stack), 1);
//            return result;
//        }

        return stack;
    }

    @Override
    public boolean isItemValid (int slot, @Nonnull ItemStack stack) {

        return !stack.isEmpty(); // && stack.getItem() instanceof ItemFood;
    }

    @Override
    public int getSlotLimit (int slot) {

        return 1;
    }
}