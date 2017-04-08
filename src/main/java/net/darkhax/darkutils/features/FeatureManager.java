package net.darkhax.darkutils.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.util.AnnotationUtils;
import net.darkhax.darkutils.features.misc.FeatureDisabled;
import net.darkhax.darkutils.handler.ConfigurationHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

public class FeatureManager {

    private static final List<Feature> features = new ArrayList<>();

    private static boolean loaded = false;

    public static void init (ASMDataTable asmDataTable) {

        loaded = true;
        features.add(new FeatureDisabled());

        for (final Entry<Feature, DUFeature> feature : AnnotationUtils.getAnnotations(asmDataTable, DUFeature.class, Feature.class).entrySet()) {

            final DUFeature annotation = feature.getValue();

            if (annotation == null) {

                Constants.LOG.warn("Annotation for " + feature.getKey().getClass().getCanonicalName() + " was null!");
                continue;
            }

            registerFeature(feature.getKey(), annotation.name(), annotation.description());
        }
    }

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
            features.add(feature);

            if (feature.usesEvents()) {
                MinecraftForge.EVENT_BUS.register(feature);
            }
        }
    }

    public static boolean isLoaded () {

        return loaded;
    }

    public static List<Feature> getFeatures () {

        return features;
    }
}