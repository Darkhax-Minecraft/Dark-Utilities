package net.darkhax.darkutils.items;

import java.util.List;

import net.darkhax.bookshelf.lib.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockCake extends ItemBlock {
    
    public final Block theBlock;
    
    public ItemBlockCake(Block block) {
        
        super(block);
        this.theBlock = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata (int damage) {
        
        return damage;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        Utilities.wrapStringToList(I18n.translateToLocal("tooltip." + this.theBlock.getUnlocalizedName() + ".desc"), 35, false, tooltip);
    }
}