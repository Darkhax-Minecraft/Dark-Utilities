package net.darkhax.darkutils.client;

import net.darkhax.darkutils.common.ProxyCommon;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;

public class ProxyClient extends ProxyCommon {

    @Override
    public void onPreInit () {

        for (final Feature feature : FeatureManager.FEATURES)
            feature.onClientPreInit();
    }

    @Override
    public void onInit () {

        for (final Feature feature : FeatureManager.FEATURES)
            feature.onClientInit();
    }

    @Override
    public void onPostInit () {

        for (final Feature feature : FeatureManager.FEATURES)
            feature.onClientPostInit();
    }
}