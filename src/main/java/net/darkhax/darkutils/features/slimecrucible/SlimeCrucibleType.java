package net.darkhax.darkutils.features.slimecrucible;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class SlimeCrucibleType {
    
    /**
     * An internal map that holds all known slime types. Entries will be added to this map
     * automatically when they are constructed. To retrieve an entry use
     * {@link #getType(ResourceLocation)}.
     */
    private static final Map<ResourceLocation, SlimeCrucibleType> REGISTRY_MAP = new HashMap<>();
    
    public static final SlimeCrucibleType ALL = new SlimeCrucibleType(new ResourceLocation(DarkUtils.MOD_ID, "all"), 0f, null, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundEvents.ENTITY_GENERIC_EXPLODE);
    public static final SlimeCrucibleType GREEN = new SlimeCrucibleType(new ResourceLocation(DarkUtils.MOD_ID, "green"), 64f, EntityType.SLIME::create, SoundEvents.ENTITY_SLIME_SQUISH, SoundEvents.ENTITY_SLIME_JUMP);
    public static final SlimeCrucibleType MAGMA = new SlimeCrucibleType(new ResourceLocation(DarkUtils.MOD_ID, "magma"), 32f, EntityType.MAGMA_CUBE::create, SoundEvents.ENTITY_MAGMA_CUBE_JUMP, SoundEvents.ENTITY_MAGMA_CUBE_JUMP);
    
    /**
     * Gets the ID of the entity. This is used for serialization of types in recipes.
     */
    private final ResourceLocation id;
    
    /**
     * The sound event to play when an item is crafted by a crucible of the type.
     */
    private final SoundEvent craftingSound;
    
    /**
     * The sound event to play when a slime is fed a food it likes, or whenever the slime is
     * happy.
     */
    private final SoundEvent happySound;
    
    /**
     * The default name for the crucible's container GUI.
     */
    private final ITextComponent containerName;
    
    /**
     * The maximum number of slime that can be held by a crucible.
     */
    private final float maxSlimePoints;
    
    /**
     * A function that is used to build the slime in the crucible. This is primarily used on
     * the client for the tile entity renderer but can safely be used on both sides.
     */
    private final Function<World, SlimeEntity> entityBuilder;
    
    public SlimeCrucibleType(ResourceLocation id, float maxSlimePoints, Function<World, SlimeEntity> entityBuilder, SoundEvent craftingSound, SoundEvent happySound) {
        
        this.id = id;
        this.maxSlimePoints = maxSlimePoints;
        this.entityBuilder = entityBuilder;
        this.craftingSound = craftingSound;
        this.happySound = happySound;
        this.containerName = new TranslationTextComponent("container." + id.getNamespace() + ".slime_crucible." + id.getPath());
        REGISTRY_MAP.put(id, this);
    }
    
    /**
     * Gets the registry name used by the slime type. This should be usable with
     * {@link #getType(ResourceLocation)} to provide the same object.
     * 
     * @return The registry name for the slime type.
     */
    public ResourceLocation getRegistryName () {
        
        return this.id;
    }
    
    /**
     * Creates the slime entity represented by this type. Note that {@link #ALL} is an internal
     * type used for matching and will try to crash if you call this on it.
     * 
     * @param world An instance of the world. The entity will not be spawned into the world,
     *        but is required for constructing entity objects.
     * @return A slime entity that this type represents.
     */
    public SlimeEntity createSlime (World world) {
        
        if (this.entityBuilder == null) {
            
            throw new IllegalArgumentException(this == ALL ? "Someone tried to create an instance of the ALL slime type. This is an unsupported operation." : "The slime type " + this.id + " does not have an entityBuilder. This is not allowed.");
        }
        
        return this.entityBuilder.apply(world);
    }
    
    /**
     * Checks if any of the passed slime types match the current type.
     * 
     * @param otherTypes The other types to check against.
     * @return Whether or not any of them match.
     */
    public boolean matchesAny (SlimeCrucibleType... otherTypes) {
        
        for (final SlimeCrucibleType otherType : otherTypes) {
            
            if (this.matches(otherType)) {
                
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks if the crucible type matches another one. This is used by recipes to make sure
     * the player is using the right one. Be default a type only matches if either type is
     * {@link #ALL} or the types are the same.
     * 
     * @param otherType The other type you want to match.
     * @return Whether or not the types match for crafting purposes.
     */
    public boolean matches (SlimeCrucibleType otherType) {
        
        return this == ALL || otherType == this || otherType == ALL;
    }
    
    /**
     * Looks up a slime type using a registry ID. This is primarily used to deserialize
     * recipes. This will return null if no entry exists.
     * 
     * @param key The registry key.
     * @return The crucible type that was found. This will be null if no entry exists.
     */
    @Nullable
    public static SlimeCrucibleType getType (ResourceLocation key) {
        
        return REGISTRY_MAP.get(key);
    }
    
    /**
     * Gets the sound that is played when the slime crafts something using the slime crucible.
     * 
     * @return The sound used when crafting stuff.
     */
    public SoundEvent getCraftingSound () {
        
        return this.craftingSound;
    }
    
    /**
     * Gets the sound played when a player makes the slime happy. Like when you feed it a food
     * it likes.
     * 
     * @return The sound used when the slime is happy.
     */
    public SoundEvent getHappySound () {
        
        return this.happySound;
    }
    
    /**
     * Gets a function which can be used to spawn a new instance of the slime entity.
     * 
     * @return A function for building instances of the slime entity.
     */
    public Function<World, SlimeEntity> getEntityBuilder () {
        
        return this.entityBuilder;
    }
    
    /**
     * Gets the default display name for the slime type. This is shown in the crafting GUI but
     * can be overwritten by a player if the item has a custom name.
     * 
     * @param state The state of the block.
     * @param world An instance of the world.
     * @param pos The position of the tile entity.
     * @return The default display name for the crafting GUI.
     */
    public ITextComponent getContainerDisplayName (BlockState state, World world, BlockPos pos) {
        
        return this.containerName;
    }
    
    /**
     * Gets the maximum number of slime points that a slime can hold.
     * 
     * @return The maximum number of slime points that a slime can be fed.
     */
    public float getMaxSlimePoints () {
        
        return this.maxSlimePoints;
    }
}