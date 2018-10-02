package net.darkhax.darkutils.features.updatedetector;

import java.util.Random;

import net.darkhax.bookshelf.data.Blockstates;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockUpdateDetector extends Block {

    public BlockUpdateDetector () {

        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(Blockstates.POWERED, false));
        this.setHardness(1f);
    }

    @Override
    public void neighborChanged (IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {

        if (worldIn.isRemote || blockIn.canProvidePower(state) || blockIn == Blocks.PISTON_EXTENSION || blockIn == Blocks.PISTON_HEAD || state.getValue(Blockstates.POWERED)) {
            return;
        }

        worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(Blockstates.POWERED, true), 1 | 2);
        worldIn.scheduleUpdate(pos, this, 5);
    }

    @Override
    public BlockStateContainer createBlockState () {

        return new BlockStateContainer(this, Blockstates.POWERED);
    }

    @Override
    public int getMetaFromState (IBlockState state) {

        return state.getValue(Blockstates.POWERED) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta (int meta) {

        return this.getDefaultState().withProperty(Blockstates.POWERED, meta == 1);
    }

    @Override
    @Deprecated
    public boolean canProvidePower (IBlockState state) {

        return true;
    }

    @Override
    public int getWeakPower (IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return state.getValue(Blockstates.POWERED) ? 15 : 0;
    }

    @Override
    public int tickRate (World world) {

        return 5;
    }

    @Override
    public void updateTick (World world, BlockPos pos, IBlockState state, Random rand) {

        if (state.getValue(Blockstates.POWERED)) {
            world.setBlockState(pos, state.withProperty(Blockstates.POWERED, false), 1 | 2);
        }
    }

    @Override
    public boolean isOpaqueCube (IBlockState state) {

        return false;
    }

    @Override
    public boolean isFullCube (IBlockState state) {

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer () {

        return BlockRenderLayer.CUTOUT;
    }
}