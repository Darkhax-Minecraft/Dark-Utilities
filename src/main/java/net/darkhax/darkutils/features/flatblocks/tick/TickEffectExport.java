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
import net.minecraftforge.items.wrapper.EmptyHandler;

public class TickEffectExport implements TickEffect {
    
    private final int extractSpeed;
    
    public TickEffectExport(int extractSpeed) {
        
        this.extractSpeed = extractSpeed;
    }
    
    @Override
    public void apply (BlockState state, World world, BlockPos pos) {
        
        final Direction pointingDirection = state.get(BlockStateProperties.HORIZONTAL_FACING);
        final IItemHandler connectedInventory = InventoryUtils.getInventory(world, pos.offset(pointingDirection.getOpposite()), pointingDirection);
        
        if (connectedInventory != null && connectedInventory != EmptyHandler.INSTANCE) {
            
            for (int slot = 0; slot < connectedInventory.getSlots(); slot++) {
                
                final ItemStack simulated = connectedInventory.extractItem(slot, extractSpeed, true);
                
                if (!simulated.isEmpty()) {
                    
                    final ItemStack extracted = connectedInventory.extractItem(slot, extractSpeed, false);
                    
                    final ItemEntity item = new ItemEntity(EntityType.ITEM, world);
                    item.setItem(extracted);
                    item.setPosition(pos.getX() + 0.5f, pos.getY() + 0.2f, pos.getZ() + 0.5f);
                    item.lifespan = extracted.getEntityLifespan(world);
                    world.addEntity(item);
                    break;
                }
            }
        }
    }
}
