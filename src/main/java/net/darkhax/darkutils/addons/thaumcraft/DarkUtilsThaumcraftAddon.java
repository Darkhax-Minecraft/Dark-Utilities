package net.darkhax.darkutils.addons.thaumcraft;

import mezz.jei.api.IModRegistry;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.handler.ContentHandler;
import net.darkhax.darkutils.items.ItemSourcedSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.config.ConfigResearch;

public class DarkUtilsThaumcraftAddon {
    
    public static Item deathSword;
    
    public static void preInit () {
        
        // Blocks
        
        // Items
        deathSword = new ItemSourcedSword(ThaumcraftMaterials.TOOLMAT_THAUMIUM, DamageSourceThaumcraft.dissolve, EnumChatFormatting.DARK_PURPLE, 0.30f).setUnlocalizedName("swordDeath");
        GameRegistry.registerItem(deathSword, "sword_death");
        
        // Recipes
        
        // Aspect Tags
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockTrap, 1, 0), new AspectList().add(Aspect.EARTH, 3).add(Aspect.DEATH, 1).add(Aspect.TRAP, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockTrap, 1, 1), new AspectList().add(Aspect.EARTH, 4).add(Aspect.TRAP, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockTrap, 1, 2), new AspectList().add(Aspect.EARTH, 3).add(Aspect.AVERSION, 1).add(Aspect.TRAP, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockTrap, 1, 3), new AspectList().add(Aspect.EARTH, 3).add(Aspect.MOTION, 1).add(Aspect.TRAP, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockTrap, 1, 4), new AspectList().add(Aspect.EARTH, 3).add(Aspect.FIRE, 1).add(Aspect.TRAP, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockTrap, 1, 5), new AspectList().add(Aspect.EARTH, 3).add(Aspect.UNDEAD, 1).add(Aspect.TRAP, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockEnderTether), new AspectList().add(Aspect.TRAP, 1).add(Aspect.ELDRITCH, 2).add(Aspect.DARKNESS, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockTrapMovement), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 2).add(Aspect.EARTH, 3));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockGrate), new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.MOTION, 3).add(Aspect.METAL, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemMaterial, 1, 0), new AspectList().add(Aspect.DEATH, 1).add(Aspect.UNDEAD, 1).add(Aspect.SOUL, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemMaterial, 1, 1), new AspectList().add(Aspect.ELDRITCH, 3).add(Aspect.DEATH, 1).add(Aspect.UNDEAD, 1));
        
        ThaumcraftApi.registerObjectTag(new ItemStack(deathSword), new AspectList().add(Aspect.AVERSION, 3).add(Aspect.DEATH, 2).add(Aspect.UNDEAD, 2));
        
        DarkUtils.proxy.thaumcraftPreInit();
    }
    
    public static void init () {
        
        ConfigResearch.recipes.put("DeathSword", ThaumcraftApi.addInfusionCraftingRecipe("ELEMENTALTOOLS", new ItemStack(deathSword), 4, new AspectList().add(Aspect.DEATH, 8).add(Aspect.AVERSION, 8).add(Aspect.ENTROPY, 32), new ItemStack(ItemsTC.thaumiumSword), new Object[] { new ItemStack(ItemsTC.bucketDeath), new ItemStack(ItemsTC.bucketDeath), new ItemStack(ItemsTC.bucketDeath) }));
    }
    
    public static void postInit () {
        
        ResearchCategories.registerCategory("DARKUTILS", "DARKUTILS_LANDING", new ResourceLocation("darkutils:textures/research/research_darkutils.png"), new ResourceLocation("darkutils:textures/research/gui_research_darkutils_background.png"), new ResourceLocation("darkutils:textures/research/gui_research_darkutils_background_overlay.png"));
        
        new ResearchItem("DARKUTILS_LANDING", "DARKUTILS", new AspectList(), 0, 0, 0, new Object[] { new ResourceLocation("darkutils:textures/research/research_darkutils.png") }).setPages(new ResearchPage[] { new ResearchPage("tc.research_page.darkutils.1") }).setStub().setHidden().setRound().setSpecial().registerResearchItem();
        new ResearchItem("DARKUTILS_DEATH_SWORD", "DARKUTILS", new AspectList().add(Aspect.AVERSION, 3).add(Aspect.DEATH, 2).add(Aspect.UNDEAD, 2), 0, 2, 0, new Object[] { new ItemStack(deathSword) }).setPages(new ResearchPage[] { new ResearchPage("tc.research_page.sword") }).setRound().setParents("DARKUTILS_LANDING").registerResearchItem();
        
        ScanningManager.addScannableThing(new ScanDarkUtils());
    }
    
    public static void jeiRegisterHook (IModRegistry registry) {
    
    }
}