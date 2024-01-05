package net.darkhax.darkutilities;

import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class DarkUtilsNeoForge {

    public DarkUtilsNeoForge() {

        DarkUtilsCommon.getInstance();
    }
}