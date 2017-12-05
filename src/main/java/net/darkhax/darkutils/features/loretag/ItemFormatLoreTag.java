package net.darkhax.darkutils.features.loretag;

import net.darkhax.bookshelf.item.ICustomModel;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.GuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFormatLoreTag extends Item implements ICustomModel {

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
    public String getItemStackDisplayName (ItemStack stack) {

        final TextFormatting format = getFormatting(stack);
        return (format != null ? format : "") + super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (CreativeTabs tab, NonNullList<ItemStack> subItems) {

        if (this.isInCreativeTab(tab)) {

            for (int meta = 0; meta < this.variants.length; meta++) {

                subItems.add(new ItemStack(this, 1, meta));
            }
        }
    }

    public static TextFormatting getFormatting (ItemStack stack) {

        return TextFormatting.values()[stack.getMetadata()];
    }

    public static LoreType getLore (ItemStack stack) {

        return LoreType.values()[stack.getMetadata()];
    }

    @Override
    public void registerMeshModels () {

        for (int meta = 0; meta < this.variants.length; meta++) {

            DarkUtils.REGISTRY.registerInventoryModel(this, meta, this.getRegistryName().toString());
        }
    }
}