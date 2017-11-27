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
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;

public class TileEntityMonolith extends TileEntityBasicTickable {

    public static boolean validatePosition (WorldServer world, TileEntity firstTile, BlockPos original, boolean shouldBreak) {

        for (final TileEntity tile : FeatureMonolith.getMonoliths(world)) {

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

    public boolean isInSameChunk (BlockPos pos) {

        return WorldUtils.areSameChunk(this.getWorld(), this.pos, pos);
    }

    public void onSpawnCheck (CheckSpawn event) {

    }

    @Override
    public void onLoad () {

        super.onLoad();

        if (!this.world.isRemote && !FeatureMonolith.isTracked(this)) {

            FeatureMonolith.trackMonolith(this);
        }
    }

    @Override
    public void onChunkUnload () {

        // Stop tracking when chunk is unloaded.
        FeatureMonolith.stopTrackingMonolith(this);
    }

    @Override
    public void onTileRemoved (World world, BlockPos pos, IBlockState state) {

        // Stop tracking when the tile is broken.
        FeatureMonolith.stopTrackingMonolith(this);
    }

    @Override
    public void onEntityUpdate () {

        if (this.getWorld() instanceof WorldServer) {
            
            validatePosition((WorldServer) this.world, this, this.pos, true);
        }
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

    }
}