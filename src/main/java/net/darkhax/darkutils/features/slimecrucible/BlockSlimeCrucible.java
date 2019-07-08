package net.darkhax.darkutils.features.slimecrucible;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;

public class BlockSlimeCrucible extends ContainerBlock implements ISlimeCrucibleBlock {
    
    private static final VoxelShape boundsFullCube = VoxelShapes.fullCube();
    private static final VoxelShape boundsCollision = VoxelShapes.combineAndSimplify(boundsFullCube, Block.makeCuboidShape(2.0D, Math.max(2, 1), 2.0D, 14.0D, 16.0D, 14.0D), IBooleanFunction.ONLY_FIRST);
    private final SlimeCrucibleType type;
    
    public BlockSlimeCrucible(Properties properties, SlimeCrucibleType type) {
        
        super(properties);
        this.type = type;
    }
    
    @Override
    public VoxelShape getShape (BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        
        return boundsCollision;
    }
    
    @Override
    public VoxelShape getRaytraceShape (BlockState state, IBlockReader worldIn, BlockPos pos) {
        
        return boundsFullCube;
    }
    
    @Override
    public VoxelShape getCollisionShape (BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        
        return boundsCollision;
    }
    
    @Override
    public TileEntity createNewTileEntity (IBlockReader worldIn) {
        
        return new TileEntitySlimeCrucible();
    }
    
    @Override
    public BlockRenderType getRenderType (BlockState state) {
        
        return BlockRenderType.MODEL;
    }
    
    @Override
    public BlockRenderLayer getRenderLayer () {
        
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public boolean onBlockActivated (BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        
        final TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntitySlimeCrucible) {
            
            final TileEntitySlimeCrucible crucible = (TileEntitySlimeCrucible) tile;
            
            final ItemStack heldItem = player.getHeldItem(handIn);
            
            final int points = TileEntitySlimeCrucible.getSlimePointsForItem(world, heldItem, this);
            
            // If the item can be eaten, try to eat the item and modify the state of the
            // crucible.
            if (points > 0) {
                
                // Increases the slime points.
                crucible.setSlimePoints(crucible.getContainedSlimePoints() + points);
                
                if (world instanceof ServerWorld) {
                    
                    final ServerWorld serverWorld = (ServerWorld) world;
                    
                    // Spawns food eating particles.
                    for (int i = 0; i < 16; i++) {
                        
                        serverWorld.spawnParticle(new ItemParticleData(ParticleTypes.ITEM, heldItem), pos.getX() + 0.5d, pos.getY() + 1.25d, pos.getZ() + 0.5d, 1, world.rand.nextDouble() * 0.45 - 0.45, 0d, world.rand.nextDouble() * 0.45 - 0.45, 0d);
                    }
                }
                
                // Makes the slime do a little squish when fed.
                crucible.setSquishAmount(1f);
                
                // Plays the eating sound for the slime. This can be changed by the entity if
                // it overrides getEatSound.
                world.playSound(null, pos.getX() + 0.5f, pos.getY() + 1.25f, pos.getZ() + 0.5f, crucible.getContainedSlime(world).getEatSound(heldItem), SoundCategory.BLOCKS, 0.5F + 0.5F * world.rand.nextInt(2), (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                return true;
            }
            
            else {
                
                player.openContainer(state.getContainer(world, pos));
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        
        // Applies custom name to the tile entity if the conditions are right.
        if (stack.hasDisplayName()) {
            
            final TileEntity tileentity = worldIn.getTileEntity(pos);
            
            if (tileentity instanceof TileEntitySlimeCrucible) {
                
                ((TileEntitySlimeCrucible) tileentity).setCustomName(stack.getDisplayName());
            }
        }
        
    }
    
    @Override
    public SlimeCrucibleType getCrucibleType () {
        
        return this.type;
    }
}
