package net.darkhax.darkutils.features.potion;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PotionColorHandler implements IItemColor {

    @Override
    public int getColorFromItemstack (ItemStack stack, int renderPass) {

        return renderPass > 0 ? 16777215 : stack.getMetadata() == 0 ? 16710911 : 7371335;
    }
}
