package net.darkhax.darkutils.features.gluttonycharm;

import baubles.api.BaubleType;
import net.darkhax.bookshelf.lib.modutils.baubles.ItemBauble;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class ItemGluttonyCharm extends ItemBauble {
    
    public ItemGluttonyCharm() {
        
        this.setMaxStackSize(1);
    }
    
    @Override
    @Optional.Method(modid = "Baubles")
    public BaubleType getBaubleType (ItemStack itemstack) {
        
        return BaubleType.CHARM;
    }
}
