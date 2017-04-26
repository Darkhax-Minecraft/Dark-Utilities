package net.darkhax.darkutils.features;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.darkutils.handler.ConfigurationHandler;
import net.minecraftforge.common.MinecraftForge;

public class FeatureManager {

    public static final List<Feature> FEATURES = new ArrayList<>();

    /**
     * Registers a new feature with the feature manager. This will automatically create an
     * entry in the configuration file to enable/disable this feature. If the feature has been
     * disabled, it will not be registered. This will also handle event bus subscriptions.
     *
     * @param feature The feature being registered.
     * @param name The name of the feature.
     * @param description A short description of the feature.
     */
    public static void registerFeature (Feature feature, String name, String description) {

        feature.enabled = ConfigurationHandler.isFeatureEnabled(feature, name, description);

        if (feature.enabled) {

            feature.configName = name.toLowerCase().replace(' ', '_');
            FEATURES.add(feature);

            if (feature.usesEvents())
                MinecraftForge.EVENT_BUS.register(feature);
        }
    }
}