package net.darkhax.darkutils.features.charms;

import baubles.api.BaubleType;
import net.darkhax.bookshelf.util.baubles.ItemBauble;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class ItemPortalCharm extends ItemBauble {

    public ItemPortalCharm () {

        this.maxStackSize = 1;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public BaubleType getBaubleType (ItemStack itemstack) {

        return BaubleType.CHARM;
    }
}