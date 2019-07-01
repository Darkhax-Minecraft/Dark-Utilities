package net.darkhax.darkutils.features.slimecrucible;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntitySlimeCrucible extends TileEntityBasicTickable {

    private Direction sideToFace = Direction.SOUTH;
    private double slimeHeightOffset = 0.2f;
    private Entity entity;
    
    private final ItemHandlerSlimeInventory slimeInventory;

    public TileEntitySlimeCrucible () {

        super(DarkUtils.content.tileSlimeCrucible);
        this.slimeInventory = new ItemHandlerSlimeInventory();
    }

    @Override
    public void writeNBT (CompoundNBT dataTag) {

        dataTag.put("SlimeInventory", slimeInventory.serializeNBT());
    }

    @Override
    public void readNBT (CompoundNBT dataTag) {

        slimeInventory.deserializeNBT(dataTag.getCompound("SlimeInventory"));
    }

    @Override
    public void onEntityUpdate () {

        final BlockPos pos = this.getPos();

        final PlayerEntity player = this.world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 4.5, true);

        if (player != null) {

            final double d0 = player.posX - (this.pos.getX() + 0.5F);
            final double d1 = player.posZ - (this.pos.getZ() + 0.5F);
            final double angle = MathHelper.atan2(d0, d1) * 180.0 / Math.PI;
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
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability (@Nonnull Capability<T> cap, @Nullable Direction side) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != Direction.UP) {

            return LazyOptional.of( () -> this.slimeInventory).cast();
        }

        return super.getCapability(cap, side);
    }
    
    public int getSlimePoints() {
        
        return this.slimeInventory.getSlimePoints();
    }
    
    public Entity getContainedSlime (World world) {

        if (this.entity == null) {
        	
            this.entity = EntityType.SLIME.create(world);
        }

        return this.entity;
    }

    public Direction getSideToFace () {

        return this.sideToFace;
    }

    public double getEntityHeightOffset () {

        return this.slimeHeightOffset;
    }
}