package net.darkhax.darkutils.features.portalcharm;

import baubles.api.BaubleType;
import net.darkhax.bookshelf.lib.modutils.baubles.ItemBauble;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class ItemPortalCharm extends ItemBauble {

    public ItemPortalCharm () {

        this.maxStackSize = 1;
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public BaubleType getBaubleType (ItemStack itemstack) {

        return BaubleType.CHARM;
    }
}