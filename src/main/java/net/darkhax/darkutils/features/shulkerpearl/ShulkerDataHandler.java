package net.darkhax.darkutils.features.shulkerpearl;

import net.darkhax.darkutils.libs.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityShulker;
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

public class ShulkerDataHandler {

    /**
     * The capability field. Used for checks and references. Initialized when the capability is
     * initialized.
     */
    @CapabilityInject(ICustomData.class)
    public static final Capability<ICustomData> CUSTOM_DATA = null;

    /**
     * Initializes the CustomDataHandler and sets everything up. Should be called in the
     * preInit loading phase.
     */
    public static void init () {

        CapabilityManager.INSTANCE.register(ICustomData.class, new Storage(), Default.class);
        MinecraftForge.EVENT_BUS.register(new ShulkerDataHandler());
    }

    /**
     * Called every time a target is constructed. Allows for capabilities to be attatched to
     * the target.
     *
     * @param event The forge event.
     */
    @SubscribeEvent
    public void attachCapabilities (AttachCapabilitiesEvent.Entity event) {

        if (event.getEntity() instanceof EntityShulker)
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "shulker_info"), new Provider());
    }

    /**
     * Gets the data from an entity.
     *
     * @param entity The entity to pull data from.
     * @return The data.
     */
    public static ICustomData getData (Entity entity) {

        return entity.getCapability(CUSTOM_DATA, EnumFacing.DOWN);
    }

    /**
     * Interface for holding various getter and setter methods.
     */
    public static interface ICustomData {

        int getCooldown ();

        void setCooldown (int cooldown);
    }

    /**
     * Default implementation of the custom data.
     */
    public static class Default implements ICustomData {

        private int cooldown = 0;

        @Override
        public int getCooldown () {

            return this.cooldown;
        }

        @Override
        public void setCooldown (int cooldown) {

            this.cooldown = cooldown;
        }
    }

    /**
     * Handles reand/write of custom data.
     */
    public static class Storage implements Capability.IStorage<ICustomData> {

        @Override
        public NBTBase writeNBT (Capability<ICustomData> capability, ICustomData instance, EnumFacing side) {

            final NBTTagCompound tag = new NBTTagCompound();

            tag.setInteger("cooldown", instance.getCooldown());

            return tag;
        }

        @Override
        public void readNBT (Capability<ICustomData> capability, ICustomData instance, EnumFacing side, NBTBase nbt) {

            final NBTTagCompound tag = (NBTTagCompound) nbt;
            instance.setCooldown(tag.getInteger("cooldown"));
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