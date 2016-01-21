package net.darkhax.darkutils.blocks;

import net.darkhax.bookshelf.lib.util.EntityUtils;
import net.darkhax.bookshelf.lib.util.MathsUtils;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockTrapMovement extends BlockTrapBase {
    
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public BlockTrapMovement() {
        
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
        
        if (requireCentering(pos, entity.getPositionVector(), direction))
            centerDirectional(pos, entity, direction);
            
        if (entity != null)
            EntityUtils.pushTowards(entity, state.getValue(FACING), 0.06);
    }
    
    @Override
    public IBlockState onBlockPlaced (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    protected BlockState createBlockState () {
        
        return new BlockState(this, new IProperty[] { FACING });
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
    }
    
    private void centerDirectional (BlockPos block, Entity entity, EnumFacing facing) {
        
        Vec3 entPos = entity.getPositionVector();
        
        if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
            entity.setPositionAndUpdate(entPos.xCoord, entPos.yCoord, block.getZ() + 0.5);
            
        else
            entity.setPositionAndUpdate(block.getX() + 0.5, entPos.yCoord, entPos.zCoord);
    }
    
    private boolean requireCentering (BlockPos block, Vec3 entPos, EnumFacing facing) {
        
        if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
            return !MathsUtils.isInRange(block.getZ() + 0.35, block.getZ() + 0.65, entPos.zCoord);
            
        return !MathsUtils.isInRange(block.getX() + 0.35, block.getX() + 0.65, entPos.xCoord);
    }
}