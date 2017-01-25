package net.darkhax.darkutils.features.faketnt;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFakeTNT extends BlockTNT {

    public BlockFakeTNT () {

        super();
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public void explode (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {

        if (!worldIn.isRemote)
            if (state.getValue(EXPLODE).booleanValue()) {

                final EntityFakeTNT entitytntprimed = new EntityFakeTNT(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
                worldIn.spawnEntityInWorld(entitytntprimed);
                worldIn.playSound((EntityPlayer) null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
    }
}