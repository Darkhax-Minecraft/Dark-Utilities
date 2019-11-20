package net.darkhax.darkutils.features.enderhopper;

import java.util.List;

import net.darkhax.bookshelf.Bookshelf;
import net.darkhax.bookshelf.util.InventoryUtils;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.IItemHandler;

public class TileEntityEnderHopper extends TileEntity implements ITickableTileEntity {
    
    private BlockPos lastPos;
    protected AxisAlignedBB collectionBounds;
    
    public TileEntityEnderHopper() {
        
        super(DarkUtils.content.tileEnderHopper);
    }
    
    public TileEntityEnderHopper(TileEntityType<?> tileEntityTypeIn) {
        
        super(tileEntityTypeIn);
    }
    
    @Override
    public void tick () {
        
        // Exit early if the block can not function.
        if (this.isRemoved() || this.world == null || this.pos == null || this.pos == BlockPos.ZERO) {
            
            return;
        }
        
        // Update the last position if position has changed.
        if (this.lastPos == null || this.lastPos == BlockPos.ZERO || this.lastPos != this.pos) {
            
            this.lastPos = this.pos;
            this.collectionBounds = new AxisAlignedBB(this.pos.getX() - 5d, this.pos.getY() - 5d, this.pos.getZ() - 5d, this.pos.getX() + 5d, this.getPos().getY() + 5d, this.getPos().getZ() + 5d);
        }
        
        if (this.world.isRemote) {
            
            return;
        }
        
        // Get block state, don't use the cached method.
        final BlockState state = this.world.getBlockState(this.pos);
        
        // Ensure the block is enabled
        if (state.get(BlockStateProperties.ENABLED) && this.collectionBounds != null) {
            
            final Direction accessingFace = state.get(BlockStateProperties.FACING);
            final BlockPos invPos = this.getPos().offset(accessingFace.getOpposite());
            final IItemHandler inventory = InventoryUtils.getInventory(this.world, invPos, accessingFace);
            
            // If the inventory exists, start the process.
            if (inventory != null) {
                
                // Get all items that the ender hopper can interract with.
                final List<ItemEntity> itemEntities = this.world.getEntitiesWithinAABB(ItemEntity.class, this.collectionBounds, e -> e.isAlive() && !e.getItem().isEmpty() && !e.cannotPickup());
                
                for (final ItemEntity itemEntity : itemEntities) {
                    
                    // Create a simulated stack of what to insert.
                    final ItemStack pickupStack = itemEntity.getItem().copy().split(1);
                    
                    // Iterate every valid slot of the inventory
                    for (int slot = 0; slot < inventory.getSlots(); slot++) {
                        
                        // Check if the simulated insert stack can be accepted into the
                        // inventory.
                        if (inventory.isItemValid(slot, pickupStack) && inventory.insertItem(slot, pickupStack, true).getCount() != pickupStack.getCount()) {
                            
                            // Actually split the picked up stack so it can be legitimately
                            // inserted.
                            final ItemStack actualPickupStack = itemEntity.getItem().split(1);
                            
                            // Insert the legitimate stack.
                            final ItemStack remaining = inventory.insertItem(slot, actualPickupStack, false);
                            
                            // If there are any leftover items, spawn them on the ground.
                            if (!remaining.isEmpty()) {
                                
                                final ItemEntity item = new ItemEntity(EntityType.ITEM, this.world);
                                item.setItem(remaining);
                                item.setPosition(this.pos.getX() + 0.5f, this.pos.getY() + 0.5f, this.pos.getZ() + 0.5f);
                                item.lifespan = remaining.getEntityLifespan(this.world);
                                this.world.addEntity(item);
                            }
                            
                            if (this.world instanceof ServerWorld) {
                                
                                ((ServerWorld) this.world).spawnParticle(ParticleTypes.PORTAL, itemEntity.posX, itemEntity.posY, itemEntity.posZ, 5, getOffset(), getOffset(), getOffset(), 0.01);
                            }
                            
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private static float getOffset () {
        
        return nextFloat(-0.2f, 0.2f);
    }
    
    private static float nextFloat (float min, float max) {
        
        return min + Bookshelf.RANDOM.nextFloat() * (max - min);
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        
        return collectionBounds;
    }
}