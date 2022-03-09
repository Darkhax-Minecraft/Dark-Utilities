package net.darkhax.darkutilities.features.flatblocks;

import net.darkhax.bookshelf.api.block.ILightningConductive;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;

public class BlockFlatTileRotatableLightningUpgrade extends BlockFlatTileRotatable implements ILightningConductive {

    private final Supplier<Block> upgradeTo;

    public BlockFlatTileRotatableLightningUpgrade(Supplier<Block> upgradeTo) {

        super();
        this.upgradeTo = upgradeTo;
    }

    public BlockFlatTileRotatableLightningUpgrade(CollisionEffect collisionEffect, Supplier<Block> upgradeTo) {

        super(collisionEffect);
        this.upgradeTo = upgradeTo;
    }

    public BlockFlatTileRotatableLightningUpgrade(Properties properties, CollisionEffect collisionEffect, Supplier<Block> upgradeTo) {

        super(properties, collisionEffect);
        this.upgradeTo = upgradeTo;
    }

    @Override
    public void onDirectLightningStrike(Level world, BlockPos pos, BlockState state, LightningBolt lightning) {

        convertPlates(this.upgradeTo.get(), world, pos, 16);
    }

    @Override
    public void onIndirectLightingStrike(Level world, BlockPos strikePos, BlockState strikeState, BlockPos indirectPos, BlockState indirectState, LightningBolt lightning) {

        convertPlates(this.upgradeTo.get(), world, indirectPos, 16);
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