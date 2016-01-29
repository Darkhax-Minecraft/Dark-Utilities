package net.darkhax.darkutils.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.Chunk;

public class TileEntityAntiSlime extends TileEntity {
    
    public boolean shareChunks (EntityLivingBase entity) {
        
        Vec3 entPos = entity.getPositionVector();
        BlockPos blockpos = new BlockPos(MathHelper.floor_double(entPos.xCoord), 0, MathHelper.floor_double(entPos.zCoord));
        Chunk chunk = entity.worldObj.getChunkFromBlockCoords(blockpos);
        Chunk tileChunk = this.worldObj.getChunkFromBlockCoords(this.pos);
        return (chunk.xPosition == tileChunk.xPosition && chunk.zPosition == tileChunk.zPosition);
    }
}