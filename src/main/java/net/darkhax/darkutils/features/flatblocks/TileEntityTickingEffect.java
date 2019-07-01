package net.darkhax.darkutils.features.flatblocks;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class TileEntityTickingEffect extends TileEntity implements ITickableTileEntity {

    private byte timer;

    public TileEntityTickingEffect () {

        super(DarkUtils.content.tileTickingEffect);
    }

    public TileEntityTickingEffect (TileEntityType<?> tileEntityTypeIn) {

        super(tileEntityTypeIn);
    }

    @Override
    public void tick () {

        this.timer++;

        if (this.timer >= this.getBlockState().getBlock().tickRate(this.world)) {

            this.world.getPendingBlockTicks().scheduleTick(new BlockPos(this.pos), this.getBlockState().getBlock(), this.getBlockState().getBlock().tickRate(this.world));
            this.timer = 0;
        }
    }
}