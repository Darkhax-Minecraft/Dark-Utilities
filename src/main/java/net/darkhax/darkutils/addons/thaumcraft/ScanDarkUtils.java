package net.darkhax.darkutils.addons.thaumcraft;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import thaumcraft.api.research.IScanThing;

public class ScanDarkUtils implements IScanThing {
    
    @Override
    public boolean checkThing (EntityPlayer player, Object object) {
        
        if (object != null) {
            
            if (object instanceof BlockPos) {
                
                Block block = player.getEntityWorld().getBlockState((BlockPos) object).getBlock();
                return Utilities.getModName(block).equalsIgnoreCase("darkutils");
            }
            
            if (object instanceof EntityItem) {
                
                ItemStack stack = ((EntityItem) object).getEntityItem();
                
                if (ItemStackUtils.isValidStack(stack))
                    return Utilities.getModName(stack.getItem()).equalsIgnoreCase("darkutils");
            }
        }
        
        System.out.println(object.getClass().getName());
        return false;
    }
    
    @Override
    public String getResearchKey () {
        
        return "DARKUTILS_LANDING";
    }
}