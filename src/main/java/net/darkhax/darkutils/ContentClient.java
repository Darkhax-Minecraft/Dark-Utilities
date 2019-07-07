package net.darkhax.darkutils;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.registry.RegistryHelperClient;
import net.darkhax.darkutils.features.slimecrucible.RenderSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.ScreenSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;

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
}