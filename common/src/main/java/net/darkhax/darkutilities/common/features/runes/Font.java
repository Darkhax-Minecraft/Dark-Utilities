package net.darkhax.darkutilities.common.features.runes;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.darkutilities.common.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Locale;
import java.util.function.Supplier;

public enum Font {

    BUILDER(ResourceLocation.withDefaultNamespace("default"), true),
    GALACTIC(ResourceLocation.withDefaultNamespace("alt"), true),
    ILLAGER(ResourceLocation.withDefaultNamespace("illageralt"), true),
    RUNELIC(ResourceLocation.fromNamespaceAndPath("runelic", "runelic"), Services.PLATFORM.isModLoaded("runelic")),
    PIGPEN(ResourceLocation.fromNamespaceAndPath("pigpen", "pigpen"), Services.PLATFORM.isModLoaded("pigpen")),
    NYCTOGRAPHY(ResourceLocation.fromNamespaceAndPath("nyctography", "nyctography"), Services.PLATFORM.isModLoaded("nyctography"));

    public final ResourceLocation fontId;
    public final boolean available;
    public final Supplier<Item> runeItem;

    Font(ResourceLocation fontId, boolean available) {
        this.fontId = fontId;
        this.available = available;
        this.runeItem = CachedSupplier.of(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rune_" + this.name().toLowerCase(Locale.ROOT)));
    }
}