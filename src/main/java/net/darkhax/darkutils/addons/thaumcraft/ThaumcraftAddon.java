package net.darkhax.darkutils.addons.thaumcraft;

import mezz.jei.api.IModRegistry;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.addons.ModAddon;
import net.darkhax.darkutils.handler.ContentHandler;
import net.darkhax.darkutils.items.ItemSourcedSword;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.config.ConfigResearch;
import thaumcraft.common.entities.monster.tainted.EntityTaintChicken;
import thaumcraft.common.entities.monster.tainted.EntityTaintCow;
import thaumcraft.common.entities.monster.tainted.EntityTaintCreeper;
import thaumcraft.common.entities.monster.tainted.EntityTaintPig;
import thaumcraft.common.entities.monster.tainted.EntityTaintRabbit;
import thaumcraft.common.entities.monster.tainted.EntityTaintSheep;
import thaumcraft.common.entities.monster.tainted.EntityTaintVillager;

public class ThaumcraftAddon implements ModAddon {
    
    public static Item itemDisolveSword;
    public static Item itemRecallFocus;
    
    @Override
    public void onPreInit () {
        
        // Blocks
        
        // Items
        itemDisolveSword = new ItemSourcedSword(ThaumcraftMaterials.TOOLMAT_THAUMIUM, DamageSourceThaumcraft.dissolve, EnumChatFormatting.DARK_PURPLE, 0.40f).setUnlocalizedName("darkutils.swordDeath");
        GameRegistry.registerItem(itemDisolveSword, "sword_death");
        
        itemRecallFocus = new ItemFociRecall();
        GameRegistry.registerItem(itemRecallFocus, "focus_recall");
        
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
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemMaterial, 1, 2), new AspectList().add(Aspect.WATER, 3).add(Aspect.DEATH, 1).add(Aspect.UNDEAD, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemPotion, 1, 0), new AspectList().add(Aspect.VOID, 1).add(Aspect.LIFE, 3).add(Aspect.WATER, 2));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemPotion, 1, 1), new AspectList().add(Aspect.VOID, 1).add(Aspect.DEATH, 3).add(Aspect.WATER, 2));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemEnchantedRing, 1, 0), new AspectList().add(Aspect.METAL, 1).add(Aspect.DESIRE, 1).add(Aspect.MOTION, 3));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemEnchantedRing, 1, 1), new AspectList().add(Aspect.METAL, 1).add(Aspect.DESIRE, 1).add(Aspect.FIRE, 3));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemEnchantedRing, 1, 2), new AspectList().add(Aspect.METAL, 1).add(Aspect.DESIRE, 2).add(Aspect.CRYSTAL, 2));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemEnchantedRing, 1, 3), new AspectList().add(Aspect.METAL, 1).add(Aspect.DESIRE, 2).add(Aspect.AVERSION, 2));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemEnchantedRing, 1, 4), new AspectList().add(Aspect.METAL, 1).add(Aspect.DESIRE, 1).add(Aspect.WATER, 2).add(Aspect.ENERGY, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemEnchantedRing, 1, 5), new AspectList().add(Aspect.METAL, 1).add(Aspect.DESIRE, 2).add(Aspect.WATER, 2));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.itemEnchantedRing, 1, 6), new AspectList().add(Aspect.METAL, 1).add(Aspect.DESIRE, 1).add(Aspect.TOOL, 2).add(Aspect.MOTION, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockDetector), new AspectList().add(Aspect.EARTH, 8).add(Aspect.ENERGY, 3).add(Aspect.MECHANISM, 3));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockSneakyBlock), new AspectList().add(Aspect.EARTH, 2).add(Aspect.WATER, 1).add(Aspect.LIFE, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockSneakyLever), new AspectList().add(Aspect.EARTH, 2).add(Aspect.WATER, 1).add(Aspect.LIFE, 1).add(Aspect.MECHANISM, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockSneakyGhost), new AspectList().add(Aspect.EARTH, 2).add(Aspect.WATER, 1).add(Aspect.LIFE, 1).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 2));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockSneakyTorch), new AspectList().add(Aspect.EARTH, 2).add(Aspect.WATER, 1).add(Aspect.LIFE, 1).add(Aspect.LIGHT, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ContentHandler.blockCakeEPlus), new AspectList().add(Aspect.LIFE, 4).add(Aspect.DESIRE, 2).add(Aspect.PLANT, 2).add(Aspect.ENERGY, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(itemDisolveSword), new AspectList().add(Aspect.AVERSION, 3).add(Aspect.DEATH, 2).add(Aspect.UNDEAD, 2));
        
        DarkUtils.proxy.thaumcraftPreInit();
    }
    
    @Override
    public void onInit () {
        
        //Infusion
        ConfigResearch.recipes.put("DarkUtils:DeathSword", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_DEATH_SWwORD", new ItemStack(itemDisolveSword), 4, new AspectList().add(Aspect.DEATH, 8).add(Aspect.AVERSION, 8).add(Aspect.ENTROPY, 32), new ItemStack(ItemsTC.thaumiumSword), new Object[] { new ItemStack(ItemsTC.bucketDeath), new ItemStack(ContentHandler.itemMaterial, 1, 0), new ItemStack(ItemsTC.bucketDeath), new ItemStack(ContentHandler.itemMaterial, 1, 1), new ItemStack(ItemsTC.bucketDeath), new ItemStack(ContentHandler.itemMaterial, 1, 2) }));
        ConfigResearch.recipes.put("DarkUtils:RingKnockback", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_ENCHRINGS", new ItemStack(ContentHandler.itemEnchantedRing, 1, 0), 3, new AspectList().add(Aspect.METAL, 64).add(Aspect.DESIRE, 16).add(Aspect.MOTION, 32), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { new ItemStack(Blocks.stone), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone), new ItemStack(Items.iron_ingot) }));
        ConfigResearch.recipes.put("DarkUtils:RingFire", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_ENCHRINGS", new ItemStack(ContentHandler.itemEnchantedRing, 1, 1), 3, new AspectList().add(Aspect.METAL, 64).add(Aspect.DESIRE, 16).add(Aspect.FIRE, 32), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { new ItemStack(ItemsTC.shard, 1, 1), new ItemStack(Items.iron_ingot), new ItemStack(ItemsTC.shard, 1, 1), new ItemStack(Items.iron_ingot), new ItemStack(ItemsTC.shard, 1, 1), new ItemStack(Items.iron_ingot) }));
        ConfigResearch.recipes.put("DarkUtils:RingFortune", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_ENCHRINGS", new ItemStack(ContentHandler.itemEnchantedRing, 1, 2), 3, new AspectList().add(Aspect.METAL, 64).add(Aspect.DESIRE, 16).add(Aspect.CRYSTAL, 32), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { new ItemStack(Items.emerald), new ItemStack(Items.iron_ingot), new ItemStack(Items.emerald), new ItemStack(Items.iron_ingot), new ItemStack(Items.emerald), new ItemStack(Items.iron_ingot) }));
        ConfigResearch.recipes.put("DarkUtils:RingLoot", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_ENCHRINGS", new ItemStack(ContentHandler.itemEnchantedRing, 1, 3), 3, new AspectList().add(Aspect.METAL, 64).add(Aspect.DESIRE, 16).add(Aspect.AVERSION, 32), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { new ItemStack(Blocks.lapis_block), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.lapis_block), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.lapis_block), new ItemStack(Items.iron_ingot) }));
        ConfigResearch.recipes.put("DarkUtils:RingLure", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_ENCHRINGS", new ItemStack(ContentHandler.itemEnchantedRing, 1, 4), 3, new AspectList().add(Aspect.METAL, 64).add(Aspect.DESIRE, 16).add(Aspect.WATER, 32).add(Aspect.ENERGY, 16), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { new ItemStack(Items.fishing_rod), new ItemStack(Items.iron_ingot), new ItemStack(Items.fish), new ItemStack(Items.iron_ingot), new ItemStack(Items.fish), new ItemStack(Items.iron_ingot) }));
        ConfigResearch.recipes.put("DarkUtils:RingLuck", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_ENCHRINGS", new ItemStack(ContentHandler.itemEnchantedRing, 1, 5), 3, new AspectList().add(Aspect.METAL, 64).add(Aspect.DESIRE, 16).add(Aspect.WATER, 32), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { new ItemStack(Blocks.lapis_block), new ItemStack(Items.iron_ingot), new ItemStack(Items.prismarine_shard), new ItemStack(Items.iron_ingot), new ItemStack(Items.prismarine_shard), new ItemStack(Items.iron_ingot) }));
        ConfigResearch.recipes.put("DarkUtils:RingEfficiency", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_ENCHRINGS", new ItemStack(ContentHandler.itemEnchantedRing, 1, 6), 3, new AspectList().add(Aspect.METAL, 64).add(Aspect.DESIRE, 16).add(Aspect.TOOL, 32).add(Aspect.MOTION, 16), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { new ItemStack(Items.golden_pickaxe), new ItemStack(Items.iron_ingot), new ItemStack(Items.golden_pickaxe), new ItemStack(Items.iron_ingot), new ItemStack(Items.golden_pickaxe), new ItemStack(Items.iron_ingot) }));
        //ConfigResearch.recipes.put("DarkUtils:Recall", ThaumcraftApi.addInfusionCraftingRecipe("DARKUTILS_RECALL", new ItemStack(itemRecallFocus), 4,new AspectList().add, input, recipe));
        
        //Crucible
        ConfigResearch.recipes.put("DarkUtils:PotionCure", ThaumcraftApi.addCrucibleRecipe("DARKUTILS_POTIONS", new ItemStack(ContentHandler.itemPotion, 1, 0), new ItemStack(Items.glass_bottle), new AspectList().add(Aspect.WATER, 8).add(Aspect.LIFE, 32).add(Aspect.ENERGY, 4)));
        ConfigResearch.recipes.put("DarkUtils:PotionDisease", ThaumcraftApi.addCrucibleRecipe("DARKUTILS_POTIONS", new ItemStack(ContentHandler.itemPotion, 1, 1), new ItemStack(Items.glass_bottle), new AspectList().add(Aspect.WATER, 8).add(Aspect.DEATH, 32).add(Aspect.ENERGY, 4)));
    }
    
    @Override
    public void onPostInit () {
        
        ResearchCategories.registerCategory("DARKUTILS", "DARKUTILS_LANDING", new ResourceLocation("darkutils:textures/research/research_darkutils.png"), new ResourceLocation("darkutils:textures/research/gui_research_darkutils_background.png"), new ResourceLocation("darkutils:textures/research/gui_research_darkutils_background_overlay.png"));
        
        new ResearchItem("DARKUTILS_LANDING", "DARKUTILS", new AspectList(), 0, 0, 0, new Object[] { new ResourceLocation("darkutils:textures/research/research_darkutils.png") }).setPages(new ResearchPage[] { new ResearchPage("tc.research_page.darkutils.1") }).setStub().setHidden().setRound().setSpecial().registerResearchItem();
        new ResearchItem("DARKUTILS_DEATH_SWORD", "DARKUTILS", new AspectList().add(Aspect.AVERSION, 3).add(Aspect.DEATH, 2).add(Aspect.UNDEAD, 2), 0, 2, 1, new Object[] { new ItemStack(itemDisolveSword) }).setPages(new ResearchPage[] { new ResearchPage("tc.research_page.darkutils.sword"), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:DeathSword")) }).setRound().setParents("DARKUTILS_LANDING").registerResearchItem();
        new ResearchItem("DARKUTILS_ENCHRINGS", "DARKUTILS", new AspectList().add(Aspect.METAL, 3).add(Aspect.DESIRE, 3).add(Aspect.ENERGY, 1).add(Aspect.AURA, 1), 2, 2, 2, new Object[] { new ItemStack(ContentHandler.itemEnchantedRing, 1, 0), new ItemStack(ContentHandler.itemEnchantedRing, 1, 1), new ItemStack(ContentHandler.itemEnchantedRing, 1, 2), new ItemStack(ContentHandler.itemEnchantedRing, 1, 3), new ItemStack(ContentHandler.itemEnchantedRing, 1, 4), new ItemStack(ContentHandler.itemEnchantedRing, 1, 5), new ItemStack(ContentHandler.itemEnchantedRing, 1, 6) }).setRound().setPages(new ResearchPage[] { new ResearchPage("tc.research_page.darkutils.enchrings"), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:RingKnockback")), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:RingFire")), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:RingFortune")), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:RingLoot")), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:RingLure")), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:RingLuck")), new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get("DarkUtils:RingEfficiency")) }).setParents("DARKUTILS_LANDING").registerResearchItem();
        new ResearchItem("DARKUTILS_POTIONS", "DARKUTILS", new AspectList().add(Aspect.VOID, 1).add(Aspect.WATER, 3).add(Aspect.LIFE, 2).add(Aspect.DEATH, 2).add(Aspect.ENERGY, 3).add(Aspect.FLUX, 3), -2, 2, 2, new Object[] { new ItemStack(ContentHandler.itemPotion, 1, 0), new ItemStack(ContentHandler.itemPotion, 1, 1) }).setRound().setPages(new ResearchPage[] { new ResearchPage("tc.research_page.darkutils.potion"), new ResearchPage((CrucibleRecipe) ConfigResearch.recipes.get("DarkUtils:PotionCure")), new ResearchPage((CrucibleRecipe) ConfigResearch.recipes.get("DarkUtils:PotionDisease")) }).setParents("DARKUTILS_LANDING").registerResearchItem();
        new ResearchItem("DARKUTILS_RECALL", "DARKUTILS", new AspectList().add(Aspect.MOTION, 4).add(Aspect.ELDRITCH, 4), 0, -2, 3, (Object[]) ItemFociRecall.getDisplayStacks()).setRound().setPages(new ResearchPage[] { new ResearchPage("tc.research_page.darkutils.recall") }).setParents("DARKUTILS_LANDING").registerResearchItem();
        ScanningManager.addScannableThing(new ScanDarkUtils());
    }
    
    @Override
    public void onClientPreInit () {
        
        ModelLoader.setCustomModelResourceLocation(ThaumcraftAddon.itemDisolveSword, 0, new ModelResourceLocation("darkutils:sword_death", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ThaumcraftAddon.itemRecallFocus, 0, new ModelResourceLocation("darkutils:focus_recall", "inventory"));
    }
    
    @Override
    public void onJEIReady (IModRegistry registry) {
        
        registry.addDescription(new ItemStack(itemDisolveSword), "jei.darkutils.deathSword.desc");
        registry.addDescription(new ItemStack(ContentHandler.itemPotion), "jei.darkutils.potion.cure.thaumcraft.desc");
        registry.addDescription(new ItemStack(itemRecallFocus), "jei.darkutils.focus.recall.desc");
    }
    
    /**
     * A special hook that handles Thaumcraft logic for the Mysterious Potion cure.
     * 
     * @param stack: The potion stack.
     * @param player: The player doing the curing.
     * @param entity: The entity being cured.
     * @return boolean: Whether or not something was cured.
     */
    public static boolean cureHook (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        
        EntityLivingBase normal = null;
        
        if (entity instanceof EntityTaintChicken)
            normal = new EntityChicken(entity.worldObj);
            
        else if (entity instanceof EntityTaintCow)
            normal = new EntityCow(entity.worldObj);
            
        else if (entity instanceof EntityTaintCreeper)
            normal = new EntityCreeper(entity.worldObj);
            
        else if (entity instanceof EntityTaintPig)
            normal = new EntityPig(entity.worldObj);
            
        else if (entity instanceof EntityTaintRabbit)
            normal = new EntityRabbit(entity.worldObj);
            
        else if (entity instanceof EntityTaintSheep)
            normal = new EntitySheep(entity.worldObj);
            
        else if (entity instanceof EntityTaintVillager)
            normal = new EntityVillager(entity.worldObj);
            
        if (normal != null) {
            
            if (player.worldObj.isRemote)
                return true;
                
            ItemStackUtils.decreaseStackSize(stack, 1);
            normal.copyLocationAndAnglesFrom(entity);
            entity.worldObj.spawnEntityInWorld(normal);
            entity.setDead();
            return true;
        }
        
        return false;
    }
}