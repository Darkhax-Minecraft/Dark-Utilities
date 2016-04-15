package net.darkhax.darkutils.client.renderer;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class PotionColorHandler implements IItemColor {
    
    @Override
    public int getColorFromItemstack (ItemStack stack, int renderPass) {
        
        return renderPass > 0 ? 16777215 : stack.getMetadata() == 0 ? 16710911 : 7371335;
    }
}
