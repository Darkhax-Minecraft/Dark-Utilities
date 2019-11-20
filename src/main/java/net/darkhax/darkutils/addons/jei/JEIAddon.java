package net.darkhax.darkutils.addons.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIAddon implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation("darkutils", "jei_support");
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        for (Item item : DarkUtils.registry.getItems()) {
            
            registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, "tooltip.darkutils." + item.getRegistryName().getPath() + ".long");
        }
    }
    
    @Override
    public ResourceLocation getPluginUid () {
        
        return ID;
    }
}