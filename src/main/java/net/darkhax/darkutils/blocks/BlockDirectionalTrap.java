package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.libs.Utilities;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockDirectionalTrap extends BlockTrapBase {
    
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public BlockDirectionalTrap() {
        
        super();
        this.setUnlocalizedName("darkutils.trap.movement");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.slipperiness = 0.98F;
    }
    
    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity) {
        
        if (entity.isSneaking())
            return;
        
        EnumFacing direction = state.getValue(FACING);
        
        if (Utilities.requireCentering(pos, entity.getPositionVector(), direction)) {
            
            Utilities.centerDirectional(pos, entity, direction);
            System.out.println("Centering");
        }
        
        if (entity != null)
            Utilities.pushTowards(entity, state.getValue(FACING), 0.06);
    }
    
    @Override
    public IBlockState onBlockPlaced (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    protected BlockState createBlockState () {
        
        return new BlockState(this, new IProperty[] { FACING });
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState (IBlockState state) {
        
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
    }
}