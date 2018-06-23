package net.darkhax.darkutils.features.charms;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaubleItem;
import baubles.api.cap.BaublesCapabilities;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class BaubleCapabilityHandler implements ICapabilityProvider {

    public static final BaubleCapabilityHandler INSTANCE = new BaubleCapabilityHandler();

    private static IBauble bauble = new BaubleItem(FeatureCharms.restrictBaubles ? BaubleType.CHARM : BaubleType.TRINKET);

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {

        return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE;
    }

    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {

        return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE ? BaublesCapabilities.CAPABILITY_ITEM_BAUBLE.cast(bauble) : null;
    }
}