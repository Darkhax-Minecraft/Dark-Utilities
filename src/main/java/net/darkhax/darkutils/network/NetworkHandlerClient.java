package net.darkhax.darkutils.network;

import java.util.function.Supplier;

import net.darkhax.darkutils.features.slimecrucible.ContainerSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.MessageSyncCrucibleType;
import net.darkhax.darkutils.features.slimecrucible.SlimeCrucibleType;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class NetworkHandlerClient {
    
    public static MessageSyncCrucibleType decodeStageMessage (PacketBuffer buffer) {
        
        return new MessageSyncCrucibleType(buffer.readResourceLocation());
    }
    
    public static void processSyncStagesMessage (MessageSyncCrucibleType message, Supplier<Context> context) {
        
        final Container openedContainer = Minecraft.getInstance().player.openContainer;
        final SlimeCrucibleType type = SlimeCrucibleType.getType(message.getCrucibleId());
        
        if (openedContainer instanceof ContainerSlimeCrucible && type != null) {
            
            ((ContainerSlimeCrucible) openedContainer).setCrucibleType(type);
        }
    }
}
