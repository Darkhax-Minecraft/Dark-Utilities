package net.darkhax.darkutils.features.sneaky;

import net.darkhax.bookshelf.lib.BlockStates;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSneakyLever extends BlockSneaky {

    public BlockSneakyLever () {

        this.setDefaultState(((IExtendedBlockState) this.blockState.getBaseState()).withProperty(BlockStates.HELD_STATE, null).withProperty(BlockStates.BLOCK_ACCESS, null).withProperty(BlockStates.BLOCKPOS, null).withProperty(BlockStates.POWERED, Boolean.valueOf(false)));
    }

    @Override
    public BlockStateContainer createBlockState () {

        return new ExtendedBlockState(this, new IProperty[] { BlockStates.POWERED }, new IUnlistedProperty[] { BlockStates.HELD_STATE, BlockStates.BLOCK_ACCESS, BlockStates.BLOCKPOS });
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(BlockStates.POWERED).booleanValue() ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(BlockStates.POWERED, meta == 0 ? false : true);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        final ItemStack heldItem = playerIn.getHeldItemMainhand();
        
        if (!heldItem.isEmpty())
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

        if (worldIn.isRemote)
            return true;

        else {

            state = state.cycleProperty(BlockStates.POWERED);
            worldIn.setBlockState(pos, state, 1 | 2);
            worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, state.getValue(BlockStates.POWERED).booleanValue() ? 0.6F : 0.5F);
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
            return true;
        }
    }

    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {

        if (state.getValue(BlockStates.POWERED).booleanValue()) {
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int getWeakPower (IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return blockState.getValue(BlockStates.POWERED).booleanValue() ? 15 : 0;
    }

    @Override
    public int getStrongPower (IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return !blockState.getValue(BlockStates.POWERED).booleanValue() ? 15 : 0;
    }

    @Override
    public boolean canProvidePower (IBlockState state) {

        return true;
    }
}