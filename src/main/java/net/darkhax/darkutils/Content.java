package net.darkhax.darkutils;

import java.util.function.Supplier;

import com.mojang.datafixers.types.Type;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.darkutils.features.charms.CharmEffects;
import net.darkhax.darkutils.features.charms.ItemCharm;
import net.darkhax.darkutils.features.enderhopper.BlockEnderHopper;
import net.darkhax.darkutils.features.enderhopper.TileEntityEnderHopper;
import net.darkhax.darkutils.features.filters.BlockFilter;
import net.darkhax.darkutils.features.filters.Filters;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTileRotating;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTileRotatingTicking;
import net.darkhax.darkutils.features.flatblocks.TileEffects;
import net.darkhax.darkutils.features.flatblocks.TileEntityTickingEffect;
import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffectAnchor;
import net.darkhax.darkutils.features.glass.BlockDarkGlass;
import net.darkhax.darkutils.features.grates.BlockItemGrate;
import net.darkhax.darkutils.features.redstone.BlockRedstoneRandomizer;
import net.darkhax.darkutils.features.redstone.BlockShieldedRedstone;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.common.BasicTrade;

public class Content {
    
    /**
     * BLOCKS
     */
    public final Block blankPlate;
    
    public final Block vectorPlate;
    public final Block exportPlate;
    public final Block importPlate;
    
    public final Block vectorPlateFast;
    public final Block exportPlateFast;
    public final Block importPlateFast;
    
    public final Block vectorPlateHyper;
    public final Block exportPlateHyper;
    public final Block importPlateHyper;
    
    public final Block runeDamage;
    public final Block runeDamagePlayer;
    public final Block runePoison;
    public final Block runeWeakness;
    public final Block runeSlowness;
    public final Block runeWither;
    public final Block runeFire;
    public final Block runeFatigue;
    public final Block runeGlowing;
    public final Block runeHunger;
    public final Block runeBlindness;
    public final Block runeNausea;
    
    public final Block anchorPlate;
    
    public final Block filterPlayer;
    public final Block filterUndead;
    public final Block filterArthropod;
    public final Block filterIllager;
    public final Block filterRaid;
    public final Block filterHostile;
    public final Block filterAnimal;
    public final Block filterChild;
    public final Block filterPet;
    public final Block filterSlime;
    public final Block filterBoss;
    public final Block filterVillager;
    public final Block filterFireImmune;
    public final Block filterExplosionImmune;
    public final Block filterGolem;
    public final Block filterWater;
    public final Block filterNamed;
    
    public final Block itemGrate;
    
    public final Block enderHopper;
    
    public final Block redstoneRandomizer;
    public final Block shieldedRedstone;
    
    public final Block darkGlass;
    
    /**
     * ITEMS
     */
    public final Item sleepCharm;
    public final Item portalCharm;
    public final Item experienceCharm;
    public final Item gluttonyCharm;
    
    public final Item bookGalactic;
    public final Item bookRunelic;
    public final Item bookRestore;
    
    /**
     * TILE ENTITIES
     */
    public final TileEntityType<TileEntityTickingEffect> tileTickingEffect;
    public final TileEntityType<TileEntityEnderHopper> tileEnderHopper;
    
    public Content(RegistryHelper registry) {
        
        // Recipes
        // this.recipeTypeDustChange = registry.registerRecipeType("dust_change");
        // this.recipeSerializerDustChange =
        // registry.registerRecipeSerializer(RecipeDustChange.SERIALIZER, "dust_change");
        
        // Blocks
        this.blankPlate = registry.blocks.register(new BlockFlatTile(), "blank_plate");
        
        this.vectorPlate = registry.blocks.register(new BlockFlatTileRotating(TileEffects.PUSH_WEAK), "vector_plate");
        this.exportPlate = registry.blocks.register(new BlockFlatTileRotatingTicking(TileEffects.PUSH_WEAK, TileEffects.EXPORT_WEAK, 10), "export_plate");
        this.importPlate = registry.blocks.register(new BlockFlatTileRotating(TileEffects.IMPORT_WEAK), "import_plate");
        
        this.vectorPlateFast = registry.blocks.register(new BlockFlatTileRotating(TileEffects.PUSH_NORMAL), "vector_plate_fast");
        this.exportPlateFast = registry.blocks.register(new BlockFlatTileRotatingTicking(TileEffects.PUSH_NORMAL, TileEffects.EXPORT_NORMAL, 10), "export_plate_fast");
        this.importPlateFast = registry.blocks.register(new BlockFlatTileRotating(TileEffects.IMPORT_NORMAL), "import_plate_fast");
        
        this.vectorPlateHyper = registry.blocks.register(new BlockFlatTileRotating(TileEffects.PUSH_STRONG), "vector_plate_extreme");
        this.exportPlateHyper = registry.blocks.register(new BlockFlatTileRotatingTicking(TileEffects.PUSH_STRONG, TileEffects.EXPORT_STRONG, 10), "export_plate_extreme");
        this.importPlateHyper = registry.blocks.register(new BlockFlatTileRotating(TileEffects.IMPORT_STRONG), "import_plate_extreme");
        
        this.runeDamage = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_DAMAGE), "rune_damage");
        this.runeDamagePlayer = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_DAMAGE_PLAYER), "rune_damage_player");
        this.runePoison = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_POISON), "rune_poison");
        this.runeWeakness = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_WEAKNESS), "rune_weakness");
        this.runeSlowness = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_SLOWNESS), "rune_slowness");
        this.runeWither = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_WITHER), "rune_wither");
        this.runeFire = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_FIRE), "rune_fire");
        this.runeFatigue = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_FATIGUE), "rune_fatigue");
        this.runeGlowing = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_GLOWING), "rune_glowing");
        this.runeHunger = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_HUNGER), "rune_hunger");
        this.runeBlindness = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_BLINDNESS), "rune_blindness");
        this.runeNausea = registry.blocks.register(new BlockFlatTile(TileEffects.RUNE_NAUSEA), "rune_nausea");
        
        this.anchorPlate = registry.blocks.register(new BlockFlatTileRotating(new CollisionEffectAnchor()), "anchor_plate");
        
        this.filterPlayer = registry.blocks.register(new BlockFilter(Filters::filterPlayer), "filter_player");
        this.filterUndead = registry.blocks.register(new BlockFilter(Filters::filterUndead), "filter_undead");
        this.filterArthropod = registry.blocks.register(new BlockFilter(Filters::filterArthropod), "filter_arthropod");
        this.filterIllager = registry.blocks.register(new BlockFilter(Filters::filterIllager), "filter_illager");
        this.filterRaid = registry.blocks.register(new BlockFilter(Filters::filterRaid), "filter_raid");
        this.filterHostile = registry.blocks.register(new BlockFilter(Filters::filterHostile), "filter_hostile");
        this.filterAnimal = registry.blocks.register(new BlockFilter(Filters::filterAnimal), "filter_animal");
        this.filterChild = registry.blocks.register(new BlockFilter(Filters::filterBaby), "filter_child");
        this.filterPet = registry.blocks.register(new BlockFilter(Filters::filterPet), "filter_pet");
        this.filterSlime = registry.blocks.register(new BlockFilter(Filters::filterSlime), "filter_slime");
        this.filterBoss = registry.blocks.register(new BlockFilter(Filters::filterBoss), "filter_boss");
        this.filterVillager = registry.blocks.register(new BlockFilter(Filters::filterVillager), "filter_villager");
        this.filterFireImmune = registry.blocks.register(new BlockFilter(Filters::filterFireImmune), "filter_fire_immune");
        this.filterExplosionImmune = registry.blocks.register(new BlockFilter(Filters::filterExplosionImmune), "filter_explosion_immune");
        this.filterGolem = registry.blocks.register(new BlockFilter(Filters::filterGolem), "filter_golem");
        this.filterWater = registry.blocks.register(new BlockFilter(Filters::filterWater), "filter_water");
        this.filterNamed = registry.blocks.register(new BlockFilter(Filters::filterNamed), "filter_named");
        
        this.itemGrate = registry.blocks.register(new BlockItemGrate(Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), "item_grate");
        
        this.enderHopper = registry.blocks.register(new BlockEnderHopper(), "ender_hopper");
        
        this.redstoneRandomizer = registry.blocks.register(new BlockRedstoneRandomizer(), "redstone_randomizer");
        this.shieldedRedstone = registry.blocks.register(new BlockShieldedRedstone(), "shielded_redstone");
        
        this.darkGlass = registry.blocks.register(new BlockDarkGlass(), "dark_glass");
        
        this.portalCharm = registry.items.register(new ItemCharm().setTickingEffect(CharmEffects::applyPortalCharmEffect), "charm_portal");
        this.sleepCharm = registry.items.register(new ItemCharm().setTickingEffect(CharmEffects::applySleepCharmEffect), "charm_sleep");
        this.experienceCharm = registry.items.register(new ItemCharm().setTickingEffect(CharmEffects::applyExperienceCharmTickEffect).addEvent(CharmEffects::handleExpCharmBlock).addEvent(CharmEffects::handleExpCharmEntity), "charm_experience");
        this.gluttonyCharm = registry.items.register(new ItemCharm().addEvent(CharmEffects::handleGluttonCharm), "charm_gluttony");
        
        this.bookGalactic = registry.items.register(new Item(new Item.Properties().rarity(Rarity.UNCOMMON)), "book_galactic");
        this.bookRunelic = registry.items.register(new Item(new Item.Properties().rarity(Rarity.UNCOMMON)), "book_runelic");
        this.bookRestore = registry.items.register(new Item(new Item.Properties().rarity(Rarity.UNCOMMON)), "book_restore");
        this.registerItemPattern(registry, "vector_plate", Rarity.UNCOMMON);
        
        // Tiles
        this.tileTickingEffect = register("ticking_tile", TileEntityTickingEffect::new, this.exportPlate, this.exportPlateFast, this.exportPlateHyper);
        registry.tileEntities.register(this.tileTickingEffect);
        
        this.tileEnderHopper = register("ender_hopper", TileEntityEnderHopper::new, this.enderHopper);
        registry.tileEntities.register(this.tileEnderHopper);
        
        // Villager Trades
        registry.trades.addBasicWanderingTrade(new BasicTrade(10, new ItemStack(this.bookGalactic), 5, 30, 1.3f));
        registry.trades.addBasicWanderingTrade(new BasicTrade(10, new ItemStack(this.bookRunelic), 5, 30, 1.3f));
        registry.trades.addRareWanderingTrade(new BasicTrade(20, new ItemStack(this.bookRestore), 2, 30, 1.7f));
    }
    
    @Deprecated // Hopefully forge will do something with this.
    private static <T extends TileEntity> TileEntityType<T> register (String key, Supplier<? extends T> factoryIn, Block... validBlocks) {
        
        final Type<?> type = Util.attemptDataFix(TypeReferences.BLOCK_ENTITY, "darkutils:" + key);
        final TileEntityType.Builder<T> builder = TileEntityType.Builder.create(factoryIn, validBlocks);
        final TileEntityType<T> tileType = builder.build(type);
        tileType.setRegistryName("darkutils", key);
        return tileType;
    }
    
    /**
     * TODO Remove in 1.17
     */
    public void registerItemPattern (RegistryHelper registry, String id, Rarity rarity) {
        
        final BannerPattern pattern = registry.banners.registerPattern(id, true);
        registry.items.register(new BannerPatternItem(pattern, new Item.Properties().maxStackSize(1).rarity(rarity)), id + "_banner_pattern");
    }
}