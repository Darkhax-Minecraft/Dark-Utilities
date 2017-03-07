package net.darkhax.darkutils.features.antislime;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;

public class TileEntityAntiSlime extends TileEntity {

    public boolean shareChunks (EntityLivingBase entity) {

        final Vec3d entPos = entity.getPositionVector();
        final BlockPos blockpos = new BlockPos(MathHelper.floor(entPos.xCoord), 0, MathHelper.floor(entPos.zCoord));
        final Chunk chunk = entity.world.getChunkFromBlockCoords(blockpos);
        final Chunk tileChunk = this.world.getChunkFromBlockCoords(this.pos);
        return chunk.xPosition == tileChunk.xPosition && chunk.zPosition == tileChunk.zPosition;
    }
}