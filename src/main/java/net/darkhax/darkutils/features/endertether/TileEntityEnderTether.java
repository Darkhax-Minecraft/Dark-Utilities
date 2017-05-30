package net.darkhax.darkutils.features.endertether;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityEnderTether extends TileEntityBasic {

    public boolean showBorder = false;

    public AxisAlignedBB area = new AxisAlignedBB(this.pos.add(-FeatureEnderTether.tetherRange, -FeatureEnderTether.tetherRange, -FeatureEnderTether.tetherRange), this.pos.add(FeatureEnderTether.tetherRange + 1, FeatureEnderTether.tetherRange + 1, FeatureEnderTether.tetherRange + 1));

    /**
     * Checks if an entity is close enough to the tether for it to be warped while teleporting.
     * Also checks configs for validity.
     *
     * @param entity The entity to compare.
     * @return Whether or not the entity is close enough to be warped.
     */
    public boolean isEntityCloseEnough (EntityLivingBase entity) {

        return this.isInvalid() || this.area == null || entity == null || entity instanceof EntityPlayer && !FeatureEnderTether.affectPlayers ? false : this.area.intersectsWith(entity.getCollisionBoundingBox());
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