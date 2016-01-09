package net.darkhax.darkutils.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrapTile extends Block {
    
    public static final PropertyEnum<BlockTrapTile.EnumType> VARIANT = PropertyEnum.<BlockTrapTile.EnumType> create("variant", BlockTrapTile.EnumType.class);
    
    public BlockTrapTile() {
        
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockTrapTile.EnumType.POISON));
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setUnlocalizedName("trap");
        this.setHardness(3.0F);
        this.setResistance(120f);
        this.setHarvestLevel("pickaxe", 1);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0125F, 0.9375F);
    }
    
    private boolean checkForDrop (World world, BlockPos pos, IBlockState state) {
        
        if (!this.canBlockStay(world, pos)) {
            
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return false;
        }
        
        else
            return true;
    }
    
    private boolean canBlockStay (World world, BlockPos pos) {
        
        return !(world.isAirBlock(pos.down()) || !world.isSideSolid(pos.down(), EnumFacing.UP));
    }
    
    @Override
    public int damageDropped (IBlockState state) {
        
        return ((BlockTrapTile.EnumType) state.getValue(VARIANT)).meta;
    }
    
    @Override
    public boolean canPlaceBlockAt (World world, BlockPos pos) {
        
        return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos);
    }
    
    @Override
    public void onNeighborBlockChange (World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        
        this.checkForDrop(world, pos, state);
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(VARIANT, BlockTrapTile.EnumType.fromMeta(meta));
    }
    
    @Override
    public MapColor getMapColor (IBlockState state) {
        
        return MapColor.obsidianColor;
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return ((BlockTrapTile.EnumType) state.getValue(VARIANT)).meta;
    }
    
    @Override
    protected BlockState createBlockState () {
        
        return new BlockState(this, new IProperty[] { VARIANT });
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox (World worldIn, BlockPos pos, IBlockState state) {
        
        return null;
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
                effect = new PotionEffect(Potion.moveSlowdown.id, 60);
                
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
        
        for (BlockTrapTile.EnumType type : BlockTrapTile.EnumType.values())
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