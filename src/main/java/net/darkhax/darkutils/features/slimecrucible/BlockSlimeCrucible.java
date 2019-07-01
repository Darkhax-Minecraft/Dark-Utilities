package net.darkhax.darkutils.features.slimecrucible;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockSlimeCrucible extends ContainerBlock {

    public BlockSlimeCrucible (Properties properties) {

        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity (IBlockReader worldIn) {

        return new TileEntitySlimeCrucible();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
    	
        return BlockRenderType.MODEL;
     }

    @Override
    public BlockRenderLayer getRenderLayer () {

        return BlockRenderLayer.CUTOUT;
    }
    
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {   
    	
        TileEntity tile = worldIn.getTileEntity(pos);
        
        if (tile instanceof TileEntitySlimeCrucible) {
            
            System.out.println(((TileEntitySlimeCrucible) tile).getSlimePoints());
        }
        
        return false;
     }
}
