package net.darkhax.darkutils.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.darkhax.bookshelf.common.BookshelfRegistry;
import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.blocks.BlockAntiSlime;
import net.darkhax.darkutils.blocks.BlockCakeBase;
import net.darkhax.darkutils.blocks.BlockEnderTether;
import net.darkhax.darkutils.blocks.BlockFeeder;
import net.darkhax.darkutils.blocks.BlockFilter;
import net.darkhax.darkutils.blocks.BlockGrate;
import net.darkhax.darkutils.blocks.BlockTimer;
import net.darkhax.darkutils.blocks.BlockTrapEffect;
import net.darkhax.darkutils.blocks.BlockUpdateDetector;
import net.darkhax.darkutils.blocks.BlockVectorPlate;
import net.darkhax.darkutils.blocks.sneaky.BlockSneaky;
import net.darkhax.darkutils.blocks.sneaky.BlockSneakyGhost;
import net.darkhax.darkutils.blocks.sneaky.BlockSneakyLever;
import net.darkhax.darkutils.blocks.sneaky.BlockSneakyObsidian;
import net.darkhax.darkutils.blocks.sneaky.BlockSneakyPressurePlate;
import net.darkhax.darkutils.blocks.sneaky.BlockSneakyTorch;
import net.darkhax.darkutils.items.ItemBlockCake;
import net.darkhax.darkutils.items.ItemBlockFeeder;
import net.darkhax.darkutils.items.ItemBlockFilter;
import net.darkhax.darkutils.items.ItemMaterial;
import net.darkhax.darkutils.items.ItemMysteriousPotion;
import net.darkhax.darkutils.tileentity.TileEntityAntiSlime;
import net.darkhax.darkutils.tileentity.TileEntityEnderTether;
import net.darkhax.darkutils.tileentity.TileEntityFeeder;
import net.darkhax.darkutils.tileentity.TileEntitySneaky;
import net.darkhax.darkutils.tileentity.TileEntityTimer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ContentHandler {
    
    /**
     * A list of all items from DarkUtils.
     */
    public static final List<Item> ITEMS = new ArrayList<Item>();
    
    /**
     * A list of all blocks from DarkUtils.
     */
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    
    public static Block blockTrap;
    public static Block blockEnderTether;
    public static Block blockTrapMovement;
    public static Block blockTrapMovementFast;
    public static Block blockTrapMovementHyper;
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
    public static Block blockSneakyObsidian;
    public static Block blockSneakyPlate;
    public static Block blockFeeder;
    
    public static Item itemMaterial;
    public static Item itemPotion;
    
    public static void initBlocks () {
        
        blockTrap = new BlockTrapEffect();
        registerBlock(blockTrap, new ItemBlockBasic(blockTrap, BlockTrapEffect.EnumType.getTypes()), "trap_tile");
        
        blockEnderTether = new BlockEnderTether();
        registerBlock(blockEnderTether, "ender_tether");
        GameRegistry.registerTileEntity(TileEntityEnderTether.class, "ender_tether");
        
        blockTrapMovement = new BlockVectorPlate(0.06d);
        registerBlock(blockTrapMovement, "trap_move");
        
        blockTrapMovementFast = new BlockVectorPlate(0.3d);
        registerBlock(blockTrapMovementFast, "trap_move_fast");
        
        blockTrapMovementHyper = new BlockVectorPlate(1.5d);
        registerBlock(blockTrapMovementHyper, "trap_move_hyper");
        
        blockGrate = new BlockGrate();
        registerBlock(blockGrate, "grate");
        
        blockFilter = new BlockFilter();
        registerBlock(blockFilter, new ItemBlockFilter(blockFilter, BlockFilter.EnumType.getTypes()), "filter");
        
        blockTimer = new BlockTimer();
        registerBlock(blockTimer, "timer");
        GameRegistry.registerTileEntity(TileEntityTimer.class, "timer");
        
        blockAntiSlime = new BlockAntiSlime();
        registerBlock(blockAntiSlime, "anti_slime");
        GameRegistry.registerTileEntity(TileEntityAntiSlime.class, "anti_slime");
        
        blockDetector = new BlockUpdateDetector();
        registerBlock(blockDetector, "update_detector");
        
        blockCakeEPlus = new BlockCakeBase("eplus");
        registerBlock(blockCakeEPlus, new ItemBlockCake(blockCakeEPlus), "cake_eplus");
        
        blockSneakyBlock = new BlockSneaky();
        registerBlock(blockSneakyBlock, "sneaky");
        GameRegistry.registerTileEntity(TileEntitySneaky.class, "sneaky");
        
        blockSneakyLever = new BlockSneakyLever();
        registerBlock(blockSneakyLever, "sneaky_lever");
        
        blockSneakyGhost = new BlockSneakyGhost();
        registerBlock(blockSneakyGhost, "sneaky_ghost");
        
        blockSneakyTorch = new BlockSneakyTorch();
        registerBlock(blockSneakyTorch, "sneaky_torch");
        
        blockSneakyObsidian = new BlockSneakyObsidian();
        registerBlock(blockSneakyObsidian, "sneaky_obsidian");
        
        blockSneakyPlate = new BlockSneakyPressurePlate();
        registerBlock(blockSneakyPlate, "sneaky_plate");
        
        blockFeeder = new BlockFeeder();
        registerBlock(blockFeeder, new ItemBlockFeeder(blockFeeder), "feeder");
        GameRegistry.registerTileEntity(TileEntityFeeder.class, "feeder");
    }
    
    public static void initItems () {
        
        itemMaterial = new ItemMaterial();
        registerItem(itemMaterial, "material");
        
        itemPotion = new ItemMysteriousPotion();
        registerItem(itemPotion, "mystery_potion");
    }
    
    public static void initRecipes () {
        
        final Object[] trapIngredients = new Object[] { Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE, Items.IRON_SWORD, Blocks.SOUL_SAND, Items.FLINT_AND_STEEL, new ItemStack(itemMaterial, 1, 0) };
        final Object[] filterIngredients = new Object[] { Items.GOLDEN_PICKAXE, Items.BONE, Items.SPIDER_EYE, Items.ROTTEN_FLESH, Items.WHEAT, Items.WATER_BUCKET, Items.EGG, Items.MILK_BUCKET, Blocks.SLIME_BLOCK };
        
        GameRegistry.addShapedRecipe(new ItemStack(blockEnderTether), new Object[] { " u ", "oto", 'u', new ItemStack(itemMaterial, 1, 1), 'o', Blocks.OBSIDIAN, 't', Blocks.REDSTONE_TORCH, 'i', Items.IRON_INGOT });
        GameRegistry.addShapedRecipe(new ItemStack(blockTrapMovement, 8), new Object[] { "isi", "bfb", 's', Items.SLIME_BALL, 'b', Blocks.STONE, 'f', Items.SUGAR });
        GameRegistry.addShapedRecipe(new ItemStack(blockTimer), new Object[] { "sts", "tct", "sts", 's', Blocks.STONE, 't', Blocks.REDSTONE_TORCH, 'c', Items.CLOCK });
        GameRegistry.addShapedRecipe(new ItemStack(blockAntiSlime), new Object[] { "sws", "wcw", "sws", 's', Blocks.STONE, 'w', Blocks.COBBLESTONE_WALL, 'c', new ItemStack(itemMaterial, 1, 2) });
        GameRegistry.addShapedRecipe(new ItemStack(blockDetector), new Object[] { "sps", "srs", "sps", 's', Blocks.STONE, 'p', Blocks.PISTON, 'r', Blocks.REDSTONE_BLOCK });
        GameRegistry.addShapedRecipe(new ItemStack(blockSneakyBlock, 8), new Object[] { "rrr", "rsr", "rrr", 'r', Blocks.STONE, 's', Items.SLIME_BALL });
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFeeder), new Object[] { "ccc", "geg", "ccc", 'c', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 5), 'g', "paneGlass", 'e', "gemEmerald" }));
        
        GameRegistry.addShapelessRecipe(new ItemStack(blockGrate), Blocks.IRON_BARS, Blocks.STONE, Blocks.TRAPDOOR);
        GameRegistry.addShapelessRecipe(new ItemStack(itemMaterial, 3, 0), new ItemStack(Items.SKULL, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(itemMaterial, 1, 1), new ItemStack(itemMaterial, 1, 0), Items.ENDER_PEARL);
        GameRegistry.addShapelessRecipe(new ItemStack(itemMaterial, 1, 2), new ItemStack(itemMaterial, 1, 0), Items.SLIME_BALL);
        GameRegistry.addShapelessRecipe(new ItemStack(blockSneakyLever), blockSneakyBlock, Blocks.LEVER);
        GameRegistry.addShapelessRecipe(new ItemStack(blockSneakyGhost), blockSneakyBlock, Blocks.WOOL);
        GameRegistry.addShapelessRecipe(new ItemStack(blockSneakyTorch), blockSneakyBlock, Blocks.TORCH);
        GameRegistry.addShapelessRecipe(new ItemStack(blockSneakyTorch), blockSneakyBlock, Blocks.REDSTONE_TORCH);
        GameRegistry.addShapelessRecipe(new ItemStack(blockSneakyObsidian), blockSneakyBlock, Blocks.OBSIDIAN);
        GameRegistry.addShapelessRecipe(new ItemStack(blockSneakyPlate), blockSneakyBlock, Blocks.WOODEN_PRESSURE_PLATE);
        
        addConversionRecipes(new ItemStack(blockTrapMovement), new ItemStack(blockTrapMovementFast, 1, 0));
        addConversionRecipes(new ItemStack(blockTrapMovementFast), new ItemStack(blockTrapMovementHyper, 1, 0));
        addConversionRecipes(new ItemStack(blockTrapMovementHyper), new ItemStack(blockTrapMovement, 1, 0));
        
        BookshelfRegistry.addAnvilRecipe(new ItemStack(Items.CAKE, 1), new ItemStack(Items.ENCHANTED_BOOK), "eplus", 2, new ItemStack(blockCakeEPlus));
        
        for (final BlockFilter.EnumType type : BlockFilter.EnumType.values())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockFilter, 1, type.meta), new Object[] { "gsg", "sis", "gsg", 'g', "fenceGate", 's', Blocks.STONE, 'i', filterIngredients[type.meta] }));
            
        for (final BlockTrapEffect.EnumType type : BlockTrapEffect.EnumType.values())
            GameRegistry.addShapedRecipe(new ItemStack(blockTrap, 1, type.meta), new Object[] { "sis", 's', Blocks.STONE, 'i', trapIngredients[type.meta] });
    }
    
    public static void initMisc () {
        
        OreDictionary.registerOre("fenceGate", Blocks.OAK_FENCE_GATE);
        OreDictionary.registerOre("fenceGate", Blocks.ACACIA_FENCE_GATE);
        OreDictionary.registerOre("fenceGate", Blocks.BIRCH_FENCE_GATE);
        OreDictionary.registerOre("fenceGate", Blocks.DARK_OAK_FENCE_GATE);
        OreDictionary.registerOre("fenceGate", Blocks.JUNGLE_FENCE_GATE);
        OreDictionary.registerOre("fenceGate", Blocks.SPRUCE_FENCE_GATE);
    }
    
    /**
     * Adds a basic conversion recipe. A conversion recipe converts one item directly into
     * another. Adds support for converting up to 9 items a time at a 1 to 1 ratio.
     * 
     * @param input: The input item.
     * @param output: The output item.
     */
    private static void addConversionRecipes (ItemStack input, ItemStack output) {
        
        for (int amount = 1; amount < 10; amount++) {
            
            final ItemStack[] inputs = new ItemStack[amount];
            Arrays.fill(inputs, input);
            GameRegistry.addShapelessRecipe(ItemStackUtils.copyStackWithSize(output, amount), (Object[]) inputs);
        }
    }
    
    /**
     * Provides the same functionality as older forge tile registration.
     * 
     * @param block The block to register.
     * @param ID The ID to register the block with.
     */
    private static void registerBlock (Block block, String ID) {
        
        block.setRegistryName(ID);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), block.getRegistryName());
        BLOCKS.add(block);
    }
    
    /**
     * Provides the same functionality as older forge tile registration.
     * 
     * @param block The block to register.
     * @param ID The ID to register the block with.
     */
    private static void registerBlock (Block block, ItemBlock item, String ID) {
        
        block.setRegistryName(ID);
        GameRegistry.register(block);
        GameRegistry.register(item, block.getRegistryName());
        BLOCKS.add(block);
    }
    
    /**
     * Provides the same functionality as older forge item registration.
     * 
     * @param item The item to register.
     * @param ID The ID to register the item with.
     */
    private static void registerItem (Item item, String ID) {
        
        if (item.getRegistryName() == null)
            item.setRegistryName(ID);
            
        GameRegistry.register(item);
        ITEMS.add(item);
    }
}