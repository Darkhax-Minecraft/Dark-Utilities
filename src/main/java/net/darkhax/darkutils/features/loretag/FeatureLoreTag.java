package net.darkhax.darkutils.features.loretag;

import net.darkhax.bookshelf.BookshelfRegistry;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
    @SideOnly(Side.CLIENT)
    public void onClientInit () {

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LoreTagColor(), coloredLoreTag);
    }

    @SubscribeEvent
    public void onRecipeRegistry (RegistryEvent.Register<IRecipe> event) {

        final IRecipe recipe = new RecipeLoreTag();
        recipe.setRegistryName(new ResourceLocation("darkutils", "dye_lore_tag"));
        event.getRegistry().register(recipe);
    }

    @Override
    public boolean usesEvents () {

        return true;
    }
}
