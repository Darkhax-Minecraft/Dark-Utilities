package net.darkhax.darkutils.features.enchrings;

import java.util.List;

import baubles.api.BaubleType;
import net.darkhax.bookshelf.utils.baubles.ItemBauble;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRing extends ItemBauble {

    public static String[] varients = new String[] { "pyro", "engineer", "depth", "titan", "protect", "angler", "frost" };

    public ItemRing () {

        this.hasSubtypes = true;
        this.maxStackSize = 1;
    }

    @Override
    public String getUnlocalizedName (ItemStack stack) {

        final int meta = stack.getMetadata();

        if (!(meta >= 0 && meta < varients.length))
            return super.getUnlocalizedName() + "." + varients[0];

        return super.getUnlocalizedName() + "." + varients[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {

        for (int meta = 0; meta < varients.length; meta++) {
            subItems.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        tooltip.add(getEnchantmentFromMeta(stack.getMetadata()).getTranslatedName(1));
    }

    public static Enchantment getEnchantmentFromMeta (int meta) {

        switch (meta) {

            case 0:
                return Enchantments.FIRE_ASPECT;
            case 1:
                return Enchantments.EFFICIENCY;
            case 2:
                return Enchantments.DEPTH_STRIDER;
            case 3:
                return Enchantments.KNOCKBACK;
            case 4:
                return Enchantments.PROTECTION;
            case 5:
                return Enchantments.LUCK_OF_THE_SEA;
            case 6:
                return Enchantments.FROST_WALKER;
            default:
                return Enchantments.FIRE_ASPECT;
        }
    }

    @Override
    @Optional.Method(modid = "baubles")
    public BaubleType getBaubleType (ItemStack itemstack) {

        return BaubleType.RING;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip (ItemStack itemstack, EntityLivingBase player) {

        return FeatureEnchantedRing.allowBaubles;
    }
}
