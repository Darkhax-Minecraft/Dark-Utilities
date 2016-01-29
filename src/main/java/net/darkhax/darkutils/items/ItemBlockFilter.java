package net.darkhax.darkutils.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
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
        
        if (!(stack.getMetadata() > names.length))
            tooltip.add(StatCollector.translateToLocal("tooltip.darkutils.filter.type") + ": " + EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.darkutils.filter.type." + names[stack.getMetadata()]));
    }
}