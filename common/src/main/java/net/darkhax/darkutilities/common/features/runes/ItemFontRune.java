package net.darkhax.darkutilities.common.features.runes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class ItemFontRune extends Item {

    private final FontDescription.Resource fontId;

    public ItemFontRune(Properties properties, Identifier fontId) {
        super(properties);
        this.fontId = new FontDescription.Resource(fontId);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("font." + fontId.id().getNamespace() + "." + fontId.id().getPath() + ".preview").withStyle(style -> style.withFont(this.fontId).withColor(ChatFormatting.GRAY)));
    }
}