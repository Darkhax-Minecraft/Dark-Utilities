package net.darkhax.darkutils;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.darkutils.features.dust.DustDispensorBehaviour;
import net.darkhax.darkutils.features.dust.RecipeDustChange;
import net.darkhax.darkutils.features.filters.BlockFilter;
import net.darkhax.darkutils.features.filters.Filters;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTileRotating;
import net.darkhax.darkutils.features.flatblocks.BlockFlatTileRotatingTicking;
import net.darkhax.darkutils.features.flatblocks.TileEffects;
import net.darkhax.darkutils.features.flatblocks.TileEntityTickingEffect;
import net.darkhax.darkutils.features.slimecrucible.BlockSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.ContainerSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.RecipeSlimeCrafting;
import net.darkhax.darkutils.features.slimecrucible.RecipeSlimeFood;
import net.darkhax.darkutils.features.slimecrucible.SlimeCrucibleType;
import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;
import net.darkhax.darkutils.features.spawnitems.ItemMobSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;

public class Content {
    
    /*
     * RECIPE TYPES
     */
    public final IRecipeType<RecipeSlimeFood> recipeTypeSlimeFood;
    public final IRecipeType<RecipeSlimeCrafting> recipeTypeSlimeCrafting;
    public final IRecipeType<RecipeDustChange> recipeTypeDustChange;
    
    /**
     * RECIPE SERIALIZERS
     */
    public final IRecipeSerializer<RecipeSlimeFood> recipeSerializerSlimeFood;
    public final IRecipeSerializer<RecipeSlimeCrafting> recipeSerializerSlimeCrafting;
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
    
    public final Block slimeCrucibleGreen;
    public final Block slimeCrucibleMagma;
    
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
    
    /**
     * ITEMS
     */
    public final Item slimeEgg;
    public final Item magmaEgg;
    
    public final Item dustPurify;
    public final Item dustFiendish;
    public final Item dustCorrupt;
    
    /**
     * TILE ENTITIES
     */
    public final TileEntityType<TileEntityTickingEffect> tileTickingEffect;
    public final TileEntityType<TileEntitySlimeCrucible> tileSlimeCrucible;
    
    /**
     * CONTAINERS
     */
    public final ContainerType<ContainerSlimeCrucible> containerSlimeCrucible;
    
    public Content(RegistryHelper registry) {
        
        // Recipes
        this.recipeTypeSlimeFood = registry.registerRecipeType("slime_food");
        this.recipeTypeSlimeCrafting = registry.registerRecipeType("slime_crafting");
        this.recipeTypeDustChange = registry.registerRecipeType("dust_change");
        
        this.recipeSerializerSlimeFood = registry.registerRecipeSerializer(RecipeSlimeFood.SERIALIZER, "slime_food");
        this.recipeSerializerSlimeCrafting = registry.registerRecipeSerializer(RecipeSlimeCrafting.SERIALIZER, "slime_crafting");
        this.recipeSerializerDustChange = registry.registerRecipeSerializer(RecipeDustChange.SERIALIZER, "dust_change");
        
        // Blocks
        this.blankPlate = registry.registerBlock(new BlockFlatTile(), "blank_plate");
        
        this.vectorPlate = registry.registerBlock(new BlockFlatTileRotating(TileEffects.PUSH_WEAK), "vector_plate");
        this.exportPlate = registry.registerBlock(new BlockFlatTileRotatingTicking(TileEffects.PUSH_WEAK, TileEffects.EXPORT_INVENTORY, 10), "export_plate");
        this.importPlate = registry.registerBlock(new BlockFlatTileRotating(TileEffects.IMPORT_WEAK), "import_plate");
        
        this.vectorPlateFast = registry.registerBlock(new BlockFlatTileRotating(TileEffects.PUSH_NORMAL), "vector_plate_fast");
        this.exportPlateFast = registry.registerBlock(new BlockFlatTileRotatingTicking(TileEffects.PUSH_NORMAL, TileEffects.EXPORT_INVENTORY, 5), "export_plate_fast");
        this.importPlateFast = registry.registerBlock(new BlockFlatTileRotating(TileEffects.IMPORT_NORMAL), "import_plate_fast");
        
        this.vectorPlateHyper = registry.registerBlock(new BlockFlatTileRotating(TileEffects.PUSH_STRONG), "vector_plate_extreme");
        this.exportPlateHyper = registry.registerBlock(new BlockFlatTileRotatingTicking(TileEffects.PUSH_STRONG, TileEffects.EXPORT_INVENTORY, 1), "export_plate_extreme");
        this.importPlateHyper = registry.registerBlock(new BlockFlatTileRotating(TileEffects.IMPORT_STRONG), "import_plate_extreme");
        
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
        
        this.slimeCrucibleGreen = registry.registerBlock(new BlockSlimeCrucible(Properties.create(Material.CLAY, MaterialColor.GRASS).slipperiness(0.8F).hardnessAndResistance(0.6F).sound(SoundType.SLIME), SlimeCrucibleType.GREEN), "slime_crucible_green");
        this.slimeCrucibleMagma = registry.registerBlock(new BlockSlimeCrucible(Properties.create(Material.ROCK, MaterialColor.NETHERRACK).lightValue(3).hardnessAndResistance(0.5F), SlimeCrucibleType.MAGMA), "slime_crucible_magma");

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
        
        // Items
        this.slimeEgg = registry.registerItem(new ItemMobSpawner(EntityType.SLIME), "egg_slime");
        this.magmaEgg = registry.registerItem(new ItemMobSpawner(EntityType.MAGMA_CUBE), "egg_magma");
        
        this.dustPurify = registry.registerItem(new Item(new Item.Properties()), "dust_purify");
        this.dustFiendish = registry.registerItem(new Item(new Item.Properties()), "dust_fiendish");
        this.dustCorrupt = registry.registerItem(new Item(new Item.Properties()), "dust_corrupt");
        
        // Tiles
        this.tileTickingEffect = registry.registerTileEntity(TileEntityTickingEffect::new, "ticking_tile", this.exportPlate, this.exportPlateFast, this.exportPlateHyper);
        this.tileSlimeCrucible = registry.registerTileEntity(TileEntitySlimeCrucible::new, "slime_crucible", this.slimeCrucibleGreen, this.slimeCrucibleMagma);
        
        // Containers
        this.containerSlimeCrucible = registry.registerContainer(ContainerSlimeCrucible::new, "slime_crucible");
        
        // Dispenser Behaviour
        DispenserBlock.registerDispenseBehavior(this.dustPurify, DustDispensorBehaviour.BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(this.dustFiendish, DustDispensorBehaviour.BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(this.dustCorrupt, DustDispensorBehaviour.BEHAVIOR);
    }
}