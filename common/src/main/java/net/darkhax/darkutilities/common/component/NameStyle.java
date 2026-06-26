package net.darkhax.darkutilities.common.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.darkutilities.common.DarkUtils;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

import java.util.Optional;

public record NameStyle(Identifier fontID, Optional<DyeColor> color) {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(DarkUtils.MOD_ID, "name_style");
    public static final CachedSupplier<DataComponentType<NameStyle>> COMPONENT = CachedSupplier.of(BuiltInRegistries.DATA_COMPONENT_TYPE, ID).cast();
    public static final MapCodec<NameStyle> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("font").forGetter(NameStyle::fontID),
            DyeColor.CODEC.optionalFieldOf("color").forGetter(NameStyle::color)
    ).apply(instance, NameStyle::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, NameStyle> STREAM = StreamCodec.of(
            (buf, val) -> {
                buf.writeIdentifier(val.fontID);
                buf.writeOptional(val.color, DyeColor.STREAM_CODEC);
            },
            buf -> {
                final Identifier font = buf.readIdentifier();
                final Optional<DyeColor> color = buf.readOptional(DyeColor.STREAM_CODEC);
                return new NameStyle(font, color);
            }
    );
    public FontDescription description() {
        return new FontDescription.Resource(this.fontID);
    }
}