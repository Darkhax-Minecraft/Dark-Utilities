package net.darkhax.darkutils.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFeeder extends ItemBlock {
    
    public final Block theBlock;
    
    public ItemBlockFeeder(Block block) {
        
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
    public String getUnlocalizedName (ItemStack stack) {
        
        return super.getUnlocalizedName() + "." + (stack.getMetadata() == 0 ? "empty" : stack.getMetadata() == 10 ? "full" : "partial");
    }
}