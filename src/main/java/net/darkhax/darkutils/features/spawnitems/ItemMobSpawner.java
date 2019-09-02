package net.darkhax.darkutils.features.spawnitems;

import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemMobSpawner extends Item {
    
    private final EntityType<?> type;
    
    public ItemMobSpawner(EntityType<?> type) {
        
        this(type, new Properties().maxStackSize(1).rarity(Rarity.UNCOMMON));
    }
    
    public ItemMobSpawner(EntityType<?> type, Item.Properties builder) {
        
        super(builder);
        this.type = type;
    }
    
    @Override
    public ActionResultType onItemUse (ItemUseContext context) {
        
        final World world = context.getWorld();
        
        if (!world.isRemote) {
            
            final ItemStack heldItem = context.getItem();
            final BlockPos clickPos = context.getPos();
            final Direction direction = context.getFace();
            final BlockState blockstate = world.getBlockState(clickPos);
            final BlockPos spawnPos = blockstate.getCollisionShape(world, clickPos).isEmpty() ? clickPos : clickPos.offset(direction);
            
            final EntityType<?> spawnType = this.getType(heldItem.getTag());
            
            if (spawnType.spawn(world, heldItem, context.getPlayer(), spawnPos, SpawnReason.SPAWN_EGG, true, !Objects.equals(clickPos, spawnPos) && direction == Direction.UP) != null) {
                
                heldItem.shrink(1);
            }
        }
        
        return ActionResultType.SUCCESS;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick (World world, PlayerEntity player, Hand hand) {
        
        final ItemStack heldItem = player.getHeldItem(hand);
        
        if (world.isRemote) {
            
            return new ActionResult<>(ActionResultType.PASS, heldItem);
        }
        
        else {
            
            final RayTraceResult traceResult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
            
            if (traceResult.getType() != RayTraceResult.Type.BLOCK) {
                
                return new ActionResult<>(ActionResultType.PASS, heldItem);
            }
            
            else {
                
                final BlockRayTraceResult blockTraceResult = (BlockRayTraceResult) traceResult;
                final BlockPos blockpos = blockTraceResult.getPos();
                
                if (!(world.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
                    return new ActionResult<>(ActionResultType.PASS, heldItem);
                }
                
                else if (world.isBlockModifiable(player, blockpos) && player.canPlayerEdit(blockpos, blockTraceResult.getFace(), heldItem)) {
                    
                    final EntityType<?> entitytype = this.getType(heldItem.getTag());
                    
                    if (entitytype.spawn(world, heldItem, player, blockpos, SpawnReason.SPAWN_EGG, false, false) == null) {
                        
                        return new ActionResult<>(ActionResultType.PASS, heldItem);
                    }
                    
                    else {
                        
                        if (!player.abilities.isCreativeMode) {
                            heldItem.shrink(1);
                        }
                        
                        player.addStat(Stats.ITEM_USED.get(this));
                        return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
                    }
                }
                
                else {
                    return new ActionResult<>(ActionResultType.FAIL, heldItem);
                }
            }
        }
    }
    
    public EntityType<?> getType (@Nullable CompoundNBT itemTag) {
        
        if (itemTag != null && itemTag.contains("EntityTag", 10)) {
            
            final CompoundNBT entityTag = itemTag.getCompound("EntityTag");
            
            if (entityTag.contains("id", 8)) {
                
                return EntityType.byKey(entityTag.getString("id")).orElse(this.type);
            }
        }
        
        return this.type;
    }
}