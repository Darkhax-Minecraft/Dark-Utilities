package net.darkhax.darkutils.features.flatblocks;

import java.util.Random;

import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffect;
import net.darkhax.darkutils.features.flatblocks.tick.TickEffect;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockFlatTileRotatingTicking extends BlockFlatTileRotating implements ITileEntityProvider {

    private final TickEffect tickEffect;
    private final int tickRate;

    public BlockFlatTileRotatingTicking (CollisionEffect collisionEffect, TickEffect tickEffect, int tickRate) {

        this(BLOCK_PROPERTIES, collisionEffect, tickEffect, tickRate);
    }

    public BlockFlatTileRotatingTicking (Properties properties, CollisionEffect collisionEffect, TickEffect tickEffect, int tickRate) {

        super(properties, collisionEffect);
        this.tickEffect = tickEffect;
        this.tickRate = tickRate;
    }

    @Override
    public int tickRate (IWorldReader world) {

        return this.tickRate;
    }

    @Override
    public void tick (BlockState state, World world, BlockPos pos, Random random) {

        if (this.tickEffect != null) {

            this.tickEffect.apply(state, world, pos);
        }
    }

    @Override
    public TileEntity createNewTileEntity (IBlockReader worldIn) {

        return new TileEntityTickingEffect();
    }

    @Override
    public boolean eventReceived (BlockState state, World worldIn, BlockPos pos, int id, int param) {

        super.eventReceived(state, worldIn, pos, id, param);
        final TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public void onReplaced (BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

        if (state.getBlock() != newState.getBlock()) {
            super.onReplaced(state, worldIn, pos, newState, isMoving);
            worldIn.removeTileEntity(pos);
        }
    }
}