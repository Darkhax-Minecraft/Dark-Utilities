package net.darkhax.darkutils.blocks;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVectorPlate extends BlockTrapBase {
    
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    private final double speed;
    
    public BlockVectorPlate(double speed) {
        
        super();
        this.setUnlocalizedName("darkutils.trap.movement");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.speed = speed;
    }
    
    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity) {
        
        final EnumFacing direction = state.getValue(FACING).getOpposite();
        
        if (entity.canBePushed() && !entity.isSneaking()) {
            
            entity.motionX += this.speed * direction.getFrontOffsetX();
            entity.motionZ += this.speed * direction.getFrontOffsetZ();
            
            if (entity instanceof EntityItem)
                ((EntityItem) entity).setAgeToCreativeDespawnTime();
        }
    }
    
    @Override
    protected BlockStateContainer createBlockState () {
        
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }
    
    @Override
    public IBlockState onBlockPlaced (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
}