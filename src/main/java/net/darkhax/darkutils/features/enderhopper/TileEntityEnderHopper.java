package net.darkhax.darkutils.features.enderhopper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasic;
import net.darkhax.bookshelf.data.Blockstates;
import net.darkhax.bookshelf.lib.Constants;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntityEnderHopper extends TileEntityBasic implements ITickable {

    private final Map<UUID, Integer> cooldowns = new HashMap<>();

    public boolean showBorder = false;

    public AxisAlignedBB area = new AxisAlignedBB(this.pos.add(-FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange), this.pos.add(FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1));

    @Override
    public void update () {

        if (this.isInvalid() || !this.getWorld().isBlockLoaded(this.getPos()) || this.world.isBlockPowered(this.pos) || this.world.isRemote) {

            return;
        }

        try {

            final EnumFacing direction = this.getDirection();
            final TileEntity tile = this.getWorld().getTileEntity(this.getPos().offset(direction.getOpposite()));

            if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction)) {

                final List<EntityItem> items = this.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.pos.add(-FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange), this.pos.add(FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1)));
                final IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction);

                for (final EntityItem item : items) {

                    // If no cooldown, set to 100. If it has cooldown, reduce by one.
                    this.cooldowns.put(item.getPersistentID(), this.cooldowns.containsKey(item.getPersistentID()) ? this.cooldowns.get(item.getPersistentID()) - 1 : 100);

                    if (this.cooldowns.get(item.getPersistentID()) <= 0) {

                        final ItemStack foundStack = item.getItem();
                        final ItemStack simulation = ItemHandlerHelper.insertItem(handler, foundStack.copy(), true);

                        if ((simulation.isEmpty() || foundStack.getCount() != simulation.getCount()) && !item.isDead) {

                            final ItemStack result = ItemHandlerHelper.insertItem(handler, foundStack, false);

                            if (result.isEmpty()) {
                                item.setDead();
                                this.cooldowns.remove(item.getPersistentID());
                            }
                            else {
                                item.setItem(result);
                                this.cooldowns.put(item.getPersistentID(), 100);
                            }
                        }
                    }

                    final ItemStack simulation = ItemHandlerHelper.insertItemStacked(handler, item.getItem(), true);

                    if (this.getWorld() instanceof WorldServer) {

                        if (simulation.isEmpty() || simulation.getCount() != item.getItem().getCount()) {

                            ((WorldServer) this.getWorld()).spawnParticle(EnumParticleTypes.PORTAL, true, item.posX, item.posY, item.posZ, 1, -0.2d + this.nextFloat(-0.2f, 0.2f), 1f + this.nextFloat(-0.2f, 0.2f), this.nextFloat(-0.2f, 0.2f), 0.01f, new int[0]);
                        }

                        else {

                            ((WorldServer) this.getWorld()).spawnParticle(EnumParticleTypes.REDSTONE, true, item.posX + this.nextFloat(-0.2f, 0.2f), item.posY + 0.2f + this.nextFloat(-0.2f, 0.2f), item.posZ + this.nextFloat(-0.2f, 0.2f), 0, 0f, 0f, 0f, 0.01f, new int[] { 1, 0, 0 });
                        }
                    }
                }
            }
        }

        catch (final Exception exception) {

            Constants.LOG.warn(exception, "Ender Hopper at %s in world %s failed an update tick!", this.getPos(), this.getWorld().getWorldInfo().getWorldName());
        }
    }

    private EnumFacing getDirection () {

        return this.getWorld().getBlockState(this.getPos()).getValue(Blockstates.FACING);
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

        dataTag.setBoolean("showBorder", this.showBorder);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        this.showBorder = dataTag.getBoolean("showBorder");
    }

    private float nextFloat (float min, float max) {

        return min + Constants.RANDOM.nextFloat() * (max - min);
    }
}
