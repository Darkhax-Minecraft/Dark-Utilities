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
import net.minecraft.item.crafting.IRecipe;
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
    
    private ITextComponent customName;
    private int slimePoints = 0;
    
    private Direction sideToFace = Direction.SOUTH;
    private double slimeHeightOffset = 0.2f;
    private float squishFactor = 0f;
    private float prevSquishFactor = 0f;
    private float squishAmount = 0f;
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
        
        if (player != null) {
            
            final double distX = player.posX - (this.pos.getX() + 0.5F);
            final double distZ = player.posZ - (this.pos.getZ() + 0.5F);
            final double angle = MathHelper.atan2(distX, distZ) * 180.0 / Math.PI;
            this.sideToFace = Direction.fromAngle(angle + 180).getOpposite();
            
            if (this.slimeHeightOffset < 0.85) {
                
                this.slimeHeightOffset = Math.min(this.slimeHeightOffset + 0.05, 0.85);
            }
        }
        
        else {
            
            if (this.slimeHeightOffset > 0.20) {
                
                this.slimeHeightOffset = Math.max(this.slimeHeightOffset - 0.03, 0.2);
            }
        }
        
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        this.squishAmount *= 0.55f;
    }
    
    public SlimeEntity getContainedSlime (World world) {
        
        if (this.entity == null) {
            
            final Block block = world.getBlockState(this.pos).getBlock();
            
            if (block instanceof BlockSlimeCrucible) {
                
                this.entity = ((BlockSlimeCrucible) block).getCrucibleType().createSlime(world);
            }
        }
        
        return this.entity;
    }
    
    public Direction getSideToFace () {
        
        return this.sideToFace;
    }
    
    public double getEntityHeightOffset () {
        
        return this.slimeHeightOffset;
    }
    
    public int getContainedSlimePoints () {
        
        return this.slimePoints;
    }
    
    public static int getSlimePointsForItem (World world, ItemStack inputStack, Block crucibleBlock) {
        
        for (final IRecipe<?> recipeCandidate : WorldUtils.getRecipes(DarkUtils.content.recipeTypeSlimeFood, world.getRecipeManager()).values()) {
            
            if (recipeCandidate instanceof RecipeSlimeFood) {
                
                final RecipeSlimeFood slimeRecipe = (RecipeSlimeFood) recipeCandidate;
                
                if (slimeRecipe.isValid(inputStack, crucibleBlock)) {
                    
                    return slimeRecipe.getSlimePoints();
                }
            }
        }
        
        return MathHelper.floor(ComposterBlock.CHANCES.getOrDefault(inputStack.getItem(), 0.0f) * 5f);
    }
    
    public float getSquishFactor () {
        
        return this.squishFactor;
    }
    
    public float getPrevSquishFactor () {
        
        return this.prevSquishFactor;
    }
    
    public float getSquishAmount () {
        
        return this.squishAmount;
    }
    
    public void setSquishAmount (float amount) {
        
        this.squishAmount = amount;
    }
    
    public void setSlimePoints (int newPoints) {
        
        this.slimePoints = Math.min(newPoints, this.getCrucibleType().getMaxSlimePoints());
    }
    
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
    
    public void setCustomName (ITextComponent name) {
        
        this.customName = name;
    }
    
    @Override
    public ITextComponent getCustomName () {
        
        return this.customName;
    }
}