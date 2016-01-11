package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.tileentity.TileEntityDog;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDog extends BlockContainer {
    
    private static final Material DOG = new Material(MapColor.snowColor);
    
    public BlockDog() {
        
        super(DOG);
        this.setUnlocalizedName("darkutils.dog");
        this.setHardness(50.0F);
        this.setResistance(2000.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntityDog();
    }
}