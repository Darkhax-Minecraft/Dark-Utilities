package net.darkhax.darkutils;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.registry.RegistryHelperClient;
import net.darkhax.darkutils.features.slimecrucible.RenderSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.ScreenSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ContentClient extends Content {
    
    public ContentClient(RegistryHelper registry) {
        
        super(registry);
        
        if (registry instanceof RegistryHelperClient) {
            
            final RegistryHelperClient clientRegistry = (RegistryHelperClient) registry;
            
            // Tile Entity Renders
            clientRegistry.setSpecialRenderer(TileEntitySlimeCrucible.class, new RenderSlimeCrucible());
            
            // Gui Screens
            clientRegistry.registerGuiScreen(this.containerSlimeCrucible, ScreenSlimeCrucible::new);
        }
    }
    
    public static void addTooltips(ItemTooltipEvent event) {
        
        ResourceLocation id = event.getItemStack().getItem().getRegistryName();
        
        if ("darkutils".equals(id.getNamespace())) {
            
            event.getToolTip().add(new TranslationTextComponent("tooltip.darkutils." + id.getPath() + ".short").applyTextStyle(TextFormatting.GRAY));
        }
    }
}