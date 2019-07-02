package net.darkhax.darkutils.features.flatblocks.collision;

import net.darkhax.bookshelf.util.InventoryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class CollisionEffectImport extends CollisionEffectPush {

    private final int insertAmount;

    public CollisionEffectImport (double velocity, int insertAmount) {

        super(velocity);
        this.insertAmount = insertAmount;
    }

    @Override
    public void additionalEffects (BlockState state, World world, BlockPos pos, Entity entity) {

        if (entity instanceof ItemEntity) {

            final ItemEntity itemEntity = (ItemEntity) entity;
            final Direction insertSide = state.get(BlockStateProperties.HORIZONTAL_FACING);
            final IItemHandler inventory = InventoryUtils.getInventory(world, pos.offset(insertSide), insertSide.getOpposite());

            // Check if inventory exists and if the item can be picked up.
            if (inventory != null && !itemEntity.cannotPickup() && !itemEntity.getItem().isEmpty()) {

                // Create a simulated stack of what to insert.
                final ItemStack pickupStack = itemEntity.getItem().copy().split(this.insertAmount);

                // Iterate every valid slot of the inventory
                for (int slot = 0; slot < inventory.getSlots(); slot++) {

                    // Check if the simulated insert stack can be accepted into the inventory.
                    if (inventory.isItemValid(slot, pickupStack) && inventory.insertItem(slot, pickupStack, true).getCount() != pickupStack.getCount()) {

                        // Actually split the picked up stack so it can be legitimately
                        // inserted.
                        final ItemStack actualPickupStack = itemEntity.getItem().split(this.insertAmount);

                        // Insert the legitimate stack.
                        final ItemStack remaining = inventory.insertItem(slot, actualPickupStack, false);

                        // If there are any leftover items, spawn them on the ground.
                        if (!remaining.isEmpty()) {

                            final ItemEntity item = new ItemEntity(EntityType.ITEM, world);
                            item.setItem(remaining);
                            item.setPosition(entity.posX, entity.posY, entity.posZ);
                            item.lifespan = remaining.getEntityLifespan(world);
                            world.addEntity(item);
                        }

                        break;
                    }
                }
            }
        }
    }
}