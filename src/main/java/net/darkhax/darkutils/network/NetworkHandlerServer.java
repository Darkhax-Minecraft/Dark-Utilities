package net.darkhax.darkutils.network;

import net.darkhax.darkutils.features.slimecrucible.MessageSyncCrucibleType;
import net.minecraft.network.PacketBuffer;

public class NetworkHandlerServer {
    
    public static void encodeStageMessage (MessageSyncCrucibleType packet, PacketBuffer buffer) {
        
        buffer.writeResourceLocation(packet.getCrucibleId());
    }
}
