package net.darkhax.darkutils.features.sneaky;

import java.util.Random;

import net.darkhax.bookshelf.data.Blockstates;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSneakyButton extends BlockSneaky {

    public BlockSneakyButton () {

        this.setDefaultState(((IExtendedBlockState) this.blockState.getBaseState()).withProperty(Blockstates.HELD_STATE, null).withProperty(Blockstates.BLOCK_ACCESS, null).withProperty(Blockstates.BLOCKPOS, null).withProperty(Blockstates.POWERED, Boolean.valueOf(false)));
        this.setTickRandomly(true);
    }

    @Override
    public BlockStateContainer createBlockState () {

        return new ExtendedBlockState(this, new IProperty[] { Blockstates.POWERED }, new IUnlistedProperty[] { Blockstates.HELD_STATE, Blockstates.BLOCK_ACCESS, Blockstates.BLOCKPOS });
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(Blockstates.POWERED).booleanValue() ? 1 : 0;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(Blockstates.POWERED, meta == 0 ? false : true);
    }

    @Override
    public void randomTick (World worldIn, BlockPos pos, IBlockState state, Random random) {

    }

    @Override
    public void updateTick (World worldIn, BlockPos pos, IBlockState state, Random rand) {

        if (!worldIn.isRemote) {
            if (state.getValue(Blockstates.POWERED).booleanValue()) {
                worldIn.setBlockState(pos, state.withProperty(Blockstates.POWERED, Boolean.valueOf(false)));
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        final ItemStack heldItem = playerIn.getHeldItemMainhand();

        if (!heldItem.isEmpty()) {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }

        if (worldIn.isRemote) {

            return true;
        }
        else {

            worldIn.setBlockState(pos, state.withProperty(Blockstates.POWERED, Boolean.valueOf(true)), 3);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            return true;
        }
    }

    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {

        if (state.getValue(Blockstates.POWERED).booleanValue()) {
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @Deprecated
    public int getWeakPower (IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return blockState.getValue(Blockstates.POWERED).booleanValue() ? 15 : 0;
    }

    @Override
    @Deprecated
    public int getStrongPower (IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return blockState.getValue(Blockstates.POWERED).booleanValue() ? 15 : 0;
    }

    @Override
    @Deprecated
    public boolean canProvidePower (IBlockState state) {

        return true;
    }
}
