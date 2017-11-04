package net.darkhax.darkutils.handler;

import java.io.File;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.FeatureManager;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

    private static Configuration config;

    public static boolean oredictVanillaFenceGate;

    public static boolean oredictVanillaStone;

    public static Configuration initConfig (File file) {

        config = new Configuration(file);
        return config;
    }

    public static void syncConfigData () {

        config.setCategoryComment("_features", "Allows features to be completely disabled");

        for (final Feature feature : FeatureManager.getFeatures()) {
            feature.setupConfiguration(config);
        }

        if (config.hasChanged()) {
            config.save();
        }
    }

    /**
     * Checks if a feature is enabled.
     *
     * @param feature The feature to check for.
     * @param name The name of the feature
     * @param description The description for the feature.
     * @return Whether or not the feature was enabled.
     */
    public static boolean isFeatureEnabled (Feature feature, String name, String description) {

        final boolean result = config.getBoolean(name, "_features", feature.enabledByDefault(), description);
        syncConfigData();
        return result;
    }
}