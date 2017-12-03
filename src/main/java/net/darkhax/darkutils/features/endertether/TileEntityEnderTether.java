package net.darkhax.darkutils.features.endertether;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityEnderTether extends TileEntityBasic {

    public boolean showBorder = false;

    public AxisAlignedBB area;

    /**
     * Checks if an entity is close enough to the tether for it to be warped while teleporting.
     * Also checks configs for validity.
     *
     * @param entity The entity to compare.
     * @return Whether or not the entity is close enough to be warped.
     */
    public boolean isEntityCloseEnough (EntityLivingBase entity) {

        if (this.area == null) {

            this.area = new AxisAlignedBB(this.pos.add(-FeatureEnderTether.tetherRange, -FeatureEnderTether.tetherRange, -FeatureEnderTether.tetherRange), this.pos.add(FeatureEnderTether.tetherRange + 1, FeatureEnderTether.tetherRange + 1, FeatureEnderTether.tetherRange + 1));
        }

        return this.isInvalid() || this.area == null || entity == null || entity.isDead || entity.getEntityBoundingBox() == null || entity instanceof EntityPlayer && !FeatureEnderTether.affectPlayers ? false : this.area.intersects(entity.getEntityBoundingBox());
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

        dataTag.setBoolean("showBorder", this.showBorder);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        this.showBorder = dataTag.getBoolean("showBorder");
    }

    @Override
    public void onLoad () {

        super.onLoad();

        if (!this.world.isRemote && !FeatureEnderTether.isTracked(this)) {

            FeatureEnderTether.trackTether(this);
        }
    }

    @Override
    public void onChunkUnload () {

        // Stop tracking when chunk is unloaded.
        FeatureEnderTether.stopTrackingTether(this);
    }

    @Override
    public void onTileRemoved (World world, BlockPos pos, IBlockState state) {

        // Stop tracking when the tile is broken.
        FeatureEnderTether.stopTrackingTether(this);
    }
}