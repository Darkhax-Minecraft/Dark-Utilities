package net.darkhax.darkutils.features.loretag;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LoreTagColor implements IItemColor {

    @Override
    public int colorMultiplier (ItemStack stack, int tintIndex) {

        final LoreType format = ItemFormatLoreTag.getLore(stack);
        return tintIndex == 1 && format != null ? format.getColor() : -1;
    }
}
