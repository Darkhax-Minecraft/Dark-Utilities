package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAntiSlime extends BlockContainer {
    
    public BlockAntiSlime() {
        
        super(Material.clay);
        this.setUnlocalizedName("darkutils.antislime");
        this.setHardness(3.0F);
        this.setResistance(10f);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(DarkUtils.tab);
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntityAntiSlime();
    }
    
    @Override
    public boolean isFullCube () {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube () {
        
        return false;
    }
    
    @Override
    public int getRenderType () {
        
        return 3;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer () {
        
        return EnumWorldBlockLayer.CUTOUT;
    }
}
