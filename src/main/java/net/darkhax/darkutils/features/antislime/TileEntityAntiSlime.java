package net.darkhax.darkutils.features.antislime;

import net.darkhax.bookshelf.util.WorldUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAntiSlime extends TileEntity {

    public boolean shareChunks (EntityLivingBase entity) {

        return WorldUtils.areSameChunk(entity.getPosition(), this.getPos());
    }
}