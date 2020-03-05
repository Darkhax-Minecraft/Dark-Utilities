package net.darkhax.darkutils;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.darkutils.features.charms.CharmEffects;
import net.darkhax.darkutils.features.charms.ItemCharm;
import net.darkhax.darkutils.features.dust.DustDispensorBehaviour;
import net.darkhax.darkutils.features.dust.DustHandler;
import net.darkhax.darkutils.features.dust.RecipeDustChange;
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
import net.darkhax.darkutils.features.glass.BlockSoulGlass;
import net.darkhax.darkutils.features.grates.BlockItemGrate;
import net.darkhax.darkutils.features.redstone.BlockRedstoneRandomizer;
import net.darkhax.darkutils.features.redstone.BlockShieldedRedstone;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.common.MinecraftForge;

public class Content {
    
    /*
     * RECIPE TYPES
     */
    public final IRecipeType<RecipeDustChange> recipeTypeDustChange;
    
    /**
     * RECIPE SERIALIZERS
     */
    public final IRecipeSerializer<RecipeDustChange> recipeSerializerDustChange;
    
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
    public final Block soulGlass;
    
    /**
     * ITEMS
     */
    public final Item dustPurify;
    public final Item dustFiendish;
    public final Item dustCorrupt;
    
    public final Item sleepCharm;
    public final Item portalCharm;
    public final Item experienceCharm;
    public final Item gluttonyCharm;
    
    /**
     * TILE ENTITIES
     */
    public final TileEntityType<TileEntityTickingEffect> tileTickingEffect;
    public final TileEntityType<TileEntityEnderHopper> tileEnderHopper;
    
    /**
     * STATS
     */
    public final ResourceLocation statSlimeCrucibleInteract;
    public final ResourceLocation statSlimeCrucibleItemsCrafted;
    public final ResourceLocation statSlimeCrucibleFeed;
    
    public Content(RegistryHelper registry) {
        
        // Recipes
        this.recipeTypeDustChange = registry.registerRecipeType("dust_change");
        
        this.recipeSerializerDustChange = registry.registerRecipeSerializer(RecipeDustChange.SERIALIZER, "dust_change");
        
        // Blocks
        this.blankPlate = registry.registerBlock(new BlockFlatTile(), "blank_plate");
        
        this.vectorPlate = registry.registerBlock(new BlockFlatTileRotating(TileEffects.PUSH_WEAK), "vector_plate");
        this.exportPlate = registry.registerBlock(new BlockFlatTileRotatingTicking(TileEffects.PUSH_WEAK, TileEffects.EXPORT_WEAK, 10), "export_plate");
        this.importPlate = registry.registerBlock(new BlockFlatTileRotating(TileEffects.IMPORT_WEAK), "import_plate");
        
        this.vectorPlateFast = registry.registerBlock(new BlockFlatTileRotating(TileEffects.PUSH_NORMAL), "vector_plate_fast");
        this.exportPlateFast = registry.registerBlock(new BlockFlatTileRotatingTicking(TileEffects.PUSH_NORMAL, TileEffects.EXPORT_NORMAL, 10), "export_plate_fast");
        this.importPlateFast = registry.registerBlock(new BlockFlatTileRotating(TileEffects.IMPORT_NORMAL), "import_plate_fast");
        
        this.vectorPlateHyper = registry.registerBlock(new BlockFlatTileRotating(TileEffects.PUSH_STRONG), "vector_plate_extreme");
        this.exportPlateHyper = registry.registerBlock(new BlockFlatTileRotatingTicking(TileEffects.PUSH_STRONG, TileEffects.EXPORT_STRONG, 10), "export_plate_extreme");
        this.importPlateHyper = registry.registerBlock(new BlockFlatTileRotating(TileEffects.IMPORT_STRONG), "import_plate_extreme");
        
        this.runeDamage = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_DAMAGE), "rune_damage");
        this.runeDamagePlayer = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_DAMAGE_PLAYER), "rune_damage_player");
        this.runePoison = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_POISON), "rune_poison");
        this.runeWeakness = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_WEAKNESS), "rune_weakness");
        this.runeSlowness = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_SLOWNESS), "rune_slowness");
        this.runeWither = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_WITHER), "rune_wither");
        this.runeFire = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_FIRE), "rune_fire");
        this.runeFatigue = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_FATIGUE), "rune_fatigue");
        this.runeGlowing = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_GLOWING), "rune_glowing");
        this.runeHunger = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_HUNGER), "rune_hunger");
        this.runeBlindness = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_BLINDNESS), "rune_blindness");
        this.runeNausea = registry.registerBlock(new BlockFlatTile(TileEffects.RUNE_NAUSEA), "rune_nausea");
        
        this.anchorPlate = registry.registerBlock(new BlockFlatTileRotating(new CollisionEffectAnchor()), "anchor_plate");
        
        this.filterPlayer = registry.registerBlock(new BlockFilter(Filters::filterPlayer), "filter_player");
        this.filterUndead = registry.registerBlock(new BlockFilter(Filters::filterUndead), "filter_undead");
        this.filterArthropod = registry.registerBlock(new BlockFilter(Filters::filterArthropod), "filter_arthropod");
        this.filterIllager = registry.registerBlock(new BlockFilter(Filters::filterIllager), "filter_illager");
        this.filterRaid = registry.registerBlock(new BlockFilter(Filters::filterRaid), "filter_raid");
        this.filterHostile = registry.registerBlock(new BlockFilter(Filters::filterHostile), "filter_hostile");
        this.filterAnimal = registry.registerBlock(new BlockFilter(Filters::filterAnimal), "filter_animal");
        this.filterChild = registry.registerBlock(new BlockFilter(Filters::filterBaby), "filter_child");
        this.filterPet = registry.registerBlock(new BlockFilter(Filters::filterPet), "filter_pet");
        this.filterSlime = registry.registerBlock(new BlockFilter(Filters::filterSlime), "filter_slime");
        this.filterBoss = registry.registerBlock(new BlockFilter(Filters::filterBoss), "filter_boss");
        this.filterVillager = registry.registerBlock(new BlockFilter(Filters::filterVillager), "filter_villager");
        this.filterFireImmune = registry.registerBlock(new BlockFilter(Filters::filterFireImmune), "filter_fire_immune");
        this.filterExplosionImmune = registry.registerBlock(new BlockFilter(Filters::filterExplosionImmune), "filter_explosion_immune");
        this.filterGolem = registry.registerBlock(new BlockFilter(Filters::filterGolem), "filter_golem");
        this.filterWater = registry.registerBlock(new BlockFilter(Filters::filterWater), "filter_water");
        this.filterNamed = registry.registerBlock(new BlockFilter(Filters::filterNamed), "filter_named");
        
        this.itemGrate = registry.registerBlock(new BlockItemGrate(Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), "item_grate");
        
        this.enderHopper = registry.registerBlock(new BlockEnderHopper(), "ender_hopper");
        
        this.redstoneRandomizer = registry.registerBlock(new BlockRedstoneRandomizer(), "redstone_randomizer");
        this.shieldedRedstone = registry.registerBlock(new BlockShieldedRedstone(), "shielded_redstone");
        
        this.darkGlass = registry.registerBlock(new BlockDarkGlass(), "dark_glass");
        this.soulGlass = registry.registerBlock(new BlockSoulGlass(), "soul_glass");
        
        // Items
        this.dustPurify = registry.registerItem(new Item(new Item.Properties()), "dust_purify");
        this.dustFiendish = registry.registerItem(new Item(new Item.Properties()), "dust_fiendish");
        this.dustCorrupt = registry.registerItem(new Item(new Item.Properties()), "dust_corrupt");
        MinecraftForge.EVENT_BUS.addListener(DustHandler::onPlayerUseItem);
        
        this.portalCharm = registry.registerItem(new ItemCharm().setTickingEffect(CharmEffects::applyPortalCharmEffect), "charm_portal");
        this.sleepCharm = registry.registerItem(new ItemCharm().setTickingEffect(CharmEffects::applySleepCharmEffect), "charm_sleep");
        this.experienceCharm = registry.registerItem(new ItemCharm().setTickingEffect(CharmEffects::applyExperienceCharmTickEffect).addEvent(CharmEffects::handleExpCharmBlock).addEvent(CharmEffects::handleExpCharmEntity), "charm_experience");
        this.gluttonyCharm = registry.registerItem(new ItemCharm().addEvent(CharmEffects::handleGluttonCharm), "charm_gluttony");
        
        // Tiles
        this.tileTickingEffect = registry.registerTileEntity(TileEntityTickingEffect::new, "ticking_tile", this.exportPlate, this.exportPlateFast, this.exportPlateHyper);
        this.tileEnderHopper = registry.registerTileEntity(TileEntityEnderHopper::new, "ender_hopper", this.enderHopper);
        
        // Dispenser Behaviour
        DispenserBlock.registerDispenseBehavior(this.dustPurify, DustDispensorBehaviour.BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(this.dustFiendish, DustDispensorBehaviour.BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(this.dustCorrupt, DustDispensorBehaviour.BEHAVIOR);
        
        // Stats
        this.statSlimeCrucibleInteract = registry.registerStat("slime_crucible_interact");
        this.statSlimeCrucibleItemsCrafted = registry.registerStat("slime_crucible_items_crafted");
        this.statSlimeCrucibleFeed = registry.registerStat("slime_crucible_fed");
        
        // Wandering Trades
        registry.addBasicWanderingTrade(new BasicTrade(24, new ItemStack(this.dustFiendish, 8), 9, 5));
        registry.addBasicWanderingTrade(new BasicTrade(24, new ItemStack(this.dustCorrupt, 8), 9, 5));
        registry.addBasicWanderingTrade(new BasicTrade(24, new ItemStack(this.dustPurify, 8), 9, 5));
        
        registry.addRareWanderingTrade(new BasicTrade(30, new ItemStack(this.portalCharm), 1, 30));
        registry.addRareWanderingTrade(new BasicTrade(30, new ItemStack(this.sleepCharm), 1, 30));
        registry.addRareWanderingTrade(new BasicTrade(30, new ItemStack(this.gluttonyCharm), 1, 30));
        registry.addRareWanderingTrade(new BasicTrade(30, new ItemStack(this.experienceCharm), 1, 30));
        
        // Villager Trades
        registry.registerVillagerTrade(VillagerProfession.CLERIC, 3, new BasicTrade(12, new ItemStack(this.dustPurify, 8), 9, 5));
    }
}