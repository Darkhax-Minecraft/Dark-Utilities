package net.darkhax.darkutils.features.loretag;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFormatLoreTag extends Item {

    private final String[] variants = { "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray", "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white" };

    public ItemFormatLoreTag () {

        this.setMaxStackSize(16);
        this.setHasSubtypes(true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick (World world, EntityPlayer player, EnumHand handIn) {

        player.openGui(DarkUtils.instance, GuiHandler.LORE_TAG, world, 0, 0, 0);
        return super.onItemRightClick(world, player, handIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName (ItemStack stack) {

        final ChatFormatting format = getTagFormatting(stack);
        return super.getUnlocalizedName() + "." + format.getName().replace("_", "");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName (ItemStack stack) {

        final ChatFormatting format = getTagFormatting(stack);
        return (format != null ? format : "") + super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (CreativeTabs tab, NonNullList<ItemStack> subItems) {

        if (this.isInCreativeTab(tab)) {

            for (final String format : this.variants) {

                final ItemStack stack = new ItemStack(this);
                final NBTTagCompound tag = StackUtils.prepareStackTag(stack);
                tag.setString("format", format);
                subItems.add(stack);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static ChatFormatting getTagFormatting (ItemStack stack) {

        final NBTTagCompound tag = StackUtils.prepareStackTag(stack);
        final ChatFormatting format = ChatFormatting.getByName(tag.getString("format"));
        return format != null ? format : ChatFormatting.WHITE;
    }
}