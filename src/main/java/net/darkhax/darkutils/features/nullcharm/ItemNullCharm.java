package net.darkhax.darkutils.features.nullcharm;

import java.util.List;

import net.darkhax.bookshelf.inventory.InventoryItem;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNullCharm extends Item {
    
    @Override
    public ActionResult<ItemStack> onItemRightClick (ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        
        if (!world.isRemote)
            player.openGui(DarkUtils.instance, GuiHandler.FILTER, world, 0, 0, 0);
            
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        for (final ItemStack blacklisted : InventoryItem.getContents(stack))
            tooltip.add(blacklisted.getDisplayName());
    }
    
    public static boolean isBlackListed (ItemStack stack, ItemStack charmStack) {
        
        for (final ItemStack blacklisted : InventoryItem.getContents(charmStack))
            if (ItemStackUtils.areStacksEqual(stack, blacklisted, true))
                return true;
                
        return false;
    }
}
