package net.darkhax.darkutils.libs;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionReferences {
    
    public static ItemStack AWKWARD = new ItemStack(Items.potionitem, 1, 16);
    public static ItemStack WITHER_I_0_45 = ItemStackUtils.writePotionEffectsToStack(new ItemStack(Items.potionitem, 1, 8204), new PotionEffect[] { new PotionEffect(Potion.wither.id, 900, 0) });
    public static ItemStack WITHER_I_2_00 = ItemStackUtils.writePotionEffectsToStack(new ItemStack(Items.potionitem, 1, 8204), new PotionEffect[] { new PotionEffect(Potion.wither.id, 2400, 0) });
    public static ItemStack WITHER_II_0_22 = ItemStackUtils.writePotionEffectsToStack(new ItemStack(Items.potionitem, 1, 8204), new PotionEffect[] { new PotionEffect(Potion.wither.id, 440, 1) });
    public static ItemStack WITHER_SPLASH_I_0_33 = ItemStackUtils.writePotionEffectsToStack(new ItemStack(Items.potionitem, 1, 16428), new PotionEffect[] { new PotionEffect(Potion.wither.id, 660, 0) });
    public static ItemStack WITHER_SPLASH_I_1_30 = ItemStackUtils.writePotionEffectsToStack(new ItemStack(Items.potionitem, 1, 16428), new PotionEffect[] { new PotionEffect(Potion.wither.id, 1800, 0) });
    public static ItemStack WITHER_SPLASH_II_0_16 = ItemStackUtils.writePotionEffectsToStack(new ItemStack(Items.potionitem, 1, 16428), new PotionEffect[] { new PotionEffect(Potion.wither.id, 320, 1) });
}