package net.darkhax.darkutilities.common.features.runes;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.darkutilities.common.DarkUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.Locale;
import java.util.function.Supplier;

public enum Font {

    BUILDER(Identifier.withDefaultNamespace("default"), true),
    GALACTIC(Identifier.withDefaultNamespace("alt"), true),
    ILLAGER(Identifier.withDefaultNamespace("illageralt"), true),
    RUNELIC(Identifier.fromNamespaceAndPath("runelic", "runelic"), Services.PLATFORM.isModLoaded("runelic")),
    PIGPEN(Identifier.fromNamespaceAndPath("pigpen", "pigpen"), Services.PLATFORM.isModLoaded("pigpen")),
    NYCTOGRAPHY(Identifier.fromNamespaceAndPath("nyctography", "nyctography"), Services.PLATFORM.isModLoaded("nyctography"));

    public final Identifier fontId;
    public final boolean available;
    public final Supplier<Item> runeItem;

    Font(Identifier fontId, boolean available) {
        this.fontId = fontId;
        this.available = available;
        this.runeItem = CachedSupplier.of(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(DarkUtils.MOD_ID, "rune_" + this.name().toLowerCase(Locale.ROOT)));
    }
}