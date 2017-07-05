package net.darkhax.darkutils.features.loretag;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class LoreTagColor implements IItemColor {

    @Override
    public int getColorFromItemstack (ItemStack stack, int tintIndex) {

        final ChatFormatting format = ItemFormatLoreTag.getTagFormatting(stack);
        return tintIndex == 1 && format != null ? Minecraft.getMinecraft().fontRenderer.getColorCode(format.getChar()) : -1;
    }
}
