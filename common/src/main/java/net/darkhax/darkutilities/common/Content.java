package net.darkhax.darkutilities.common;

import net.darkhax.bookshelf.common.api.data.loot.PoolTarget;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.registry.ContentProvider;
import net.darkhax.bookshelf.common.api.registry.adapters.GameRegistryAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.BlockRegistryAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.CreativeModeTabAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.ItemRegistryAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.LootPoolAdditionAdapter;
import net.darkhax.darkutilities.common.component.NameStyle;
import net.darkhax.darkutilities.common.features.charms.CharmEffects;
import net.darkhax.darkutilities.common.features.charms.ItemCharm;
import net.darkhax.darkutilities.common.features.filters.BlockEntityFilter;
import net.darkhax.darkutilities.common.features.filters.Filters;
import net.darkhax.darkutilities.common.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutilities.common.features.flatblocks.BlockFlatTileRotatable;
import net.darkhax.darkutilities.common.features.flatblocks.BlockFlatTileRotatableLightningUpgrade;
import net.darkhax.darkutilities.common.features.flatblocks.FlatTileEffects;
import net.darkhax.darkutilities.common.features.redstone.BlockRedstoneRandomizer;
import net.darkhax.darkutilities.common.features.redstone.BlockShieldedRedstone;
import net.darkhax.darkutilities.common.features.runes.Font;
import net.darkhax.darkutilities.common.features.runes.ItemFontRune;
import net.darkhax.darkutilities.common.recipe.SmithingFontNameRecipe;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Content implements ContentProvider {

    public static Set<Block> CUTOUT = new HashSet<>();
    private static final CachedSupplier<ItemStack> TAB_ICON = CachedSupplier.cache(() -> BuiltInRegistries.ITEM.getValue(DarkUtils.id("vector_plate")).getDefaultInstance());

    @Override
    public void defineBlocks(BlockRegistryAdapter registry) {

        registry.addPlaceable("blank_plate", BlockFlatTile.of(null), BlockFlatTile.PROPERTIES);

        // Vector Plates
        registry.addPlaceable("vector_plate", BlockFlatTileRotatable.of(FlatTileEffects.PUSH_WEAK), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("vector_plate_fast", BlockFlatTileRotatable.of(FlatTileEffects.PUSH_NORMAL), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("vector_plate_extreme", p -> new BlockFlatTileRotatableLightningUpgrade(p, FlatTileEffects.PUSH_STRONG, CachedSupplier.of(BuiltInRegistries.BLOCK, DarkUtils.MOD_ID, "vector_plate_ultra")), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("vector_plate_ultra", BlockFlatTileRotatable.of(FlatTileEffects.PUSH_ULTRA), BlockFlatTile.PROPERTIES);

        // Damage Plates
        registry.addPlaceable("damage_plate", BlockFlatTile.of(FlatTileEffects.DAMAGE_GENERIC), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("damage_plate_maim", BlockFlatTile.of(FlatTileEffects.DAMAGE_MAIM), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("damage_plate_player", BlockFlatTile.of(FlatTileEffects.DAMAGE_PLAYER), BlockFlatTile.PROPERTIES);

        // Effect Plates
        registry.addPlaceable("flame_plate", BlockFlatTile.of(FlatTileEffects.FLAME), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("slowness_plate", BlockFlatTile.of(FlatTileEffects.SLOWNESS), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("fatigue_plate", BlockFlatTile.of(FlatTileEffects.FATIGUE), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("darkness_plate", BlockFlatTile.of(FlatTileEffects.DARKNESS), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("hunger_plate", BlockFlatTile.of(FlatTileEffects.HUNGER), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("weakness_plate", BlockFlatTile.of(FlatTileEffects.WEAKNESS), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("poison_plate", BlockFlatTile.of(FlatTileEffects.POISON), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("wither_plate", BlockFlatTile.of(FlatTileEffects.WITHER), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("alert_plate", BlockFlatTile.of(FlatTileEffects.GLOWING), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("levitation_plate", BlockFlatTile.of(FlatTileEffects.LEVITATION), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("misfortune_plate", BlockFlatTile.of(FlatTileEffects.UNLUCK), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("slowfall_plate", BlockFlatTile.of(FlatTileEffects.SLOWFALL), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("omen_plate", BlockFlatTile.of(FlatTileEffects.OMEN), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("smite_plate", BlockFlatTile.of(FlatTileEffects.SMITE), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("bane_plate", BlockFlatTile.of(FlatTileEffects.BANE), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("frost_plate", BlockFlatTile.of(FlatTileEffects.FROST), BlockFlatTile.PROPERTIES);
        registry.addPlaceable("anchor_plate", BlockFlatTileRotatable.of(FlatTileEffects.ANCHOR), BlockFlatTile.PROPERTIES);

        // Mob Filters
        registry.addPlaceable("filter_player", BlockEntityFilter.of(Filters.PLAYER), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_undead", BlockEntityFilter.of(Filters.UNDEAD), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_arthropod", BlockEntityFilter.of(Filters.ARTHROPOD), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_illager", BlockEntityFilter.of(Filters.ILLAGER), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_raider", BlockEntityFilter.of(Filters.RAIDER), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_hostile", BlockEntityFilter.of(Filters.HOSTILE), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_animal", BlockEntityFilter.of(Filters.ANIMAL), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_child", BlockEntityFilter.of(Filters.BABY), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_pet", BlockEntityFilter.of(Filters.PET), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_slime", BlockEntityFilter.of(Filters.SLIME), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_villager", BlockEntityFilter.of(Filters.VILLAGER), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_zombie", BlockEntityFilter.of(Filters.ZOMBIE), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_piglin", BlockEntityFilter.of(Filters.PIGLIN), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_fire_immune", BlockEntityFilter.of(Filters.FIRE_IMMUNE), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_golem", BlockEntityFilter.of(Filters.GOLEM), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_water", BlockEntityFilter.of(Filters.WATER), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_named", BlockEntityFilter.of(Filters.NAMED), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_freeze_immune", BlockEntityFilter.of(Filters.FREEZE_IMMUNE), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_equipment", BlockEntityFilter.of(Filters.EQUIPMENT), BlockEntityFilter.PROPERTIES);
        registry.addPlaceable("filter_passenger", BlockEntityFilter.of(Filters.PASSENGER), BlockEntityFilter.PROPERTIES);

        // Redstone
        registry.addPlaceable("redstone_randomizer", BlockRedstoneRandomizer::new, p -> p.mapColor(MapColor.DEEPSLATE).strength(3.5f).randomTicks());
        registry.addPlaceable("shielded_redstone", BlockShieldedRedstone::new, p -> p.mapColor(MapColor.DEEPSLATE).strength(3.5f));
    }

    @Override
    public void defineLootPoolAdditions(LootPoolAdditionAdapter registry) {
        for (Font font : Font.values()) {
            if (font.available) {
                registry.add(font.name().toLowerCase(Locale.ROOT) + "_rune", PoolTarget.SIMPLE_DUNGEON_UNCOMMON, font.runeItem.get(), 5);
            }
        }
    }

    @Override
    public void defineRecipeSerializers(GameRegistryAdapter<RecipeSerializer<?>> registry) {
        registry.add(SmithingFontNameRecipe.RECIPE_ID.getPath(), SmithingFontNameRecipe.SERIALIZER);
    }

    @Override
    public void defineItemComponents(GameRegistryAdapter<DataComponentType<?>> registry) {
        registry.add(NameStyle.ID.getPath(), new DataComponentType.Builder<NameStyle>().persistent(NameStyle.CODEC.codec()).networkSynchronized(NameStyle.STREAM).cacheEncoding().build());
    }

    @Override
    public void defineItems(ItemRegistryAdapter registry) {
        for (CharmEffects effectType : CharmEffects.values()) {
            registry.add("charm_" + effectType.name().toLowerCase(Locale.ROOT), p -> new ItemCharm(p, effectType.effect), p -> p.stacksTo(1));
        }
        for (Font font : Font.values()) {
            registry.add("rune_" + font.name().toLowerCase(Locale.ROOT), p -> new ItemFontRune(p, font.fontId), p -> p.rarity(Rarity.UNCOMMON));
        }
    }

    @Override
    public void defineCreativeTabs(CreativeModeTabAdapter registry) {
        registry.add("tab", TAB_ICON, (params, builder) -> {
        });
    }

    @Override
    public String namespace() {
        return DarkUtils.MOD_ID;
    }
}