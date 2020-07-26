package net.darkhax.darkutils;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ContentClient extends Content {
    
    public ContentClient(RegistryHelper registry) {
        
        super(registry);
        
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::addTooltips);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }
    
    private void addTooltips (ItemTooltipEvent event) {
        
        final ItemStack stack = event.getItemStack();
        
        if (!stack.isEmpty() && stack.getItem() != null) {
            
            final ResourceLocation id = stack.getItem().getRegistryName();
            
            if (id != null && "darkutils".equals(id.getNamespace())) {
                
                event.getToolTip().add(new TranslationTextComponent("tooltip.darkutils." + id.getPath() + ".short").func_240699_a_(TextFormatting.GRAY));
            }
        }
    }
    
    private void onClientSetup (FMLClientSetupEvent event) {
        
        RenderTypeLookup.setRenderLayer(this.vectorPlate, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.exportPlate, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.importPlate, RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(this.vectorPlateFast, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.exportPlateFast, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.importPlateFast, RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(this.vectorPlateHyper, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.exportPlateHyper, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.importPlateHyper, RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(this.runeDamage, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeDamagePlayer, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runePoison, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeWeakness, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeSlowness, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeWither, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeFire, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeFatigue, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeGlowing, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeHunger, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeBlindness, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.runeNausea, RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(this.anchorPlate, RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(this.filterPlayer, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterUndead, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterArthropod, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterIllager, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterRaid, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterHostile, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterAnimal, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterChild, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterPet, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterSlime, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterBoss, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterVillager, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterFireImmune, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterExplosionImmune, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterGolem, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterWater, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(this.filterNamed, RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(this.itemGrate, RenderType.getCutout());
        
        RenderTypeLookup.setRenderLayer(this.darkGlass, RenderType.getTranslucent());
    }
}