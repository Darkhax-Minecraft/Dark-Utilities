package net.darkhax.darkutils.features.slimecrucible;

import net.minecraft.util.ResourceLocation;

public class MessageSyncCrucibleType {
    
    private final ResourceLocation crucibleId;
    
    public MessageSyncCrucibleType(ResourceLocation crucibleId) {
        
        this.crucibleId = crucibleId;
    }
    
    public ResourceLocation getCrucibleId () {
        
        return this.crucibleId;
    }
}
