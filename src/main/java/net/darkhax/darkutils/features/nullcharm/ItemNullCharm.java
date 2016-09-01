package net.darkhax.darkutils.features.nullcharm;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemNullCharm extends Item {
    
    @Override
    public ActionResult<ItemStack> onItemRightClick (ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        
        if (!world.isRemote)
            player.openGui(DarkUtils.instance, GuiHandler.NULL_CHARM, world, 0, 0, 0);
            
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
    }
}
