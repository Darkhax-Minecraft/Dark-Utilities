package net.darkhax.darkutilities;

import net.darkhax.bookshelf.api.ClientServices;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.renderer.RenderType;

public class ContentClient {

    private final Content content;

    public ContentClient(Content content) {

        this.content = content;
    }

    public void registerBlockLayers() {

        ClientServices.CLIENT.setRenderType(this.content.filterPlayer, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterUndead, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterArthropod, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterIllager, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterRaider, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterHostile, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterAnimal, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterBaby, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterPet, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterSlime, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterVillager, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterFireImmune, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterGolem, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterWater, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterNamed, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterFreezeImmune, RenderType.translucent());
        ClientServices.CLIENT.setRenderType(this.content.filterEquipment, RenderType.translucent());
        ClientServices.CLIENT.setRenderType((this.content.filterPassenger), RenderType.translucent());
    }
}