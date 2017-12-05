package net.darkhax.darkutils.features.loretag;

import net.darkhax.bookshelf.BookshelfRegistry;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@DUFeature(name = "Lore Tag", description = "Tags which can set the lore of items")
public class FeatureLoreTag extends Feature {

    public static Item coloredLoreTag;

    @Override
    public void onPreInit () {

        DarkUtils.NETWORK.register(PacketSyncLore.class, Side.SERVER);
        coloredLoreTag = DarkUtils.REGISTRY.registerItem(new ItemFormatLoreTag(), "lore_tag");
        BookshelfRegistry.addAnvilRecipe(new AnvilRecipeLoreTag());
    }

    @Override
    public void onPreRecipe () {

        for (final LoreType lore : LoreType.values()) {

            DarkUtils.REGISTRY.addShapedRecipe("lore_tag_" + lore.name().toLowerCase(), new ItemStack(coloredLoreTag, 1, lore.ordinal()), "bs ", "su ", "  d", 'b', Items.BOOK, 's', OreDictUtils.STRING, 'u', OreDictUtils.SLIMEBALL, 'd', lore.getCrafting());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientInit () {

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LoreTagColor(), coloredLoreTag);
    }

    @Override
    public boolean usesEvents () {

        return true;
    }
}
