package net.darkhax.darkutils.addons.thaumcraft;

import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.ContentHandler;
import net.darkhax.darkutils.items.ItemSourcedSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;

public class DarkUtilsThaumcraftAddon {
    
    public static Item deathSword;
    
    public static void preInit () {
        
        initBlocks();
        initItems();
        initRecipes();
        initMisc();
        
        DarkUtils.proxy.thaumcraftPreInit();
    }
    
    private static void initBlocks() {
        
    }
    
    private static void initItems() {
        
        deathSword = new ItemSourcedSword(ThaumcraftMaterials.TOOLMAT_THAUMIUM, DamageSourceThaumcraft.dissolve, EnumChatFormatting.DARK_PURPLE, 0.30f).setUnlocalizedName("swordDeath");
        GameRegistry.registerItem(deathSword, "sword_death");
    }
    
    private static void initRecipes() {
        
    }
    
    private static void initMisc() {
        
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemMaterial, 1, 0), new AspectList().add(Aspect.DEATH, 1).add(Aspect.UNDEAD, 1).add(Aspect.SOUL, 1));
    }
}