package net.darkhax.darkutils;

import net.darkhax.bookshelf.client.model.FullbrightBakedModel;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.registry.RegistryHelperClient;
import net.darkhax.darkutils.features.enderhopper.RenderEnderHopper;
import net.darkhax.darkutils.features.enderhopper.TileEntityEnderHopper;
import net.darkhax.darkutils.features.slimecrucible.RenderSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.ScreenSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;
import net.darkhax.darkutils.features.witherslime.EntitySlimeWither;
import net.darkhax.darkutils.features.witherslime.RenderSlimeWither;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class ContentClient extends Content {
    
    public ContentClient(RegistryHelper registry) {
        
        super(registry);
        
        if (registry instanceof RegistryHelperClient) {
            
            final RegistryHelperClient clientRegistry = (RegistryHelperClient) registry;
            
            // Tile Entity Renders
            clientRegistry.setSpecialRenderer(TileEntitySlimeCrucible.class, new RenderSlimeCrucible());
            clientRegistry.setSpecialRenderer(TileEntityEnderHopper.class, new RenderEnderHopper());
            
            // Gui Screens
            clientRegistry.registerGuiScreen(this.containerSlimeCrucible, ScreenSlimeCrucible::new);
            
            // Entities
            clientRegistry.registerEntityRenderer(EntitySlimeWither.class, RenderSlimeWither::new);
            
            // Block Model Overrides
            this.makeFullBrightFlatTile(clientRegistry, this.vectorPlate);
            this.makeFullBrightFlatTile(clientRegistry, this.vectorPlateFast);
            this.makeFullBrightFlatTile(clientRegistry, this.vectorPlateHyper);
            this.makeFullBrightFlatTile(clientRegistry, this.importPlate);
            this.makeFullBrightFlatTile(clientRegistry, this.importPlateFast);
            this.makeFullBrightFlatTile(clientRegistry, this.importPlateHyper);
            this.makeFullBrightFlatTile(clientRegistry, this.exportPlate);
            this.makeFullBrightFlatTile(clientRegistry, this.exportPlateFast);
            this.makeFullBrightFlatTile(clientRegistry, this.exportPlateHyper);
            this.makeFullBrightFlatTile(clientRegistry, this.runeDamage);
            this.makeFullBrightFlatTile(clientRegistry, this.runeDamagePlayer);
            this.makeFullBrightFlatTile(clientRegistry, this.runePoison);
            this.makeFullBrightFlatTile(clientRegistry, this.runeWeakness);
            this.makeFullBrightFlatTile(clientRegistry, this.runeSlowness);
            this.makeFullBrightFlatTile(clientRegistry, this.runeWither);
            this.makeFullBrightFlatTile(clientRegistry, this.runeFire);
            this.makeFullBrightFlatTile(clientRegistry, this.runeFatigue);
            this.makeFullBrightFlatTile(clientRegistry, this.runeGlowing);
            this.makeFullBrightFlatTile(clientRegistry, this.runeHunger);
            this.makeFullBrightFlatTile(clientRegistry, this.runeBlindness);
            this.makeFullBrightFlatTile(clientRegistry, this.runeNausea);
            this.makeFullBrightFlatTile(clientRegistry, this.anchorPlate);
        }
        
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::addTooltips);
    }
    
    private void addTooltips (ItemTooltipEvent event) {
        
        final ResourceLocation id = event.getItemStack().getItem().getRegistryName();
        
        if ("darkutils".equals(id.getNamespace())) {
            
            event.getToolTip().add(new TranslationTextComponent("tooltip.darkutils." + id.getPath() + ".short").applyTextStyle(TextFormatting.GRAY));
        }
    }
    
    private void makeFullBrightFlatTile (RegistryHelperClient registry, Block tile) {
        
        final ResourceLocation blockId = tile.getRegistryName();
        registry.registerModelFactory(blockId, (original, ctx) -> {
            
            return new FullbrightBakedModel(original, 0.007f, new ResourceLocation(blockId.getNamespace(), "block/" + blockId.getPath()));
        });
    }
}