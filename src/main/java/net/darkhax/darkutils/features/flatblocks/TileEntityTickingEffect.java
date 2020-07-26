package net.darkhax.darkutils.features.flatblocks;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;

public class TileEntityTickingEffect extends TileEntity implements ITickableTileEntity {
    
    private byte timer;
    
    public TileEntityTickingEffect() {
        
        super(DarkUtils.content.tileTickingEffect);
    }
    
    public TileEntityTickingEffect(TileEntityType<?> tileEntityTypeIn) {
        
        super(tileEntityTypeIn);
    }
    
    @Override
    public void tick () {
        
        if (this.world instanceof ServerWorld && !this.world.isBlockPowered(this.pos)) {
            
            this.timer++;
            
            final Block block = this.getBlockState().getBlock();
            
            if (block instanceof BlockFlatTileRotatingTicking && this.timer >= ((BlockFlatTileRotatingTicking) block).getTickRate()) {
                
                block.tick(this.getBlockState(), (ServerWorld) this.world, this.pos, this.world.rand);
                this.timer = 0;
            }
        }
        
        else {
            
            this.timer = 0;
        }
    }
}