package net.darkhax.darkutils.features.charms;

import net.darkhax.bookshelf.item.ItemInventory;
import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemNullCharm extends ItemCharm {

    @Override
    public ActionResult<ItemStack> onItemRightClick (World world, EntityPlayer player, EnumHand handIn) {

        if (!world.isRemote) {
            player.openGui(DarkUtils.instance, GuiHandler.FILTER, world, 0, 0, 0);
        }

        return super.onItemRightClick(world, player, handIn);
    }

    public static boolean isBlackListed (ItemStack stack, ItemStack charmStack) {

        for (final ItemStack blacklisted : ItemInventory.getContents(charmStack)) {
            if (StackUtils.areStacksEqual(stack, blacklisted, true)) {
                return true;
            }
        }

        return false;
    }
}
