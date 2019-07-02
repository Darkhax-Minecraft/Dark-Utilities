package net.darkhax.darkutils.features.flatblocks.tick;

import net.darkhax.bookshelf.util.InventoryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class TickEffectExport implements TickEffect {

    @Override
    public void apply (BlockState state, World world, BlockPos pos) {

        final Direction pointingDirection = state.get(BlockStateProperties.HORIZONTAL_FACING);
        final IItemHandler connectedInventory = InventoryUtils.getInventory(world, pos.offset(pointingDirection.getOpposite()), pointingDirection);

        if (connectedInventory != null) {

            for (int slot = 0; slot < connectedInventory.getSlots(); slot++) {

                final ItemStack itemStack = connectedInventory.getStackInSlot(slot);

                if (!itemStack.isEmpty()) {

                    final ItemStack stackToSpawn = itemStack.split(1);

                    final ItemEntity item = new ItemEntity(EntityType.ITEM, world);
                    item.setItem(stackToSpawn);
                    item.setPosition(pos.getX() + 0.5f, pos.getY() + 0.2f, pos.getZ() + 0.5f);
                    item.lifespan = stackToSpawn.getEntityLifespan(world);
                    world.addEntity(item);
                    break;
                }
            }
        }
    }
}
