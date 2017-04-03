package net.darkhax.darkutils.features.nullcharm;

import java.util.List;

import baubles.api.BaubleType;
import net.darkhax.bookshelf.item.ItemInventory;
import net.darkhax.bookshelf.lib.modutils.baubles.ItemBauble;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNullCharm extends ItemBauble {

    public ItemNullCharm () {

        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        
        if (!world.isRemote) {
            player.openGui(DarkUtils.instance, GuiHandler.FILTER, world, 0, 0, 0);
        }

        return super.onItemRightClick(world, player, handIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        for (final ItemStack blacklisted : ItemInventory.getContents(stack)) {
            tooltip.add(blacklisted.getDisplayName());
        }
    }

    public static boolean isBlackListed (ItemStack stack, ItemStack charmStack) {

        for (final ItemStack blacklisted : ItemInventory.getContents(charmStack))
            if (ItemStackUtils.areStacksEqual(stack, blacklisted, true))
                return true;

        return false;
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public BaubleType getBaubleType (ItemStack itemstack) {

        return BaubleType.CHARM;
    }
}
