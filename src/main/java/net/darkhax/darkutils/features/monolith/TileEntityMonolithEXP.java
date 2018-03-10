package net.darkhax.darkutils.features.monolith;

import net.darkhax.bookshelf.util.ParticleUtils;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class TileEntityMonolithEXP extends TileEntityMonolith {

    private static final int MAX_EXP = 1995143615;

    private int storedXP = 0;

    @Override
    public boolean onBlockActivated (World worldIn, EntityPlayer playerIn) {

        if (!worldIn.isRemote) {

            // Always send info message
            playerIn.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "EXP: " + this.storedXP), true);

            // Player wants raw exp
            if (playerIn.isSneaking() && this.storedXP >= 15) {

                playerIn.addExperience(15);
                this.storedXP -= 15;
                this.markDirty();
            }

            // Player wants bottle of exp
            else if (playerIn.getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE && this.storedXP >= 15) {

                playerIn.getHeldItemMainhand().shrink(1);
                playerIn.addItemStackToInventory(new ItemStack(Items.EXPERIENCE_BOTTLE));
                this.storedXP -= 15;
                this.markDirty();
            }
        }

        return true;
    }

    @Override
    public void onBlockBroken (World world, BlockPos pos) {

        while (this.storedXP > 0) {

            final int amountToTake = Math.min(this.storedXP, 100);
            this.storedXP -= amountToTake;

            final EntityXPOrb xp = new EntityXPOrb(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, amountToTake);
            world.spawnEntity(xp);
        }
    }

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

        final int consumed = Math.min(MAX_EXP - this.storedXP, Math.min(10000, exp));
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

        dataTag.setInteger("exp", this.storedXP);
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        this.storedXP = dataTag.getInteger("exp");
    }
}
