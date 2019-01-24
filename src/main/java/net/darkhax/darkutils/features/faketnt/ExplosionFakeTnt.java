package net.darkhax.darkutils.features.faketnt;

import net.darkhax.bookshelf.world.ExplosionBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExplosionFakeTnt extends ExplosionBase {

    public ExplosionFakeTnt (World world, Entity entity, double x, double y, double z, float size) {

        super(world, entity, x, y, z, size, false, false);
    }

    @Override
    public void destroyBlock (Block block, IBlockState state, BlockPos pos) {

        if (block instanceof BlockFakeTNT) {

            ((BlockFakeTNT) block).explode(this.getWorld(), pos, state.withProperty(BlockTNT.EXPLODE, true), this.getExplosivePlacedBy());
            this.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
        }
    }
    
    @Override
    public boolean canAffectEntity (Entity entity) {
        
        return false;
    }
}