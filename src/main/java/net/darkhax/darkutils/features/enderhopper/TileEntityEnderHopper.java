package net.darkhax.darkutils.features.enderhopper;

import java.util.List;

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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntityEnderHopper extends TileEntityBasic implements ITickable {

    private int cooldown = 100;

    public boolean showBorder = false;

    public AxisAlignedBB area = new AxisAlignedBB(this.pos.add(-FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange), this.pos.add(FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1));

    @Override
    public void update () {

        if (this.isInvalid() || !this.getWorld().isBlockLoaded(this.getPos())) {
            return;
        }

        try {

            final EnumFacing direction = this.getDirection();
            final TileEntity tile = this.getWorld().getTileEntity(this.getPos().offset(direction.getOpposite()));

            if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction)) {

                final List<EntityItem> items = this.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.pos.add(-FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange, -FeatureEnderHopper.hopperRange), this.pos.add(FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1, FeatureEnderHopper.hopperRange + 1)));
                final IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction);

                for (final EntityItem item : items) {

                    if (this.cooldown == 0 && !this.getWorld().isRemote) {

                        final ItemStack foundStack = item.getEntityItem();
                        final ItemStack simulation = ItemHandlerHelper.insertItem(handler, foundStack.copy(), true);

                        if ((simulation == null || foundStack.getCount() != simulation.getCount()) && !item.isDead) {

                            final ItemStack result = ItemHandlerHelper.insertItem(handler, foundStack, false);

                            if (result == null) {
                                item.setDead();
                            }
                            else {
                                item.setEntityItemStack(result);
                            }
                        }
                    }

                    this.getWorld().spawnParticle(EnumParticleTypes.PORTAL, item.posX, item.posY, item.posZ, -0.5d + Constants.RANDOM.nextDouble(), -0.5d + Constants.RANDOM.nextDouble(), -0.5d + Constants.RANDOM.nextDouble(), new int[0]);
                }

                if (this.cooldown <= 0) {
                    this.cooldown = 100;
                }
                else {
                    this.cooldown--;
                }
            }
        }

        catch (final Exception exception) {

            Constants.LOG.warn("Ender Hopper at %s in world %s failed an update tick!", this.getPos(), this.getWorld().getWorldInfo().getWorldName());
            Constants.LOG.warn(exception);
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
}
