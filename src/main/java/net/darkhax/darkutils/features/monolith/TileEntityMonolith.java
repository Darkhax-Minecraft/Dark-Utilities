package net.darkhax.darkutils.features.monolith;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.darkhax.bookshelf.util.WorldUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMonolith extends TileEntityBasicTickable {

    public static final List<TileEntityMonolith> LOADED_MONOLITHS = new ArrayList<>();

    public static boolean validatePosition (World world, TileEntity firstTile, BlockPos original, boolean shouldBreak) {

        for (final TileEntity tile : world.loadedTileEntityList) {

            if (tile != firstTile && WorldUtils.areSameChunk(world, original, tile.getPos())) {

                if (shouldBreak) {

                    world.destroyBlock(original, true);
                    world.destroyBlock(tile.getPos(), true);
                }

                return false;
            }
        }

        return true;
    }

    public boolean isInSameChunk(BlockPos pos) {
        
        return WorldUtils.areSameChunk(this.getWorld(), this.pos, pos);
    }
    @Override
    public void onLoad () {

        super.onLoad();

        if (!this.world.isRemote && !LOADED_MONOLITHS.contains(this)) {

            LOADED_MONOLITHS.add(this);
        }
    }

    @Override
    public void onChunkUnload () {

        // Stop tracking when chunk is unloaded.
        LOADED_MONOLITHS.remove(this);
    }

    @Override
    public void onTileRemoved (World world, BlockPos pos, IBlockState state) {

        // Stop tracking when the tile is broken.
        LOADED_MONOLITHS.remove(this);
    }

    @Override
    public void onEntityUpdate () {

        validatePosition(this.world, this, this.pos, true);
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

    }
}