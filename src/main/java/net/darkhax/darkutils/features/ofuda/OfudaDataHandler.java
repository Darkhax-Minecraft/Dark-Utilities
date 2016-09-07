package net.darkhax.darkutils.features.ofuda;

import net.darkhax.darkutils.libs.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OfudaDataHandler {
    
    @CapabilityInject(ICustomData.class)
    public static final Capability<ICustomData> CUSTOM_DATA = null;
    
    public static void init () {
        
        CapabilityManager.INSTANCE.register(ICustomData.class, new Storage(), Default.class);
        MinecraftForge.EVENT_BUS.register(new OfudaDataHandler());
    }
    
    @SubscribeEvent
    public void attachCapabilities (AttachCapabilitiesEvent.Entity event) {
        
        if (event.getEntity() instanceof EntityLivingBase)
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "ofuda"), new Provider());
    }
    
    public static interface ICustomData {
        
        boolean isBound ();
        
        boolean isSilenced ();
        
        boolean isWarded ();
        
        boolean isDenounced ();
        
        boolean isConfined ();
        
        void setBound (boolean value);
        
        void setSilenced (boolean value);
        
        void setWarded (boolean value);
        
        void setDenounced (boolean value);
        
        void setConfined (boolean value);
    }
    
    /**
     * Default implementation of the custom data.
     */
    public static class Default implements ICustomData {
        
        private boolean isBound;
        private boolean isSilenced;
        private boolean isWarded;
        private boolean isDenounced;
        private boolean isConfined;
        
        @Override
        public boolean isBound () {
            
            return this.isBound;
        }
        
        @Override
        public boolean isSilenced () {
            
            return this.isSilenced;
        }
        
        @Override
        public boolean isWarded () {
            
            return this.isWarded;
        }
        
        @Override
        public boolean isDenounced () {
            
            return this.isDenounced;
        }
        
        @Override
        public boolean isConfined () {
            
            return this.isConfined;
        }
        
        @Override
        public void setBound (boolean value) {
            
            this.isBound = value;
        }
        
        @Override
        public void setSilenced (boolean value) {
            
            this.isSilenced = value;
        }
        
        @Override
        public void setWarded (boolean value) {
            
            this.isWarded = value;
        }
        
        @Override
        public void setDenounced (boolean value) {
            
            this.isDenounced = value;
        }
        
        @Override
        public void setConfined (boolean value) {
            
            this.isConfined = value;
        }
    }
    
    /**
     * Handles read and/write of custom data.
     */
    public static class Storage implements Capability.IStorage<ICustomData> {
        
        @Override
        public NBTBase writeNBT (Capability<ICustomData> capability, ICustomData instance, EnumFacing side) {
            
            final NBTTagCompound tag = new NBTTagCompound();
            
            tag.setBoolean("isBound", instance.isBound());
            tag.setBoolean("isSilenced", instance.isSilenced());
            tag.setBoolean("isWarded", instance.isWarded());
            tag.setBoolean("isDenounced", instance.isDenounced());
            tag.setBoolean("isConfined", instance.isConfined());
            
            return tag;
        }
        
        @Override
        public void readNBT (Capability<ICustomData> capability, ICustomData instance, EnumFacing side, NBTBase nbt) {
            
            final NBTTagCompound tag = (NBTTagCompound) nbt;
            instance.setBound(tag.getBoolean("isBound"));
            instance.setSilenced(tag.getBoolean("isSilenced"));
            instance.setWarded(tag.getBoolean("isWarded"));
            instance.setDenounced(tag.getBoolean("isDenounced"));
            instance.setConfined(tag.getBoolean("isConfined"));
        }
    }
    
    /**
     * Handles all the checks and delegate methods for the capability.
     */
    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
        
        ICustomData instance = CUSTOM_DATA.getDefaultInstance();
        
        @Override
        public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
            
            return capability == CUSTOM_DATA;
        }
        
        @Override
        public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
            
            return this.hasCapability(capability, facing) ? CUSTOM_DATA.<T> cast(this.instance) : null;
        }
        
        @Override
        public NBTTagCompound serializeNBT () {
            
            return (NBTTagCompound) CUSTOM_DATA.getStorage().writeNBT(CUSTOM_DATA, this.instance, null);
        }
        
        @Override
        public void deserializeNBT (NBTTagCompound nbt) {
            
            CUSTOM_DATA.getStorage().readNBT(CUSTOM_DATA, this.instance, null, nbt);
        }
    }
}