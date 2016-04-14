package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAntiSlime extends BlockContainer {
    
    public BlockAntiSlime() {
        
        super(Material.ROCK);
        this.setUnlocalizedName("darkutils.antislime");
        this.setHardness(3.0F);
        this.setResistance(10f);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(DarkUtils.TAB);
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntityAntiSlime();
    }
    
    @Override
    public boolean isFullCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    public EnumBlockRenderType getRenderType (IBlockState state) {
        
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer () {
        
        return BlockRenderLayer.CUTOUT;
    }
}