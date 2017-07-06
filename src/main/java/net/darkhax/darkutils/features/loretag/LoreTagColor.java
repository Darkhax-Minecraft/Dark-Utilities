package net.darkhax.darkutils.features.loretag;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LoreTagColor implements IItemColor {

    @Override
    public int getColorFromItemstack (ItemStack stack, int tintIndex) {

        final ChatFormatting format = ItemFormatLoreTag.getTagFormatting(stack);
        return tintIndex == 1 && format != null ? Minecraft.getMinecraft().fontRenderer.getColorCode(format.getChar()) : -1;
    }
}
