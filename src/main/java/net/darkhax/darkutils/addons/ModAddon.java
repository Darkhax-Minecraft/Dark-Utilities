package net.darkhax.darkutils.addons;

import mezz.jei.api.IModRegistry;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ModAddon {
    
    public void onPreInit ();
    
    public void onInit ();
    
    public void onPostInit ();
    
    @SideOnly(Side.CLIENT)
    public void onClientPreInit ();
    
    @Optional.Method(modid = "JEI")
    public void onJEIReady (IModRegistry registry);
}