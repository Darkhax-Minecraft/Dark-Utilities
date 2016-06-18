package net.darkhax.darkutils.features.misc;

import java.util.List;

import net.darkhax.bookshelf.lib.util.TextUtils.ChatFormat;
import net.darkhax.darkutils.features.Feature;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureDisabled extends Feature {
    
    public static Item itemDisabled;
    
    @Override
    public void onPreInit () {
        
        itemDisabled = new ItemDisabled();
        GameRegistry.register(itemDisabled);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        ModelLoader.setCustomModelResourceLocation(itemDisabled, 0, new ModelResourceLocation("barrier", "inventory"));
    }
    
    private class ItemDisabled extends Item {
        
        public ItemDisabled() {
            
            this.setUnlocalizedName("darkutils.disabled");
            this.setRegistryName("disabled");
        }
        
        @Override
        public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
            
            tooltip.add(ChatFormat.RED + I18n.format("tooltip.darkutils.disabled"));
        }
    }
}