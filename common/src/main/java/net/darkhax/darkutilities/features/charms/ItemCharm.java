package net.darkhax.darkutilities.features.charms;

import net.minecraft.world.item.Item;

public class ItemCharm extends Item {

    public ItemCharm() {

        super(new Properties().stacksTo(1));
    }

    @Override
    public int getEnchantmentValue() {

        // While charms have no valid enchantments yet, I am considering the possibility of adding some in the future.
        return 8;
    }
}