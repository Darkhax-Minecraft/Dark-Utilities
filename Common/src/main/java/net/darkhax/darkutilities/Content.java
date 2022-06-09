package net.darkhax.darkutilities;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.bookshelf.api.entity.merchant.trade.VillagerSells;
import net.darkhax.bookshelf.api.registry.IRegistryObject;
import net.darkhax.bookshelf.api.registry.RegistryDataProvider;
import net.darkhax.bookshelf.api.util.TextHelper;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Content extends RegistryDataProvider {

    protected final Map<Item, Component> tooltipCache = new HashMap<>();

    public final IRegistryObject<Block> blankPlate;
    public final IRegistryObject<Block> vectorPlate;
    public final IRegistryObject<Block> vectorPlateFast;
    public final IRegistryObject<Block> vectorPlateExtreme;
    public final IRegistryObject<Block> vectorPlateUltra;
    public final IRegistryObject<Block> damagePlate;
    public final IRegistryObject<Block> playerDamagePlate;
    public final IRegistryObject<Block> flamePlate;
    public final IRegistryObject<Block> slownessPlate;
    public final IRegistryObject<Block> fatiguePlate;
    public final IRegistryObject<Block> darknessPlate;
    public final IRegistryObject<Block> hungerPlate;
    public final IRegistryObject<Block> weaknessPlate;
    public final IRegistryObject<Block> poisonPlate;
    public final IRegistryObject<Block> witherPlate;
    public final IRegistryObject<Block> glowingPlate;
    public final IRegistryObject<Block> levitationPlate;
    public final IRegistryObject<Block> misfortunePlate;
    public final IRegistryObject<Block> slowfallPlate;
    public final IRegistryObject<Block> omenPlate;
    public final IRegistryObject<Block> frostPlate;
    public final IRegistryObject<Block> maimPlate;
    public final IRegistryObject<Block> filterPlayer;
    public final IRegistryObject<Block> filterUndead;
    public final IRegistryObject<Block> filterArthropod;
    public final IRegistryObject<Block> filterIllager;
    public final IRegistryObject<Block> filterRaider;
    public final IRegistryObject<Block> filterHostile;
    public final IRegistryObject<Block> filterAnimal;
    public final IRegistryObject<Block> filterBaby;
    public final IRegistryObject<Block> filterPet;
    public final IRegistryObject<Block> filterSlime;
    public final IRegistryObject<Block> filterVillager;
    public final IRegistryObject<Block> filterFireImmune;
    public final IRegistryObject<Block> filterGolem;
    public final IRegistryObject<Block> filterWater;
    public final IRegistryObject<Block> filterNamed;
    public final IRegistryObject<Block> filterFreezeImmune;
    public final IRegistryObject<Block> filterEquipment;
    public final IRegistryObject<Block> filterPassenger;
    public final IRegistryObject<Block> redstoneRandomiser;
    public final IRegistryObject<Block> shieldedRedstone;

    public final IRegistryObject<Item> portalCharm;
    public final IRegistryObject<Item> sleepCharm;
    public final IRegistryObject<Item> wardingCharm;
    public final IRegistryObject<Item> enchantingTome;
    public final IRegistryObject<Item> galacticTome;
    public final IRegistryObject<Item> illagerTome;
    public final IRegistryObject<Item> shadowTome;

    // Mod Support
    public IRegistryObject<Item> pigpenTome;
    public IRegistryObject<Item> runelicTome;
    public IRegistryObject<Item> unownTome;

    public Content() {

        super(Constants.MOD_ID);
        this.withCreativeTab(this::getCrativeTabIcon);
        this.withAutoItemBlocks();
        this.bindBlockRenderLayers();
        this.items.addRegistryListener(this::createTooltips);

        this.blankPlate = this.createFlatBlock(null, "blank_plate");
        this.vectorPlate = this.createFlatBlockRotatable(FlatTileEffects.PUSH_WEAK, "vector_plate");
        this.vectorPlateFast = this.createFlatBlockRotatable(FlatTileEffects.PUSH_NORMAL, "vector_plate_fast");
        this.vectorPlateExtreme = this.blocks.add(() -> new BlockFlatTileRotatableLightningUpgrade(FlatTileEffects.PUSH_STRONG, this::getVectorPlateUltra), "vector_plate_extreme");
        this.vectorPlateUltra = this.createFlatBlockRotatable(FlatTileEffects.PUSH_ULTRA, "vector_plate_ultra");
        this.damagePlate = this.createFlatBlock(FlatTileEffects.DAMAGE_GENERIC, "damage_plate");
        this.maimPlate = this.createFlatBlock(FlatTileEffects.DAMAGE_MAIM, "damage_plate_maim");
        this.playerDamagePlate = this.createFlatBlock(FlatTileEffects.DAMAGE_PLAYER, "damage_plate_player");
        this.flamePlate = this.createFlatBlock(FlatTileEffects.FLAME, "flame_plate");
        this.slownessPlate = this.createFlatBlock(FlatTileEffects.SLOWNESS, "slowness_plate");
        this.fatiguePlate = this.createFlatBlock(FlatTileEffects.FATIGUE, "fatigue_plate");
        this.darknessPlate = this.createFlatBlock(FlatTileEffects.DARKNESS, "darkness_plate");
        this.hungerPlate = this.createFlatBlock(FlatTileEffects.HUNGER, "hunger_plate");
        this.weaknessPlate = this.createFlatBlock(FlatTileEffects.WEAKNESS, "weakness_plate");
        this.poisonPlate = this.createFlatBlock(FlatTileEffects.POISON, "poison_plate");
        this.witherPlate = this.createFlatBlock(FlatTileEffects.WITHER, "wither_plate");
        this.glowingPlate = this.createFlatBlock(FlatTileEffects.GLOWING, "alert_plate");
        this.levitationPlate = this.createFlatBlock(FlatTileEffects.LEVITATION, "levitation_plate");
        this.misfortunePlate = this.createFlatBlock(FlatTileEffects.UNLUCK, "misfortune_plate");
        this.slowfallPlate = this.createFlatBlock(FlatTileEffects.SLOWFALL, "slowfall_plate");
        this.omenPlate = this.createFlatBlock(FlatTileEffects.OMEN, "omen_plate");
        this.frostPlate = this.createFlatBlock(FlatTileEffects.FROST, "frost_plate");
        this.filterPlayer = this.createFilter(Filters.PLAYER, "filter_player");
        this.filterUndead = this.createFilter(Filters.UNDEAD, "filter_undead");
        this.filterArthropod = this.createFilter(Filters.ARTHROPOD, "filter_arthropod");
        this.filterIllager = this.createFilter(Filters.ILLAGER, "filter_illager");
        this.filterRaider = this.createFilter(Filters.RAIDER, "filter_raider");
        this.filterHostile = this.createFilter(Filters.HOSTILE, "filter_hostile");
        this.filterAnimal = this.createFilter(Filters.ANIMAL, "filter_animal");
        this.filterBaby = this.createFilter(Filters.BABY, "filter_child");
        this.filterPet = this.createFilter(Filters.PET, "filter_pet");
        this.filterSlime = this.createFilter(Filters.SLIME, "filter_slime");
        this.filterVillager = this.createFilter(Filters.VILLAGER, "filter_villager");
        this.filterFireImmune = this.createFilter(Filters.FIRE_IMMUNE, "filter_fire_immune");
        this.filterGolem = this.createFilter(Filters.GOLEM, "filter_golem");
        this.filterWater = this.createFilter(Filters.WATER, "filter_water");
        this.filterNamed = this.createFilter(Filters.NAMED, "filter_named");
        this.filterFreezeImmune = this.createFilter(Filters.FREEZE_IMMUNE, "filter_freeze_immune");
        this.filterEquipment = this.createFilter(Filters.EQUIPMENT, "filter_equipment");
        this.filterPassenger = this.createFilter(Filters.PASSENGER, "filter_passenger");
        this.redstoneRandomiser = this.blocks.add(BlockRedstoneRandomizer::new, "redstone_randomizer");
        this.shieldedRedstone = this.blocks.add(BlockShieldedRedstone::new, "shielded_redstone");

        this.portalCharm = this.items.add(ItemCharm::new, "charm_portal");
        this.sleepCharm = this.items.add(() -> new ItemCharmTicking(CharmEffects::sleepCharmTick), "charm_sleep");
        this.wardingCharm = this.items.add(() -> new ItemCharmTicking(CharmEffects::wardingCharmTick), "charm_warding");
        this.enchantingTome = this.items.add(() -> new ItemTome().withUserEffect(TomeEffects.RESET_ENCHANTMENT_SEED), "tome_enchanting");
        this.galacticTome = this.items.add(() -> new ItemTomeFont(TextHelper.FONT_ALT), "tome_sga");
        this.illagerTome = this.items.add(() -> new ItemTomeFont(TextHelper.FONT_ILLAGER), "tome_illager");
        this.shadowTome = this.items.add(() -> new ItemTome().withBlockEffect(TomeEffects.HIDE_BLOCK).withEntityEffect(TomeEffects.HIDE_ENTITY), "tome_shadows");

        Supplier<? extends ItemLike> test = this.illagerTome;
        this.trades.addRareWanderingTrade(VillagerSells.create(this.portalCharm, 8, 1, 1, 0.05f));
        this.trades.addRareWanderingTrade(VillagerSells.create(this.sleepCharm, 8, 1, 1, 0.05f));
        this.trades.addRareWanderingTrade(new VillagerSells(() -> new ItemStack(this.vectorPlateUltra.get(), 4), 8, 1, 1, 0.05f));
        this.trades.addRareWanderingTrade(new VillagerSells(() -> new ItemStack(this.playerDamagePlate.get()), 16, 1, 1, 0.05f));
        this.trades.addRareWanderingTrade(VillagerSells.create(this.enchantingTome, 16, 1, 1, 0.05f));
        this.trades.addRareWanderingTrade(VillagerSells.create(this.shadowTome, 16, 1, 1, 0.05f));

        if (Services.PLATFORM.isModLoaded("runelic")) {

            this.runelicTome = this.items.add(() -> new ItemTomeFont(new ResourceLocation("runelic", "runelic")), "tome_runelic");
            this.trades.addRareWanderingTrade(VillagerSells.create(this.runelicTome, 8, 1, 1, 0.05f));
        }

        if (Services.PLATFORM.isModLoaded("pigpen")) {

            this.pigpenTome = this.items.add(() -> new ItemTomeFont(new ResourceLocation("pigpen", "pigpen")), "tome_pigpen");
            this.trades.addRareWanderingTrade(VillagerSells.create(this.pigpenTome, 8, 1, 1, 0.05f));
        }

        if (Services.PLATFORM.isModLoaded("unown")) {

            this.unownTome = this.items.add(() -> new ItemTomeFont(new ResourceLocation("unown", "unown")), "tome_unown");
            this.trades.addRareWanderingTrade(VillagerSells.create(this.unownTome, 8, 1, 1, 0.05f));
        }
    }

    private void createTooltips(ResourceLocation id, Item item) {

        this.tooltipCache.put(item, Component.translatable("tooltip." + id.getNamespace() + "." + id.getPath()).withStyle(ChatFormatting.DARK_GRAY));
    }

    private Block getVectorPlateUltra() {

        return this.vectorPlateUltra.get();
    }

    private ItemLike getCrativeTabIcon() {

        return this.vectorPlate.get();
    }

    private IRegistryObject<Block> createFlatBlock(@Nullable BlockFlatTile.CollisionEffect effect, String id) {

        return this.blocks.add(() -> new BlockFlatTile(effect), id);
    }

    private IRegistryObject<Block> createFlatBlockRotatable(@Nullable BlockFlatTile.CollisionEffect effect, String id) {

        return this.blocks.add(() -> new BlockFlatTileRotatable(effect), id);
    }

    private IRegistryObject<Block> createFilter(Predicate<Entity> effect, String id) {

        return this.blocks.add(() -> new BlockEntityFilter(effect), id);
    }
}