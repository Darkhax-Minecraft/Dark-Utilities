package net.darkhax.darkutils.blocks.sneaky;

import java.util.List;
import java.util.Random;

import net.darkhax.bookshelf.lib.BlockStates;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSneakyPressurePlate extends BlockSneaky {
    
    protected static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 0.99D, 1D);
    
    public BlockSneakyPressurePlate() {
        
        this.setDefaultState(((IExtendedBlockState) this.blockState.getBaseState()).withProperty(BlockStates.HELD_STATE, null).withProperty(BlockStates.BLOCK_ACCESS, null).withProperty(BlockStates.BLOCKPOS, null).withProperty(BlockStates.POWERED, Boolean.valueOf(false)));
        this.setUnlocalizedName("darkutils.sneaky.plate");
        this.setTickRandomly(true);
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
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos) {
        
        return BOUNDS;
    }
    
    @Override
    public int tickRate (World worldIn) {
        
        return 20;
    }
    
    @Override
    public void randomTick (World worldIn, BlockPos pos, IBlockState state, Random random) {
    
    }
    
    @Override
    public void updateTick (World worldIn, BlockPos pos, IBlockState state, Random rand) {
        
        if (!worldIn.isRemote) {
            
            final int power = this.getRedstoneStrength(state);
            
            if (power > 0)
                this.updateState(worldIn, pos, state, power);
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock (World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        
        if (!worldIn.isRemote) {
            final int power = this.getRedstoneStrength(state);
            
            if (power == 0)
                this.updateState(worldIn, pos, state, power);
        }
    }
    
    /**
     * Updates the pressure plate when stepped on
     */
    protected void updateState (World worldIn, BlockPos pos, IBlockState state, int oldPower) {
        
        final int power = this.computeRedstoneStrength(worldIn, pos);
        final boolean turnOn = power > 0;
        
        if (oldPower != power) {
            
            state = this.setRedstoneStrength(state, power);
            worldIn.setBlockState(pos, state, 2);
            this.updateNeighbors(worldIn, pos);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
        }
        
        if (turnOn)
            worldIn.scheduleUpdate(new BlockPos(pos), this, this.tickRate(worldIn));
    }
    
    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        
        if (this.getRedstoneStrength(state) > 0)
            this.updateNeighbors(worldIn, pos);
            
        super.breakBlock(worldIn, pos, state);
    }
    
    protected void updateNeighbors (World worldIn, BlockPos pos) {
        
        worldIn.notifyNeighborsOfStateChange(pos, this);
        worldIn.notifyNeighborsOfStateChange(pos.down(), this);
    }
    
    @Override
    public int getWeakPower (IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        
        return this.getRedstoneStrength(blockState);
    }
    
    @Override
    public int getStrongPower (IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        
        return side == EnumFacing.UP ? this.getRedstoneStrength(blockState) : 0;
    }
    
    @Override
    public boolean canProvidePower (IBlockState state) {
        
        return true;
    }
    
    protected int getRedstoneStrength (IBlockState state) {
        
        return state.getValue(BlockStates.POWERED).booleanValue() ? 15 : 0;
    }
    
    protected IBlockState setRedstoneStrength (IBlockState state, int strength) {
        
        return state.withProperty(BlockStates.POWERED, Boolean.valueOf(strength > 0));
    }
    
    protected int computeRedstoneStrength (World worldIn, BlockPos pos) {
        
        final AxisAlignedBB axisalignedbb = BOUNDS.offset(pos.up());
        final List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity) null, axisalignedbb);
        
        if (!list.isEmpty())
            for (final Entity entity : list)
                if (!entity.doesEntityNotTriggerPressurePlate())
                    return 15;
                    
        return 0;
    }
}