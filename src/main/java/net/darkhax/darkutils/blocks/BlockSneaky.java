package net.darkhax.darkutils.blocks;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.darkutils.tileentity.TileEntitySneaky;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSneaky extends BlockContainer {
    
    protected BlockSneaky() {
        
        super(Material.rock);
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return null;
    }
    
    @Override
    public BlockState createBlockState () {
        
        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { BlockStates.HELD_STATE });
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return getDefaultState();
    }
    
    @Override
    public IBlockState getExtendedState (IBlockState state, IBlockAccess world, BlockPos pos) {
        
        if (world.getTileEntity(pos) instanceof TileEntitySneaky) {
            
            TileEntitySneaky tile = ((TileEntitySneaky) world.getTileEntity(pos));
            return ((IExtendedBlockState) state).withProperty(BlockStates.HELD_STATE, tile.heldState);
        }
        
        else
            return ((IExtendedBlockState) state);
    }
    
    @Override
    public boolean canRenderInLayer (EnumWorldBlockLayer layer) {
        
        return true;
    }
}