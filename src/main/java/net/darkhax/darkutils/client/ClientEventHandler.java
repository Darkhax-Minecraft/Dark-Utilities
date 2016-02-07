package net.darkhax.darkutils.client;

import net.darkhax.darkutils.client.model.ModelSneakyBlock;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {
    
    @SubscribeEvent
    public void onModelBake (ModelBakeEvent event) {
        
        System.out.println("Baked Bitches");
        event.modelRegistry.putObject(new ModelResourceLocation("darkutils:sneaky", "normal"), new ModelSneakyBlock());
    }
}