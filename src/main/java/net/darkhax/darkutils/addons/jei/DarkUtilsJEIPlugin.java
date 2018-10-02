package net.darkhax.darkutils.addons.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.bookshelf.registry.IVariant;
import net.darkhax.bookshelf.util.GameUtils;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class DarkUtilsJEIPlugin implements IModPlugin {

    @Override
    public void register (IModRegistry registry) {

        for (final Item item : DarkUtils.REGISTRY.getItems()) {

            if (item instanceof IVariant) {

                final String[] variants = ((IVariant) item).getVariant();

                for (int meta = 0; meta < variants.length; meta++) {

                    final String key = "jei." + item.getTranslationKey() + "." + variants[meta];
                    registry.addIngredientInfo(new ItemStack(item, 1, meta), ItemStack.class, key);
                    this.validateKey(key);
                }
            }

            else {

                final String key = "jei." + item.getTranslationKey();
                registry.addIngredientInfo(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), ItemStack.class, key);
                this.validateKey(key);
            }
        }
    }

    private void validateKey (String key) {

        if (GameUtils.isClient()) {

            if (!I18n.canTranslate(key)) {

                DarkUtils.LOGGER.info("Could not translate: " + key);
            }
        }
    }
}