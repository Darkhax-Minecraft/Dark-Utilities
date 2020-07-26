package net.darkhax.darkutils.features.flatblocks;

import java.util.Random;

import net.darkhax.darkutils.features.flatblocks.collision.CollisionEffect;
import net.darkhax.darkutils.features.flatblocks.tick.TickEffect;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@SuppressWarnings("deprecation")
public class BlockFlatTileRotatingTicking extends BlockFlatTileRotating {
    
    private final TickEffect tickEffect;
    private final int tickRate;
    
    public BlockFlatTileRotatingTicking(CollisionEffect collisionEffect, TickEffect tickEffect, int tickRate) {
        
        this(BLOCK_PROPERTIES, collisionEffect, tickEffect, tickRate);
    }
    
    public BlockFlatTileRotatingTicking(Properties properties, CollisionEffect collisionEffect, TickEffect tickEffect, int tickRate) {
        
        super(properties, collisionEffect);
        this.tickEffect = tickEffect;
        this.tickRate = tickRate;
    }
    
    public int getTickRate () {
        
        return this.tickRate;
    }
    
    @Override
    public void tick (BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        
        if (this.tickEffect != null) {
            
            this.tickEffect.apply(state, world, pos);
        }
    }
    
    @Override
    public boolean eventReceived (BlockState state, World worldIn, BlockPos pos, int id, int param) {
        
        super.eventReceived(state, worldIn, pos, id, param);
        final TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }
    
    @Override
    public boolean hasTileEntity (BlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createTileEntity (BlockState state, IBlockReader world) {
        
        return new TileEntityTickingEffect();
    }
}