package net.darkhax.darkutilities.common;

import net.darkhax.bookshelf.common.api.data.loot.PoolTarget;
import net.darkhax.bookshelf.common.api.entity.villager.trades.VillagerSells;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.registry.ContentProvider;
import net.darkhax.bookshelf.common.api.registry.adapters.GameRegistryAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.BlockRegistryAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.BlockRenderTypeAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.CreativeModeTabAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.LootPoolAdditionAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.VillagerTradeAdapter;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Content implements ContentProvider {

    public static Set<Block> CUTOUT = new HashSet<>();
    private static final CachedSupplier<ItemStack> TAB_ICON = CachedSupplier.cache(() -> BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "vector_plate")).getDefaultInstance());

    @Override
    public void defineBlocks(BlockRegistryAdapter registry) {

        registry.addPlaceable("blank_plate", BlockFlatTile.of(null));

        // Vector Plates
        registry.addPlaceable("vector_plate", BlockFlatTileRotatable.of(FlatTileEffects.PUSH_WEAK));
        registry.addPlaceable("vector_plate_fast", BlockFlatTileRotatable.of(FlatTileEffects.PUSH_NORMAL));
        registry.addPlaceable("vector_plate_extreme", () -> new BlockFlatTileRotatableLightningUpgrade(FlatTileEffects.PUSH_STRONG, CachedSupplier.of(BuiltInRegistries.BLOCK, Constants.MOD_ID, "vector_plate_ultra")));
        registry.addPlaceable("vector_plate_ultra", BlockFlatTileRotatable.of(FlatTileEffects.PUSH_ULTRA));

        // Damage Plates
        registry.addPlaceable("damage_plate", BlockFlatTile.of(FlatTileEffects.DAMAGE_GENERIC));
        registry.addPlaceable("damage_plate_maim", BlockFlatTile.of(FlatTileEffects.DAMAGE_MAIM));
        registry.addPlaceable("damage_plate_player", BlockFlatTile.of(FlatTileEffects.DAMAGE_PLAYER));

        // Effect Plates
        registry.addPlaceable("flame_plate", BlockFlatTile.of(FlatTileEffects.FLAME));
        registry.addPlaceable("slowness_plate", BlockFlatTile.of(FlatTileEffects.SLOWNESS));
        registry.addPlaceable("fatigue_plate", BlockFlatTile.of(FlatTileEffects.FATIGUE));
        registry.addPlaceable("darkness_plate", BlockFlatTile.of(FlatTileEffects.DARKNESS));
        registry.addPlaceable("hunger_plate", BlockFlatTile.of(FlatTileEffects.HUNGER));
        registry.addPlaceable("weakness_plate", BlockFlatTile.of(FlatTileEffects.WEAKNESS));
        registry.addPlaceable("poison_plate", BlockFlatTile.of(FlatTileEffects.POISON));
        registry.addPlaceable("wither_plate", BlockFlatTile.of(FlatTileEffects.WITHER));
        registry.addPlaceable("alert_plate", BlockFlatTile.of(FlatTileEffects.GLOWING));
        registry.addPlaceable("levitation_plate", BlockFlatTile.of(FlatTileEffects.LEVITATION));
        registry.addPlaceable("misfortune_plate", BlockFlatTile.of(FlatTileEffects.UNLUCK));
        registry.addPlaceable("slowfall_plate", BlockFlatTile.of(FlatTileEffects.SLOWFALL));
        registry.addPlaceable("omen_plate", BlockFlatTile.of(FlatTileEffects.OMEN));
        registry.addPlaceable("smite_plate", BlockFlatTile.of(FlatTileEffects.SMITE));
        registry.addPlaceable("bane_plate", BlockFlatTile.of(FlatTileEffects.BANE));
        registry.addPlaceable("frost_plate", BlockFlatTile.of(FlatTileEffects.FROST));
        registry.addPlaceable("anchor_plate", BlockFlatTileRotatable.of(FlatTileEffects.ANCHOR));

        // Mob Filters
        registry.addPlaceable("filter_player", BlockEntityFilter.of(Filters.PLAYER));
        registry.addPlaceable("filter_undead", BlockEntityFilter.of(Filters.UNDEAD));
        registry.addPlaceable("filter_arthropod", BlockEntityFilter.of(Filters.ARTHROPOD));
        registry.addPlaceable("filter_illager", BlockEntityFilter.of(Filters.ILLAGER));
        registry.addPlaceable("filter_raider", BlockEntityFilter.of(Filters.RAIDER));
        registry.addPlaceable("filter_hostile", BlockEntityFilter.of(Filters.HOSTILE));
        registry.addPlaceable("filter_animal", BlockEntityFilter.of(Filters.ANIMAL));
        registry.addPlaceable("filter_child", BlockEntityFilter.of(Filters.BABY));
        registry.addPlaceable("filter_pet", BlockEntityFilter.of(Filters.PET));
        registry.addPlaceable("filter_slime", BlockEntityFilter.of(Filters.SLIME));
        registry.addPlaceable("filter_villager", BlockEntityFilter.of(Filters.VILLAGER));
        registry.addPlaceable("filter_fire_immune", BlockEntityFilter.of(Filters.FIRE_IMMUNE));
        registry.addPlaceable("filter_golem", BlockEntityFilter.of(Filters.GOLEM));
        registry.addPlaceable("filter_water", BlockEntityFilter.of(Filters.WATER));
        registry.addPlaceable("filter_named", BlockEntityFilter.of(Filters.NAMED));
        registry.addPlaceable("filter_freeze_immune", BlockEntityFilter.of(Filters.FREEZE_IMMUNE));
        registry.addPlaceable("filter_equipment", BlockEntityFilter.of(Filters.EQUIPMENT));
        registry.addPlaceable("filter_passenger", BlockEntityFilter.of(Filters.PASSENGER));

        // Redstone
        registry.addPlaceable("redstone_randomizer", BlockRedstoneRandomizer::new);
        registry.addPlaceable("shielded_redstone", BlockShieldedRedstone::new);
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
    public void defineTrades(VillagerTradeAdapter registry) {
        for (Font font : Font.values()) {
            if (font.available) {
                registry.addRareWanderingTrade(new VillagerSells(() -> font.runeItem.get().getDefaultInstance(), 12, 3, 0, 0.05f));
            }
        }
    }

    @Override
    public void defineRecipeSerializers(GameRegistryAdapter<RecipeSerializer<?>> registry) {
        registry.add(SmithingFontNameRecipe.RECIPE_ID.getPath(), SmithingFontNameRecipe.Serializer.INSTANCE);
    }

    @Override
    public void defineItemComponents(GameRegistryAdapter<DataComponentType<?>> registry) {
        registry.add(NameStyle.ID.getPath(), new DataComponentType.Builder<NameStyle>().persistent(NameStyle.CODEC.codec()).networkSynchronized(NameStyle.STREAM).cacheEncoding().build());
    }

    @Override
    public void defineItems(GameRegistryAdapter<Item> registry) {
        for (CharmEffects effectType : CharmEffects.values()) {
            registry.add("charm_" + effectType.name().toLowerCase(Locale.ROOT), new ItemCharm(effectType.effect));
        }
        for (Font font : Font.values()) {
            registry.add("rune_" + font.name().toLowerCase(Locale.ROOT), new ItemFontRune(font.fontId));
        }
    }

    @Override
    public void defineBlockRenderTypes(BlockRenderTypeAdapter registry) {
        for (Block block : CUTOUT) {
            registry.add(block, RenderType.cutout());
        }
    }

    @Override
    public void defineCreativeTabs(CreativeModeTabAdapter registry) {
        registry.add("tab", TAB_ICON, (params, builder) -> {
        });
    }

    @Override
    public String namespace() {
        return Constants.MOD_ID;
    }
}