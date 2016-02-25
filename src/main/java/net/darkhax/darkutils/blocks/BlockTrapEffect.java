package net.darkhax.darkutils.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrapEffect extends BlockTrapBase {
    
    public static final PropertyEnum<BlockTrapEffect.EnumType> VARIANT = PropertyEnum.<BlockTrapEffect.EnumType> create("variant", BlockTrapEffect.EnumType.class);
    
    public BlockTrapEffect() {
        
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockTrapEffect.EnumType.POISON));
        this.setUnlocalizedName("darkutils.trap");
    }
    
    @Override
    public int damageDropped (IBlockState state) {
        
        return ((BlockTrapEffect.EnumType) state.getValue(VARIANT)).meta;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(VARIANT, BlockTrapEffect.EnumType.fromMeta(meta));
    }
    
    @Override
    public MapColor getMapColor (IBlockState state) {
        
        return MapColor.obsidianColor;
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return ((BlockTrapEffect.EnumType) state.getValue(VARIANT)).meta;
    }
    
    @Override
    protected BlockState createBlockState () {
        
        return new BlockState(this, new IProperty[] { VARIANT });
    }
    
    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity) {
        
        if (entity instanceof EntityLivingBase) {
            
            EntityLivingBase living = (EntityLivingBase) entity;
            PotionEffect effect = null;
            
            int type = getMetaFromState(state);
            
            if (type == 0)
                effect = new PotionEffect(Potion.poison.id, 60);
                
            if (type == 1)
                effect = new PotionEffect(Potion.weakness.id, 60);
                
            if (type == 2)
                living.attackEntityFrom(DamageSource.magic, 2.5f);
                
            if (type == 3)
                effect = new PotionEffect(Potion.moveSlowdown.id, 60, 2);
                
            if (type == 4)
                living.setFire(1);
                
            if (type == 5)
                effect = new PotionEffect(Potion.wither.id, 60);
                
            if (effect != null) {
                
                effect.setCurativeItems(new ArrayList<ItemStack>());
                living.addPotionEffect(effect);
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        
        for (BlockTrapEffect.EnumType type : BlockTrapEffect.EnumType.values())
            list.add(new ItemStack(itemIn, 1, type.meta));
    }
    
    public static enum EnumType implements IStringSerializable {
        
        POISON(0, "poison"),
        WEAKNESS(1, "weakness"),
        HARMING(2, "harming"),
        SLOWNESS(3, "slowness"),
        FIRE(4, "fire"),
        WITHER(5, "wither");
        
        private static String[] nameList;
        
        public final int meta;
        public final String type;
        
        private EnumType(int meta, String name) {
            
            this.meta = meta;
            this.type = name;
        }
        
        @Override
        public String getName () {
            
            return this.type;
        }
        
        @Override
        public String toString () {
            
            return this.type;
        }
        
        public static EnumType fromMeta (int meta) {
            
            for (EnumType type : EnumType.values())
                if (type.meta == meta)
                    return type;
                    
            return POISON;
        }
        
        public static String[] getTypes () {
            
            if (nameList != null)
                return nameList;
                
            List<String> names = new ArrayList<String>();
            
            for (EnumType type : EnumType.values())
                names.add(type.type);
                
            nameList = names.toArray(new String[names.size()]);
            return nameList;
        }
    }
}