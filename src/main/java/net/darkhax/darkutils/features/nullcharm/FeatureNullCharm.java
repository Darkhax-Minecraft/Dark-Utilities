package net.darkhax.darkutils.features.nullcharm;

import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureNullCharm extends Feature {
    
    public static Item itemNullCharm;
    private static boolean craftable;
    
    @Override
    public void onPreInit () {
        
        itemNullCharm = new ItemNullCharm();
        ModUtils.registerItem(itemNullCharm, "charm_null");
    }
    
    @Override
    public boolean usesEvents () {
        
        return true;
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftable = config.getBoolean("Craftable", this.configName, true, "Should the null charm be craftable?");
    }
    
    @Override
    public void setupRecipes () {
    
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModUtils.registerItemInvModel(itemNullCharm);
    }
}