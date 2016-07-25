package net.darkhax.darkutils.libs;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    
    public static final String MOD_ID = "darkutils";
    public static final String MOD_NAME = "Dark Utilities";
    public static final String VERSION_NUMBER = "1.1.3.0";
    public static final String CLIENT_PROXY_CLASS = "net.darkhax.darkutils.client.ProxyClient";
    public static final String SERVER_PROXY_CLASS = "net.darkhax.darkutils.common.ProxyCommon";
    public static final String DEPENDENCIES = "required-after:bookshelf@[1.2.3.275,);after:Waila;after:JEI;";
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
}
