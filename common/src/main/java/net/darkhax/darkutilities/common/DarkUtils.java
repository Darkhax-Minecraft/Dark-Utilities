package net.darkhax.darkutilities.common;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DarkUtils {

    public static final String MOD_ID = "darkutils";
    public static final String MOD_NAME = "Dark Utilities";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}