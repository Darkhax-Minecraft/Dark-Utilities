package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.tileentity.TileEntityShulkerPearl;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockShulkerPearl extends BlockContainer {
    
    public BlockShulkerPearl() {
        
        super(Material.GLASS);
        this.setCreativeTab(DarkUtils.TAB);
        this.setLightLevel(0.9375F);
    }
    
    @Override
    public TileEntity createNewTileEntity (World worldIn, int meta) {
        
        return new TileEntityShulkerPearl(worldIn);
    }
}
