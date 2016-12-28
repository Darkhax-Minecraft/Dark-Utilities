package net.darkhax.darkutils.features.material;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMaterial extends Item {

    public static String[] varients = new String[] { "wither", "unstable", "cream" };

    public ItemMaterial() {

        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int damage) {

        return damage;
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
    public void getSubItems (Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {

        for (int meta = 0; meta < varients.length; meta++)
            subItems.add(new ItemStack(this, 1, meta));
    }
}