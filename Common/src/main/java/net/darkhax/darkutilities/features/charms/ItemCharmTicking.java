package net.darkhax.darkutilities.features.charms;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemCharmTicking extends ItemCharm {

    private final ITickEffect effect;

    public ItemCharmTicking(ITickEffect effect) {

        super();
        this.effect = effect;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity user, int slotIndex, boolean selected) {

        super.inventoryTick(stack, world, user, slotIndex, selected);
        this.effect.apply(stack, world, user, selected);
    }

    @FunctionalInterface
    public interface ITickEffect {

        void apply(ItemStack stack, Level world, Entity user, boolean selected);
    }
}
