package net.darkhax.darkutils.features.slimecrucible;

import javax.annotation.Nullable;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.darkhax.bookshelf.util.WorldUtils;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntitySlimeCrucible extends TileEntityBasicTickable implements INamedContainerProvider, INameable {
    
    /**
     * A custom display name for the slime crucible.
     */
    private ITextComponent customName;
    
    /**
     * The stored amount of slime points.
     */
    private int slimePoints = 0;
    
    /**
     * The direction the slime entity should be facing.
     */
    private Direction sideToFace = Direction.SOUTH;
    
    /**
     * The height offset of the contained slime entity.
     */
    private double slimeHeightOffset = 0.2f;
    
    /**
     * The squish factor of the slime entity.
     */
    private float squishFactor = 0f;
    
    /**
     * The previous squish factor.
     */
    private float prevSquishFactor = 0f;
    
    /**
     * The remaining amount of squish.
     */
    private float squishAmount = 0f;
    
    /**
     * The contained slime entity.
     */
    private SlimeEntity entity;
    
    public TileEntitySlimeCrucible() {
        
        super(DarkUtils.content.tileSlimeCrucible);
    }
    
    @Override
    public void writeNBT (CompoundNBT dataTag) {
        
        dataTag.putInt("SlimePoints", this.slimePoints);
        
        if (this.customName != null) {
            
            dataTag.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
    }
    
    @Override
    public void readNBT (CompoundNBT dataTag) {
        
        this.slimePoints = dataTag.getInt("SlimePoints");
        
        if (dataTag.contains("CustomName", NBT.TAG_STRING)) {
            
            this.customName = ITextComponent.Serializer.fromJson(dataTag.getString("CustomName"));
        }
    }
    
    @Override
    public void onEntityUpdate () {
        
        final BlockPos pos = this.getPos();
        
        final PlayerEntity player = this.world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 4.5, curPlayer -> !curPlayer.isSpectator() && !curPlayer.isInvisible());
        
        // Rotate the slime towards the closest player, and modify the height offset to reveal
        // the slime.
        if (player != null) {
            
            final double distX = player.posX - (this.pos.getX() + 0.5F);
            final double distZ = player.posZ - (this.pos.getZ() + 0.5F);
            final double angle = MathHelper.atan2(distX, distZ) * 180.0 / Math.PI;
            this.sideToFace = Direction.fromAngle(angle + 180).getOpposite();
            
            if (this.slimeHeightOffset < 0.85) {
                
                this.slimeHeightOffset = Math.min(this.slimeHeightOffset + 0.05, 0.85);
            }
        }
        
        // When there are no players in range the slime should hide in the crucble.
        else {
            
            if (this.slimeHeightOffset > 0.20) {
                
                this.slimeHeightOffset = Math.max(this.slimeHeightOffset - 0.03, 0.2);
            }
        }
        
        // Update the squish factor once per logic tick.
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        this.squishAmount *= 0.55f;
    }
    
    /**
     * Gets the contained slime entity. If no entity exists a new one is created.
     * 
     * @param world A world instance which is used to create the entity if it doesn't exist
     *        yet.
     * @return The contained slime entity.
     */
    public SlimeEntity getContainedSlime (World world) {
        
        if (this.entity == null) {
            
            this.entity = this.getCrucibleType().createSlime(world);
        }
        
        return this.entity;
    }
    
    /**
     * Get the direction that the slime is facing.
     * 
     * @return the direction that the slime should be facing.
     */
    public Direction getSideToFace () {
        
        return this.sideToFace;
    }
    
    /**
     * Gets the height offset of the slime.
     * 
     * @return The height offset of the slime.
     */
    public double getEntityHeightOffset () {
        
        return this.slimeHeightOffset;
    }
    
    /**
     * Gets the amount of slime points contained in the tile.
     * 
     * @return The amount of slime points contained in the tile.
     */
    public int getContainedSlimePoints () {
        
        return this.slimePoints;
    }
    
    /**
     * Gets the amount of slime points to be given by an itemstack. If the returned amount is 0
     * or less the input is not valid.
     * 
     * @param world The world instance. Used to access recipes.
     * @param inputStack The ItemStack to test for.
     * @param type The type of slime crucible.
     * @return The amount of points that will be given with the given context.
     */
    public static int getSlimePointsForItem (World world, ItemStack inputStack, SlimeCrucibleType type) {
        
        for (final RecipeSlimeFood slimeRecipe : WorldUtils.getRecipes(DarkUtils.content.recipeTypeSlimeFood, world.getRecipeManager()).values()) {
            
            if (slimeRecipe.isValid(inputStack, type)) {
                
                return slimeRecipe.getSlimePoints();
            }
        }
        
        return MathHelper.floor(ComposterBlock.CHANCES.getOrDefault(inputStack.getItem(), 0.0f) * 5f);
    }
    
    /**
     * Gets the squish factor for the contained slime entity.
     * 
     * @return The squish factor for the contained slime entity.
     */
    public float getSquishFactor () {
        
        return this.squishFactor;
    }
    
    /**
     * Gets the previous squish factor for the contained slime entity.
     * 
     * @return The previous squish factor for the contained slime entity.
     */
    public float getPrevSquishFactor () {
        
        return this.prevSquishFactor;
    }
    
    /**
     * Gets the squish amount for the contained slime entity.
     * 
     * @return The squish amount for the contained slime entity.
     */
    public float getSquishAmount () {
        
        return this.squishAmount;
    }
    
    /**
     * Sets the squish amount for the contained slime entity.
     * 
     * @param amount The new squish amount.
     */
    public void setSquishAmount (float amount) {
        
        this.squishAmount = amount;
    }
    
    /**
     * Adds slime points to the contained amount. This method will not allow you to go over the
     * maximum amount set by the crucible type.
     * 
     * @param points The amount of points to add.
     */
    public void addSlimePoints (int points) {
        
        this.slimePoints = Math.min(this.slimePoints + points, this.getCrucibleType().getMaxSlimePoints());
    }
    
    /**
     * Removes slime points from the contained amount. This method will not go below 0.
     * 
     * @param points The amount of points to remove.
     */
    public void removeSlimePoints (int points) {
        
        this.slimePoints = Math.max(0, this.slimePoints - points);
    }
    
    /**
     * Sets a custom name for the slime.
     * 
     * @param name A custom name for the slime.
     */
    public void setCustomName (ITextComponent name) {
        
        this.customName = name;
    }
    
    /**
     * Gets the crucible type from the attached block. This will be null unless the block is an
     * ISlimeCrucibleBlock.
     * 
     * @return The crucible type of the attached block.
     */
    @Nullable
    public SlimeCrucibleType getCrucibleType () {
        
        final Block block = this.getBlockState().getBlock();
        
        if (block instanceof ISlimeCrucibleBlock) {
            
            return ((ISlimeCrucibleBlock) block).getCrucibleType();
        }
        
        return null;
    }
    
    @Override
    public Container createMenu (int id, PlayerInventory playerInventory, PlayerEntity player) {
        
        return new ContainerSlimeCrucible(id, playerInventory, IWorldPosCallable.of(this.getWorld(), this.getPos()));
    }
    
    @Override
    public ITextComponent getName () {
        
        if (this.hasCustomName()) {
            
            return this.getCustomName();
        }
        
        if (this.getCrucibleType() != null) {
            
            return this.getCrucibleType().getContainerDisplayName(this.getBlockState(), this.getWorld(), this.getPos());
        }
        
        return SlimeCrucibleType.GREEN.getContainerDisplayName(this.getBlockState(), this.getWorld(), this.getPos());
    }
    
    @Override
    public ITextComponent getDisplayName () {
        
        return this.getName();
    }
    
    @Override
    public ITextComponent getCustomName () {
        
        return this.customName;
    }
}