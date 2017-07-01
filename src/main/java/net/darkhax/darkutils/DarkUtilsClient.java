package net.darkhax.darkutils;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;

public class DarkUtilsClient extends DarkUtilsServer {

    @Override
    public void preInit () {

        super.preInit();
        
        for (final Feature feature : FeatureManager.getFeatures()) {
            
            feature.onClientPreInit();
        }
    }

    @Override
    public void init () {

        super.init();
        
        for (final Feature feature : FeatureManager.getFeatures()) {
            
            feature.onClientInit();
        }
    }

    @Override
    public void postInit () {

        super.postInit();
        
        for (final Feature feature : FeatureManager.getFeatures()) {
            
            feature.onClientPostInit();
        }
    }
}
