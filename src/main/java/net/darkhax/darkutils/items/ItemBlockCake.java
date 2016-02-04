package net.darkhax.darkutils.items;

import java.util.List;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockCake extends ItemBlockBasic {
    
    public ItemBlockCake(Block block, String[] names) {
        
        super(block, names);
    }
    
    @Override
    public String getUnlocalizedName (ItemStack stack) {
        
        return super.getUnlocalizedName();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        
    }
}