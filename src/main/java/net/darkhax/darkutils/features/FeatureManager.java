package net.darkhax.darkutils.features;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.darkhax.bookshelf.lib.Constants;
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
        // features.addAll(AnnotationUtils.getAnnotations(asmDataTable, DUFeature.class,
        // Feature.class));

        for (final Entry<Feature, DUFeature> feature : getAnnotations(asmDataTable, DUFeature.class, Feature.class).entrySet()) {

            final DUFeature annotation = feature.getValue();

            if (annotation == null) {
                System.out.println("Annotation is null! " + feature.getKey().getClass().toString());
                continue;
            }
            System.out.println(String.format("Name: %s - Description: %s", annotation.name(), annotation.description()));
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

    public static <T, A extends Annotation> Map<T, A> getAnnotations (ASMDataTable asmDataTable, Class<A> annotation, Class<T> instance) {

        final Map<T, A> map = new HashMap<>();

        for (final ASMDataTable.ASMData asmData : asmDataTable.getAll(annotation.getCanonicalName())) {

            try {

                final Class<?> asmClass = Class.forName(asmData.getClassName());
                final Class<? extends T> asmInstanceClass = asmClass.asSubclass(instance);
                map.put(asmInstanceClass.newInstance(), asmInstanceClass.getAnnotation(annotation));
            }

            catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {

                Constants.LOG.warn("Could not load " + asmData.getClassName(), e);
            }
        }

        return map;
    }
}