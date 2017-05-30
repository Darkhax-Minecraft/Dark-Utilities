package net.darkhax.darkutils.features.sneaky;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSneakyBedrock extends BlockSneaky {

    public BlockSneakyBedrock () {

        this.setBlockUnbreakable();
        this.setResistance(6000000f);
    }

    @Override
    public boolean onBlockActivated (World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack currentStack, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (playerIn.capabilities.isCreativeMode) {
            return super.onBlockActivated(world, pos, state, playerIn, hand, currentStack, side, hitX, hitY, hitZ);
        }

        return false;
    }
}