package net.darkhax.darkutils.features.monolith;

import net.darkhax.bookshelf.util.ParticleUtils;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

public class TileEntityMonolithEXP extends TileEntityMonolith {

    public static final int maxXP = 1995143615;

    public boolean showBorder = false;
    public int storedXP = 0;

    @Override
    public void onEntityUpdate () {

        boolean hasKilled = false;

        if (this.isInvalid() || !this.getWorld().isBlockLoaded(this.getPos()) || this.world.isBlockPowered(this.pos)) {

            return;
        }

        final Chunk chunk = this.getWorld().getChunkFromBlockCoords(this.getPos());
        final ChunkPos chunkPos = chunk.getPos();

        final AxisAlignedBB bounds = new AxisAlignedBB(new BlockPos(chunkPos.getXStart(), 0, chunkPos.getZStart()), new BlockPos(chunkPos.getXEnd(), 255, chunkPos.getZEnd()));

        for (final EntityXPOrb orb : this.getWorld().getEntitiesWithinAABB(EntityXPOrb.class, bounds)) {

            if (!orb.isDead && orb.xpValue > 0) {

                orb.xpValue = this.consumeXP(orb.xpValue);

                if (orb.xpValue <= 0) {

                    orb.setDead();

                    if (!hasKilled) {
                        final BlockPos pos = this.getPos();
                        ParticleUtils.spawnParticleRing(this.world, EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5d, pos.getY() + 0.7d, pos.getZ() + 0.5d, 0, 0.2, 0, 0.25);
                        hasKilled = true;
                    }
                }
            }
        }

        super.onEntityUpdate();
    }

    public int consumeXP (int exp) {

        final int consumed = Math.min(maxXP - this.storedXP, Math.min(10000, exp));
        this.storedXP += consumed;
        return exp - consumed;
    }

    public int takeXP (int exp) {

        final int removed = Math.min(this.storedXP, exp);
        this.storedXP -= removed;
        return removed;
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

        super.writeNBT(dataTag);
        dataTag.setInteger("exp", this.storedXP);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        super.readNBT(dataTag);
        this.storedXP = dataTag.getInteger("exp");
    }
}
