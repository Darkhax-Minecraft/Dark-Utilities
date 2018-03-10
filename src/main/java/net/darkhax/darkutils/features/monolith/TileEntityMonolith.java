package net.darkhax.darkutils.features.monolith;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.darkhax.bookshelf.util.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class TileEntityMonolith extends TileEntityBasicTickable {

    public static boolean validatePosition (World world, BlockPos original, boolean shouldBreak) {

        final boolean isValidPos = FeatureMonolith.getMonolithInChunk(world, original).size() <= 1;

        if (!isValidPos && shouldBreak) {

            world.destroyBlock(original, true);
        }

        return isValidPos;
    }

    public void onBlockBroken (World world, BlockPos pos) {

    }

    public boolean isInSameChunk (BlockPos pos) {

        return WorldUtils.areSameChunk(this.pos, pos);
    }

    public boolean onBlockActivated (World worldIn, EntityPlayer playerIn) {

        return false;
    }

    @Override
    public void onEntityUpdate () {

        if (this.getWorld() instanceof WorldServer) {

            validatePosition(this.world, this.pos, true);
        }
    }
}