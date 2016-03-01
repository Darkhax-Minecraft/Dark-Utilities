package net.darkhax.darkutils.blocks;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.bookshelf.lib.util.EntityUtils;
import net.darkhax.bookshelf.lib.util.Utilities;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFilter extends Block {
    
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType> create("variant", EnumType.class);
    
    public BlockFilter() {
        
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.PLAYER));
        this.setUnlocalizedName("darkutils.filter");
        this.setCreativeTab(DarkUtils.tab);
        this.setHardness(3.0F);
        this.setResistance(10f);
        this.setHarvestLevel("pickaxe", 1);
    }
    
    @Override
    public int damageDropped (IBlockState state) {
        
        return ((EnumType) state.getValue(VARIANT)).meta;
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(VARIANT, EnumType.fromMeta(meta));
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return ((EnumType) state.getValue(VARIANT)).meta;
    }
    
    @Override
    protected BlockState createBlockState () {
        
        return new BlockState(this, new IProperty[] { VARIANT });
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
    public void addCollisionBoxesToList (World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
        
        int meta = state.getValue(VARIANT).meta;
        
        if (collidingEntity instanceof EntityLivingBase) {
            
            EntityLivingBase living = (EntityLivingBase) collidingEntity;
            
            if ((meta == 0 && living instanceof EntityPlayer) || (meta == 1 && living.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) || (meta == 2 && living.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) || (meta == 3 && living instanceof IMob) || (meta == 4 && living instanceof EntityAnimal) || (meta == 5 && (living instanceof EntityWaterMob || living instanceof EntityGuardian)) || (meta == 6 && living.isChild()) || (meta == 7 && living instanceof EntityTameable) || (meta == 8 && living instanceof EntitySlime)) {
                
                snagMob(living, pos);
                return;
            }
        }
        
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item item, CreativeTabs tab, List<ItemStack> list) {
        
        for (EnumType type : EnumType.values())
            list.add(new ItemStack(item, 1, type.meta));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer () {
        
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    private void snagMob (EntityLivingBase living, BlockPos pos) {
        
        if (Utilities.isFluid(living.getEntityWorld().getBlockState(pos.offset(EnumFacing.UP)).getBlock()))
            EntityUtils.pushTowards(living, pos.offset(EnumFacing.DOWN), 0.6f);
    }
    
    public static enum EnumType implements IStringSerializable {
        
        PLAYER(0, "player"),
        UNDEAD(1, "undead"),
        ARTHROPOD(2, "arthropod"),
        MONSTER(3, "monster"),
        ANIMAL(4, "animal"),
        WATER(5, "water"),
        BABY(6, "baby"),
        PET(7, "pet"),
        SLIME(8, "slime");
        
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
                    
            return PLAYER;
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