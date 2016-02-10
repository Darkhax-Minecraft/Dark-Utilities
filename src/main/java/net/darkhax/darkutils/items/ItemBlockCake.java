package net.darkhax.darkutils.items;

import java.util.List;

import net.darkhax.bookshelf.lib.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
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
    
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        Utilities.wrapStringToList(StatCollector.translateToLocal("tooltip." + theBlock.getUnlocalizedName() + ".desc"), 35, false, tooltip);
    }
}