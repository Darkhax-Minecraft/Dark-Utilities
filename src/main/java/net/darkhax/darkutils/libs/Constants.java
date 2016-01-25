package net.darkhax.darkutils.libs;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    
    public static final String MOD_ID = "DarkUtils";
    public static final String MOD_NAME = "Dark Utilities";
    public static final String VERSION_NUMBER = "1.0.0.0";
    public static final String MCVERSION = "[1.8,1.8.9]";
    public static final String CLIENT_PROXY_CLASS = "net.darkhax.darkutils.client.ProxyClient";
    public static final String SERVER_PROXY_CLASS = "net.darkhax.darkutils.common.ProxyCommon";
    public static final String DEPENDENCIES = "required-after:bookshelf@[1.1.0,];after:Waila;after:Thaumcraft";
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
}