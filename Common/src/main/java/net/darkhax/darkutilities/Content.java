package net.darkhax.darkutilities;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.bookshelf.api.entity.merchant.trade.VillagerSells;
import net.darkhax.bookshelf.api.registry.IRegistryEntries;
import net.darkhax.bookshelf.api.registry.RegistryHelper;
import net.darkhax.bookshelf.api.util.TextHelper;
import net.darkhax.darkutilities.block.IItemBlockProvider;
import net.darkhax.darkutilities.features.charms.CharmEffects;
import net.darkhax.darkutilities.features.charms.ItemCharm;
import net.darkhax.darkutilities.features.charms.ItemCharmTicking;
import net.darkhax.darkutilities.features.filters.BlockEntityFilter;
import net.darkhax.darkutilities.features.filters.Filters;
import net.darkhax.darkutilities.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutilities.features.flatblocks.BlockFlatTileRotatable;
import net.darkhax.darkutilities.features.flatblocks.BlockFlatTileRotatableLightningUpgrade;
import net.darkhax.darkutilities.features.flatblocks.FlatTileEffects;
import net.darkhax.darkutilities.features.redstone.BlockRedstoneRandomizer;
import net.darkhax.darkutilities.features.redstone.BlockShieldedRedstone;
import net.darkhax.darkutilities.features.tomes.ItemTome;
import net.darkhax.darkutilities.features.tomes.ItemTomeFont;
import net.darkhax.darkutilities.features.tomes.TomeEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class Content {

    private final RegistryHelper registry = Services.REGISTRY_HELPER.create(Constants.MOD_ID);

    public final CreativeModeTab tab;

    public final Block blankPlate;
    public final Block vectorPlate;
    public final Block vectorPlateFast;
    public final Block vectorPlateExtreme;
    public final Block vectorPlateUltra;
    public final Block damagePlate;
    public final Block playerDamagePlate;
    public final Block flamePlate;
    public final Block slownessPlate;
    public final Block fatiguePlate;
    public final Block darknessPlate;
    public final Block hungerPlate;
    public final Block weaknessPlate;
    public final Block poisonPlate;
    public final Block witherPlate;
    public final Block glowingPlate;
    public final Block levitationPlate;
    public final Block misfortunePlate;
    public final Block slowfallPlate;
    public final Block omenPlate;
    public final Block frostPlate;
    public final Block maimPlate;
    public final Block filterPlayer;
    public final Block filterUndead;
    public final Block filterArthropod;
    public final Block filterIllager;
    public final Block filterRaider;
    public final Block filterHostile;
    public final Block filterAnimal;
    public final Block filterBaby;
    public final Block filterPet;
    public final Block filterSlime;
    public final Block filterVillager;
    public final Block filterFireImmune;
    public final Block filterGolem;
    public final Block filterWater;
    public final Block filterNamed;
    public final Block filterFreezeImmune;
    public final Block filterEquipment;
    public final Block filterPassenger;
    public final Block redstoneRandomiser;
    public final Block shieldedRedstone;

    protected final Map<Item, Component> tooltipCache = new HashMap<>();

    public final ItemCharm portalCharm;
    public final ItemCharm sleepCharm;
    public final ItemCharm wardingCharm;
    public final Item enchantingTome;
    public final Item galacticTome;
    public final Item illagerTome;
    public final Item shadowTome;

    // Mod Support
    public Item pigpenTome;
    public Item runelicTome;
    public Item unownTome;

    private ItemStack getTabIcon() {

        return Item.byBlock(this.vectorPlate).getDefaultInstance();
    }

    public Content() {

        this.tab = registry.createTabBuilder("general").setIcon(this::getTabIcon).build();
        this.registry.withCreativeTab(this.tab);
        this.registry.blocks.addInsertListener(this::onBlockRegistered);
        this.registry.items.addInsertListener(this::onItemRegistered);

        final IRegistryEntries<Block> blocks = registry.blocks;
        this.blankPlate = blocks.add(new BlockFlatTile(), "blank_plate");
        this.vectorPlate = blocks.add(new BlockFlatTileRotatable(FlatTileEffects.PUSH_WEAK), "vector_plate");
        this.vectorPlateFast = blocks.add(new BlockFlatTileRotatable(FlatTileEffects.PUSH_NORMAL), "vector_plate_fast");
        this.vectorPlateExtreme = blocks.add(new BlockFlatTileRotatableLightningUpgrade(FlatTileEffects.PUSH_STRONG, this::getVectorPlateUltra), "vector_plate_extreme");
        this.vectorPlateUltra = blocks.add(new BlockFlatTileRotatable(FlatTileEffects.PUSH_ULTRA), "vector_plate_ultra");
        this.damagePlate = blocks.add(new BlockFlatTile(FlatTileEffects.DAMAGE_GENERIC), "damage_plate");
        this.maimPlate = blocks.add(new BlockFlatTile(FlatTileEffects.DAMAGE_MAIM), "damage_plate_maim");
        this.playerDamagePlate = blocks.add(new BlockFlatTile(FlatTileEffects.DAMAGE_PLAYER), "damage_plate_player");
        this.flamePlate = blocks.add(new BlockFlatTile(FlatTileEffects.FLAME), "flame_plate");
        this.slownessPlate = blocks.add(new BlockFlatTile(FlatTileEffects.SLOWNESS), "slowness_plate");
        this.fatiguePlate = blocks.add(new BlockFlatTile(FlatTileEffects.FATIGUE), "fatigue_plate");
        this.darknessPlate = blocks.add(new BlockFlatTile(FlatTileEffects.DARKNESS), "darkness_plate");
        this.hungerPlate = blocks.add(new BlockFlatTile(FlatTileEffects.HUNGER), "hunger_plate");
        this.weaknessPlate = blocks.add(new BlockFlatTile(FlatTileEffects.WEAKNESS), "weakness_plate");
        this.poisonPlate = blocks.add(new BlockFlatTile(FlatTileEffects.POISON), "poison_plate");
        this.witherPlate = blocks.add(new BlockFlatTile(FlatTileEffects.WITHER), "wither_plate");
        this.glowingPlate = blocks.add(new BlockFlatTile(FlatTileEffects.GLOWING), "alert_plate");
        this.levitationPlate = blocks.add(new BlockFlatTile(FlatTileEffects.LEVITATION), "levitation_plate");
        this.misfortunePlate = blocks.add(new BlockFlatTile(FlatTileEffects.UNLUCK), "misfortune_plate");
        this.slowfallPlate = blocks.add(new BlockFlatTile(FlatTileEffects.SLOWFALL), "slowfall_plate");
        this.omenPlate = blocks.add(new BlockFlatTile(FlatTileEffects.OMEN), "omen_plate");
        this.frostPlate = blocks.add(new BlockFlatTile(FlatTileEffects.FROST), "frost_plate");
        this.filterPlayer = blocks.add(new BlockEntityFilter(Filters.PLAYER), "filter_player");
        this.filterUndead = blocks.add(new BlockEntityFilter(Filters.UNDEAD), "filter_undead");
        this.filterArthropod = blocks.add(new BlockEntityFilter(Filters.ARTHROPOD), "filter_arthropod");
        this.filterIllager = blocks.add(new BlockEntityFilter(Filters.ILLAGER), "filter_illager");
        this.filterRaider = blocks.add(new BlockEntityFilter(Filters.RAIDER), "filter_raider");
        this.filterHostile = blocks.add(new BlockEntityFilter(Filters.HOSTILE), "filter_hostile");
        this.filterAnimal = blocks.add(new BlockEntityFilter(Filters.ANIMAL), "filter_animal");
        this.filterBaby = blocks.add(new BlockEntityFilter(Filters.BABY), "filter_child");
        this.filterPet = blocks.add(new BlockEntityFilter(Filters.PET), "filter_pet");
        this.filterSlime = blocks.add(new BlockEntityFilter(Filters.SLIME), "filter_slime");
        this.filterVillager = blocks.add(new BlockEntityFilter(Filters.VILLAGER), "filter_villager");
        this.filterFireImmune = blocks.add(new BlockEntityFilter(Filters.FIRE_IMMUNE), "filter_fire_immune");
        this.filterGolem = blocks.add(new BlockEntityFilter(Filters.GOLEM), "filter_golem");
        this.filterWater = blocks.add(new BlockEntityFilter(Filters.WATER), "filter_water");
        this.filterNamed = blocks.add(new BlockEntityFilter(Filters.NAMED), "filter_named");
        this.filterFreezeImmune = blocks.add(new BlockEntityFilter(Filters.FREEZE_IMMUNE), "filter_freeze_immune");
        this.filterEquipment = blocks.add(new BlockEntityFilter(Filters.EQUIPMENT), "filter_equipment");
        this.filterPassenger = blocks.add(new BlockEntityFilter(Filters.PASSENGER), "filter_passenger");
        this.redstoneRandomiser = blocks.add(new BlockRedstoneRandomizer(), "redstone_randomizer");
        this.shieldedRedstone = blocks.add(new BlockShieldedRedstone(), "shielded_redstone");

        this.portalCharm = registry.items.add(new ItemCharm(), "charm_portal");
        this.sleepCharm = registry.items.add(new ItemCharmTicking(CharmEffects::sleepCharmTick), "charm_sleep");
        this.wardingCharm = registry.items.add(new ItemCharmTicking(CharmEffects::wardingCharmTick), "charm_warding");
        this.enchantingTome = registry.items.add(new ItemTome().withUserEffect(TomeEffects.RESET_ENCHANTMENT_SEED), "tome_enchanting");
        this.galacticTome = registry.items.add(new ItemTomeFont(TextHelper.FONT_ALT), "tome_sga");
        this.illagerTome = registry.items.add(new ItemTomeFont(TextHelper.FONT_ILLAGER), "tome_illager");
        this.shadowTome = registry.items.add(new ItemTome().withBlockEffect(TomeEffects.HIDE_BLOCK).withEntityEffect(TomeEffects.HIDE_ENTITY), "tome_shadows");

        registry.trades.addRareWanderingTrade(new VillagerSells(this.portalCharm, 8, 1, 1));
        registry.trades.addRareWanderingTrade(new VillagerSells(this.sleepCharm, 8, 1, 1));
        registry.trades.addRareWanderingTrade(new VillagerSells(() -> new ItemStack(this.vectorPlateUltra, 4), 8, 1, 1));
        registry.trades.addRareWanderingTrade(new VillagerSells(() -> new ItemStack(this.playerDamagePlate), 16, 1, 1));
        registry.trades.addRareWanderingTrade(new VillagerSells(this.enchantingTome, 16, 1, 1));
        registry.trades.addRareWanderingTrade(new VillagerSells(this.shadowTome, 16, 1, 1));

        if (Services.PLATFORM.isModLoaded("runelic")) {

            this.runelicTome = registry.items.add(new ItemTomeFont(new ResourceLocation("runelic", "runelic")), "tome_runelic");
            registry.trades.addRareWanderingTrade(new VillagerSells(this.runelicTome, 8, 1, 1));
        }

        if (Services.PLATFORM.isModLoaded("pigpen")) {

            this.pigpenTome = registry.items.add(new ItemTomeFont(new ResourceLocation("pigpen", "pigpen")), "tome_pigpen");
            registry.trades.addRareWanderingTrade(new VillagerSells(this.pigpenTome, 8, 1, 1));
        }

        if (Services.PLATFORM.isModLoaded("unown")) {

            this.unownTome = registry.items.add(new ItemTomeFont(new ResourceLocation("unown", "unown")), "tome_unown");
            registry.trades.addRareWanderingTrade(new VillagerSells(this.unownTome, 8, 1, 1));
        }

        this.registry.init();
    }

    private void onItemRegistered(ResourceLocation id, Item item) {

        this.tooltipCache.put(item, new TranslatableComponent("tooltip." + id.getNamespace() + "." + id.getPath()).withStyle(ChatFormatting.DARK_GRAY));
    }

    private void onBlockRegistered(ResourceLocation id, Block block) {

        if (block instanceof IItemBlockProvider provider) {

            final BlockItem itemBlock = provider.createItemBlock(block);

            if (itemBlock != null) {

                this.registry.items.add(itemBlock, id);
            }
        }

        else {

            this.registry.items.add(new BlockItem(block, new Item.Properties()), id);
        }
    }

    private Block getVectorPlateUltra() {

        return this.vectorPlateUltra;
    }
}