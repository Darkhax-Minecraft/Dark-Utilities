package net.darkhax.darkutilities.common.features.flatblocks;

import net.darkhax.bookshelf.common.api.block.IBlockHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;

public class BlockFlatTileRotatableLightningUpgrade extends BlockFlatTileRotatable implements IBlockHooks {

    private final Supplier<Block> upgradeTo;

    public BlockFlatTileRotatableLightningUpgrade(CollisionEffect collisionEffect, Supplier<Block> upgradeTo) {
        this(BlockFlatTile.BLOCK_PROPERTIES, collisionEffect, upgradeTo);
    }

    public BlockFlatTileRotatableLightningUpgrade(Properties properties, CollisionEffect collisionEffect, Supplier<Block> upgradeTo) {
        super(properties, collisionEffect);
        this.upgradeTo = upgradeTo;
    }

    @Override
    public void onLightningStrike(BlockState state, Level level, BlockPos pos, LightningBolt lightning) {
        convertPlates(this.upgradeTo.get(), level, pos, 16);
    }

    @Override
    public void onLightningStrikeIndirect(BlockState state, Level level, BlockPos pos, LightningBolt lightning, BlockPos strikeOrigin) {
        this.onLightningStrike(state, level, pos, lightning);
    }

    private void convertPlates(Block to, Level world, BlockPos startPos, int attempts) {
        final BlockPos.MutableBlockPos mutable = startPos.mutable();
        for (int attempt = 0; attempt < attempts; attempt++) {
            final Direction pointing = convertPlates(to, world, mutable);
            if (pointing != null) {
                mutable.move(pointing);
            }
            else {
                return;
            }
        }
    }

    private Direction convertPlates(Block to, Level world, BlockPos platePos) {
        final BlockState state = world.getBlockState(platePos);
        if (state.is(this)) {
            final Direction plateDirection = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            final boolean platePowered = state.getValue(BlockStateProperties.POWERED);
            world.setBlockAndUpdate(platePos, to.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, plateDirection).setValue(BlockStateProperties.POWERED, platePowered));
            world.levelEvent(3002, platePos, -1);
            return plateDirection;
        }
        return null;
    }
}