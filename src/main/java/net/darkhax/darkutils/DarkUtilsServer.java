package net.darkhax.darkutils;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;

public class DarkUtilsServer {

    public void preInit () {

        for (final Feature feature : FeatureManager.getFeatures()) {
            
            feature.onPreInit();
        }
    }

    public void init () {

        for (final Feature feature : FeatureManager.getFeatures()) {
            
            feature.onInit();
        }
    }

    public void postInit () {

        for (final Feature feature : FeatureManager.getFeatures()) {
            
            feature.onPostInit();
        }
    }
}
