package net.darkhax.darkutils.features.filter;

import java.util.List;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
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
            tooltip.add(I18n.format("tooltip.darkutils.filter.type") + ": " + TextFormatting.AQUA + I18n.format("tooltip.darkutils.filter.type." + this.names[stack.getMetadata()]));
    }
}