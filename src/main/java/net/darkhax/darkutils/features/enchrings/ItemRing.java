package net.darkhax.darkutils.features.enchrings;

import java.util.List;

import net.darkhax.bookshelf.item.ItemSubType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO add baubles back
public class ItemRing extends ItemSubType {

    public static String[] varients = new String[] { "pyro", "engineer", "depth", "titan", "protect", "angler", "frost" };

    public ItemRing () {

        super(varients);
        this.maxStackSize = 1;
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
}
