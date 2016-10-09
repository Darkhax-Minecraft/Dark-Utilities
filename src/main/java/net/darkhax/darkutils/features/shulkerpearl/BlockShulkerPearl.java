package net.darkhax.darkutils.features.shulkerpearl;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockShulkerPearl extends Block {
    
    public static String[] types = new String[] { "default", "brick", "carved", "chiseled" };
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType> create("variant", EnumType.class);
    
    public BlockShulkerPearl() {
        
        super(Material.ROCK, MapColor.QUARTZ);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.DEFAULT));
        this.setHardness(0.45F);
        this.setResistance(5f);
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(1.0F);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item item, CreativeTabs tab, List<ItemStack> list) {
        
        for (int meta = 0; meta < 4; meta++)
            list.add(new ItemStack(item, 1, meta));
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return state.getValue(VARIANT).getMetadata();
    }
    
    @Override
    protected BlockStateContainer createBlockState () {
        
        return new BlockStateContainer(this, new IProperty[] { VARIANT });
    }
    
    @Override
    public boolean isBeaconBase (IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
        
        return true;
    }
    
    public static enum EnumType implements IStringSerializable {
        
        DEFAULT(0, "default"),
        BRICK(1, "brick"),
        CARVED(2, "carved"),
        CHISELED(3, "chiseled");
        
        private static final EnumType[] META_LOOKUP = new EnumType[values().length];
        private final int meta;
        private final String name;
        
        private EnumType(int meta, String name) {
            
            this.meta = meta;
            this.name = name;
        }
        
        public int getMetadata () {
            
            return this.meta;
        }
        
        @Override
        public String toString () {
            
            return this.name;
        }
        
        public static EnumType byMetadata (int meta) {
            
            if (meta < 0 || meta >= META_LOOKUP.length)
                meta = 0;
            
            return META_LOOKUP[meta];
        }
        
        @Override
        public String getName () {
            
            return this.name;
        }
        
        static {
            
            for (final EnumType type : values())
                META_LOOKUP[type.getMetadata()] = type;
        }
    }
}
