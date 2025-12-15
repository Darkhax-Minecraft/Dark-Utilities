package net.darkhax.darkutilities.common.features.runes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemFontRune extends Item {

    private final ResourceLocation fontId;

    public ItemFontRune(ResourceLocation fontId) {
        super(new Properties().rarity(Rarity.UNCOMMON));
        this.fontId = fontId;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
        tooltips.add(Component.translatable("font." + fontId.getNamespace() + "." + fontId.getPath() + ".preview").withStyle(style -> style.withFont(this.fontId).withColor(ChatFormatting.GRAY)));
    }
}