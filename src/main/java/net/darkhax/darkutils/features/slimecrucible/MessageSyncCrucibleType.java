package net.darkhax.darkutils.features.slimecrucible;

import net.minecraft.util.ResourceLocation;

/**
 * This packet is used to synchronize the slime crucible type from the server container to the
 * client.
 */
public class MessageSyncCrucibleType {
    
    /**
     * The Id of the crucible to sync.
     */
    private final ResourceLocation crucibleId;
    
    public MessageSyncCrucibleType(ResourceLocation crucibleId) {
        
        this.crucibleId = crucibleId;
    }
    
    /**
     * Gets the type of slime crucible being synced to the client.
     * 
     * @return The Id of the slime crucible type being synced.
     */
    public ResourceLocation getCrucibleId () {
        
        return this.crucibleId;
    }
}
