package net.darkhax.darkutils.features.ofuda;

import java.util.List;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOfuda extends Item {
    
    public static String[] varients = new String[] { "bind", "silence", "smite", "ward", "denounce", "confine"};
    
    public ItemOfuda() {
        
        this.setCreativeTab(DarkUtils.TAB);
        this.setUnlocalizedName("darkutils.ofuda");
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        
        return false;
    }
    
    @Override
    public int getMetadata (int damage) {
        
        return damage;
    }
    
    @Override
    public String getUnlocalizedName (ItemStack stack) {
        
        final int meta = stack.getMetadata();
        
        if (!(meta >= 0 && meta < varients.length))
            return super.getUnlocalizedName() + "." + varients[0];
            
        return super.getUnlocalizedName() + "." + varients[meta];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        
        for (int meta = 0; meta < varients.length; meta++)
            subItems.add(new ItemStack(this, 1, meta));
    }
}