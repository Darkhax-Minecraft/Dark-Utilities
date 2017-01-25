package net.darkhax.darkutils.features.endertether;

import net.darkhax.bookshelf.lib.util.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnderTether extends TileEntity {

    /**
     * Checks if an entity is close enough to the tether for it to be warped while teleporting.
     * Also checks configs for validity.
     *
     * @param entity The entity to compare.
     * @return Whether or not the entity is close enough to be warped.
     */
    public boolean isEntityCloseEnough (EntityLivingBase entity) {

        return entity instanceof EntityPlayer && !FeatureEnderTether.affectPlayers ? false : EntityUtils.getDistaceFromPos(entity, this.getPos()) <= FeatureEnderTether.tetherRange;
    }
}