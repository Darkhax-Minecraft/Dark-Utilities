package net.darkhax.darkutils.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockFilter extends ItemBlockBasic {
    
    public ItemBlockFilter(Block block, String[] names) {
        
        super(block, names);
    }
    
    @Override
    public String getUnlocalizedName (ItemStack stack) {
        
        return super.getUnlocalizedName();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        if (!(stack.getMetadata() > this.names.length))
            tooltip.add(I18n.translateToLocal("tooltip.darkutils.filter.type") + ": " + ChatFormatting.AQUA + I18n.translateToLocal("tooltip.darkutils.filter.type." + this.names[stack.getMetadata()]));
    }
}