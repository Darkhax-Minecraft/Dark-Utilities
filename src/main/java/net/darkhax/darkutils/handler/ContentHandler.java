package net.darkhax.darkutils.handler;

import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.lib.util.Utilities;
import net.darkhax.darkutils.blocks.BlockAntiSlime;
import net.darkhax.darkutils.blocks.BlockCakeBase;
import net.darkhax.darkutils.blocks.BlockEnderTether;
import net.darkhax.darkutils.blocks.BlockFilter;
import net.darkhax.darkutils.blocks.BlockGrate;
import net.darkhax.darkutils.blocks.BlockSneaky;
import net.darkhax.darkutils.blocks.BlockSneakyGhost;
import net.darkhax.darkutils.blocks.BlockSneakyLever;
import net.darkhax.darkutils.blocks.BlockSneakyTorch;
import net.darkhax.darkutils.blocks.BlockTimer;
import net.darkhax.darkutils.blocks.BlockTrapEffect;
import net.darkhax.darkutils.blocks.BlockTrapMovement;
import net.darkhax.darkutils.blocks.BlockUpdateDetector;
import net.darkhax.darkutils.items.ItemBlockCake;
import net.darkhax.darkutils.items.ItemBlockFilter;
import net.darkhax.darkutils.items.ItemMaterial;
import net.darkhax.darkutils.items.ItemMysteriousPotion;
import net.darkhax.darkutils.items.ItemRingEnchanted;
import net.darkhax.darkutils.libs.PotionReferences;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.darkhax.darkutils.tileentity.TileEntitySneaky;
import net.darkhax.darkutils.tileentity.TileEntityTimer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ContentHandler {
    
    public static Block blockTrap;
    public static Block blockEnderTether;
    public static Block blockTrapMovement;
    public static Block blockGrate;
    public static Block blockFilter;
    public static Block blockTimer;
    public static Block blockAntiSlime;
    public static Block blockDetector;
    public static Block blockCakeEPlus;
    public static Block blockSneakyBlock;
    public static Block blockSneakyLever;
    public static Block blockSneakyGhost;
    public static Block blockSneakyTorch;
    
    public static Item itemMaterial;
    public static Item itemPotion;
    public static Item itemEnchantedRing;
    
    public static void initBlocks () {
        
        blockTrap = new BlockTrapEffect();
        GameRegistry.registerBlock(blockTrap, ItemBlockBasic.class, "trap_tile", new Object[] { BlockTrapEffect.EnumType.getTypes() });
        
        blockEnderTether = new BlockEnderTether();
        GameRegistry.registerBlock(blockEnderTether, "ender_tether");
        GameRegistry.registerTileEntity(TileEntityEnderTether.class, "ender_tether");
        
        blockTrapMovement = new BlockTrapMovement();
        GameRegistry.registerBlock(blockTrapMovement, "trap_move");
        
        blockGrate = new BlockGrate();
        GameRegistry.registerBlock(blockGrate, "grate");
        
        blockFilter = new BlockFilter();
        GameRegistry.registerBlock(blockFilter, ItemBlockFilter.class, "filter", new Object[] { BlockFilter.EnumType.getTypes() });
        
        blockTimer = new BlockTimer();
        GameRegistry.registerBlock(blockTimer, "timer");
        GameRegistry.registerTileEntity(TileEntityTimer.class, "timer");
        
        blockAntiSlime = new BlockAntiSlime();
        GameRegistry.registerBlock(blockAntiSlime, "anti_slime");
        GameRegistry.registerTileEntity(TileEntityAntiSlime.class, "anti_slime");
        
        blockDetector = new BlockUpdateDetector();
        GameRegistry.registerBlock(blockDetector, "update_detector");
        
        blockCakeEPlus = new BlockCakeBase("eplus");
        GameRegistry.registerBlock(blockCakeEPlus, ItemBlockCake.class, "cake_eplus", new Object[] {});
        
        blockSneakyBlock = new BlockSneaky(Material.rock);
        GameRegistry.registerBlock(blockSneakyBlock, "sneaky");
        GameRegistry.registerTileEntity(TileEntitySneaky.class, "sneaky");
        
        blockSneakyLever = new BlockSneakyLever();
        GameRegistry.registerBlock(blockSneakyLever, "sneaky_lever");
        
        blockSneakyGhost = new BlockSneakyGhost();
        GameRegistry.registerBlock(blockSneakyGhost, "sneaky_ghost");
        
        blockSneakyTorch = new BlockSneakyTorch();
        GameRegistry.registerBlock(blockSneakyTorch, "sneaky_torch");
    }
    
    public static void initItems () {
        
        itemMaterial = new ItemMaterial();
        GameRegistry.registerItem(itemMaterial, "material");
        
        itemPotion = new ItemMysteriousPotion();
        GameRegistry.registerItem(itemPotion, "mystery_potion");
        
        itemEnchantedRing = new ItemRingEnchanted();
        GameRegistry.registerItem(itemEnchantedRing, "ring_enchanted");
    }
    
    public static void initRecipes () {
        
        final Object[] trapIngredients = new Object[] { Items.spider_eye, Items.fermented_spider_eye, Items.iron_sword, Blocks.soul_sand, Items.flint_and_steel, new ItemStack(itemMaterial, 1, 0) };
        final Object[] filterIngredients = new Object[] { Items.golden_pickaxe, Items.bone, Items.spider_eye, Items.rotten_flesh, Items.wheat, Items.water_bucket, Items.egg, Items.milk_bucket, Blocks.slime_block };
            
        GameRegistry.addShapedRecipe(new ItemStack(blockEnderTether), new Object[] { " u ", "oto", 'u', new ItemStack(itemMaterial, 1, 1), 'o', Blocks.obsidian, 't', Blocks.redstone_torch });
        GameRegistry.addShapedRecipe(new ItemStack(blockTrapMovement), new Object[] { " s ", "bfb", 's', Items.slime_ball, 'b', Blocks.stone, 'f', Items.sugar });
        GameRegistry.addShapedRecipe(new ItemStack(blockTimer), new Object[] { "sts", "tct", "sts", 's', Blocks.stone, 't', Blocks.redstone_torch, 'c', Items.clock });        
        GameRegistry.addShapedRecipe(new ItemStack(blockAntiSlime), new Object[] { "sws", "wcw", "sws", 's', Blocks.stone, 'w', Blocks.cobblestone_wall, 'c', new ItemStack(itemMaterial, 1, 2) });
        GameRegistry.addShapedRecipe(new ItemStack(blockDetector), new Object[] { "sps", "srs", "sps", 's', Blocks.stone, 'p', Blocks.piston, 'r', Blocks.redstone_block });
                   
        GameRegistry.addShapelessRecipe(new ItemStack(blockGrate), Blocks.iron_bars, Blocks.stone, Blocks.trapdoor);
        GameRegistry.addShapelessRecipe(new ItemStack(itemMaterial, 3, 0), new ItemStack(Items.skull, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(itemMaterial, 1, 1), new ItemStack(itemMaterial, 1, 0), Items.ender_pearl);
        GameRegistry.addShapelessRecipe(new ItemStack(itemMaterial, 1, 2), new ItemStack(itemMaterial, 1, 0), Items.slime_ball);
        
        for (BlockFilter.EnumType type : BlockFilter.EnumType.values())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, type.meta), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGate", 's', Blocks.stone, 'i', filterIngredients[type.meta] }));
        
        for (BlockTrapEffect.EnumType type : BlockTrapEffect.EnumType.values())
            GameRegistry.addShapedRecipe(new ItemStack(blockTrap, 1, type.meta), new Object[] { "sis", 's', Blocks.stone, 'i', trapIngredients[type.meta] });
    }
    
    public static void initMisc () {
        
        OreDictionary.registerOre("fenceGate", Blocks.oak_fence_gate);
        OreDictionary.registerOre("fenceGate", Blocks.acacia_fence_gate);
        OreDictionary.registerOre("fenceGate", Blocks.birch_fence_gate);
        OreDictionary.registerOre("fenceGate", Blocks.dark_oak_fence_gate);
        OreDictionary.registerOre("fenceGate", Blocks.jungle_fence_gate);
        OreDictionary.registerOre("fenceGate", Blocks.spruce_fence_gate);
        
        BrewingRecipeRegistry.addRecipe(PotionReferences.AWKWARD, new ItemStack(itemMaterial, 1, 0), PotionReferences.WITHER_I_0_45);
        
        for (int meta = 0; meta < ItemRingEnchanted.varients.length; meta++)
            for (String category : Utilities.vanillaLootChests)
                ChestGenHooks.addItem(category, new WeightedRandomChestContent(new ItemStack(ContentHandler.itemEnchantedRing, 1, meta), 1, 1, 1));
                
        for (int meta = 0; meta < ItemMysteriousPotion.varients.length; meta++)
            for (String category : Utilities.vanillaLootChests)
                ChestGenHooks.addItem(category, new WeightedRandomChestContent(new ItemStack(ContentHandler.itemPotion, 1, meta), 1, 1, 1));
    }
}
