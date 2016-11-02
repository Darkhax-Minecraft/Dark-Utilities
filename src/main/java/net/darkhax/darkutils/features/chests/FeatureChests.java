package net.darkhax.darkutils.features.chests;

import net.darkhax.bookshelf.block.BlockBasicChest;
import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.libs.Constants;
import net.darkhax.darkutils.libs.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest.Type;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;;

public class FeatureChests extends Feature {
    
    @SideOnly(Side.CLIENT)
    private static StateMapperBase stateMap;
    
    public static final Type TYPE_GLACIAL = EnumHelper.addEnum(Type.class, "DARKUTILS_GLACIAL", new Class[0]);
    public static final Type TYPE_GLASS = EnumHelper.addEnum(Type.class, "DARKUTILS_GLASS", new Class[0]);
    public static final Type TYPE_JUNGLE = EnumHelper.addEnum(Type.class, "DARKUTILS_JUNGLE", new Class[0]);
    public static final Type TYPE_MAGIC = EnumHelper.addEnum(Type.class, "DARKUTILS_MAGIC", new Class[0]);
    public static final Type TYPE_NETHER = EnumHelper.addEnum(Type.class, "DARKUTILS_NETHER", new Class[0]);
    public static final Type TYPE_ROYAL = EnumHelper.addEnum(Type.class, "DARKUTILS_ROYAL", new Class[0]);
    public static final Type TYPE_SANDSTONE = EnumHelper.addEnum(Type.class, "DARKUTILS_SANDSTONE", new Class[0]);
    public static final Type TYPE_PRISMARINE = EnumHelper.addEnum(Type.class, "DARKUTILS_PRISMARINE", new Class[0]);
    
    public static Block glacial;
    public static Block glass;
    public static Block jungle;
    public static Block magic;
    public static Block nether;
    public static Block royal;
    public static Block sandstone;
    public static Block prismarine;
    
    public static boolean craftGlacial;
    public static boolean craftGlass;
    public static boolean craftJungle;
    public static boolean craftMagic;
    public static boolean craftNether;
    public static boolean craftRoyal;
    public static boolean craftSandstone;
    public static boolean craftPrismarine;
    
    @Override
    public void onPreInit () {
        
        glacial = this.createChest(new BlockBasicChest(Constants.MOD_ID, "glacial", TYPE_GLACIAL).setHardness(2.5F), "chest_glacial");
        glass = this.createChest(new BlockBasicChest(Constants.MOD_ID, "glass", TYPE_GLASS).setHardness(2.5F), "chest_glass");
        jungle = this.createChest(new BlockBasicChest(Constants.MOD_ID, "jungle", TYPE_JUNGLE).setHardness(2.5F), "chest_jungle");
        magic = this.createChest(new BlockBasicChest(Constants.MOD_ID, "magic", TYPE_MAGIC).setHardness(2.5F), "chest_magic");
        nether = this.createChest(new BlockBasicChest(Constants.MOD_ID, "nether", TYPE_NETHER).setHardness(2.5F), "chest_nether");
        royal = this.createChest(new BlockBasicChest(Constants.MOD_ID, "royal", TYPE_ROYAL).setHardness(2.5F), "chest_royal");
        sandstone = this.createChest(new BlockBasicChest(Constants.MOD_ID, "sandstone", TYPE_SANDSTONE).setHardness(2.5F), "chest_sandstone");
        prismarine = this.createChest(new BlockBasicChest(Constants.MOD_ID, "prismarine", TYPE_PRISMARINE).setHardness(2.5F), "chest_prismarine");
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        craftGlacial = config.getBoolean("Craft Glacial Chest", this.configName, true, "Should the glacial chest be craftable??");
        craftGlass = config.getBoolean("Craft Glass Chest", this.configName, true, "Should the glass chest be craftable??");
        craftJungle = config.getBoolean("Craft Jungle Chest", this.configName, true, "Should the jungle chest be craftable??");
        craftMagic = config.getBoolean("Craft Magic Chest", this.configName, true, "Should the magic chest be craftable??");
        craftNether = config.getBoolean("Craft Nether Chest", this.configName, true, "Should the nether chest be craftable??");
        craftRoyal = config.getBoolean("Craft Royal Chest", this.configName, true, "Should the royal chest be craftable??");
        craftSandstone = config.getBoolean("Craft Sandstone Chest", this.configName, true, "Should the sandstone chest be craftable??");
        craftPrismarine = config.getBoolean("Craft Prismarine Chest", this.configName, true, "Should the prismarine chest be craftable??");
    }
    
    @Override
    public void setupRecipes () {
        
        if (craftGlacial)
            GameRegistry.addRecipe(new ShapedOreRecipe(glacial, new Object[] { "xyx", "ycy", "xyx", 'x', Blocks.SNOW, 'y', Blocks.ICE, 'c', OreDictUtils.CHEST }));
        
        if (craftGlass)
            GameRegistry.addRecipe(new ShapedOreRecipe(glass, new Object[] { "xyx", "ycy", "xyx", 'x', OreDictUtils.BLOCK_GLASS, 'y', OreDictUtils.PANE_GLASS, 'c', OreDictUtils.CHEST }));
        
        if (craftJungle)
            GameRegistry.addRecipe(new ShapedOreRecipe(jungle, new Object[] { "xyx", "ycy", "xyx", 'x', OreDictUtils.TREE_LEAVES, 'y', OreDictUtils.TREE_SAPLING, 'c', OreDictUtils.CHEST }));
        
        if (craftMagic)
            GameRegistry.addRecipe(new ShapedOreRecipe(magic, new Object[] { "xyx", "ycy", "xyx", 'x', OreDictUtils.INGOT_GOLD, 'y', new ItemStack(Blocks.WOOL, 1, 11), 'c', OreDictUtils.CHEST }));
        
        if (craftNether)
            GameRegistry.addRecipe(new ShapedOreRecipe(nether, new Object[] { "xqx", "ycy", "xyx", 'x', OreDictUtils.NETHERRACK, 'q', OreDictUtils.GEM_QUARTZ, 'y', OreDictUtils.INGOT_BRICK_NETHER, 'c', OreDictUtils.CHEST }));
        
        if (craftRoyal)
            GameRegistry.addRecipe(new ShapedOreRecipe(royal, new Object[] { "xyx", "ycy", "xyx", 'x', OreDictUtils.INGOT_GOLD, 'y', new ItemStack(Blocks.WOOL, 1, 14), 'c', OreDictUtils.CHEST }));
        
        if (craftSandstone)
            GameRegistry.addRecipe(new ShapedOreRecipe(sandstone, new Object[] { "xxx", "xcx", "xxx", 'x', OreDictUtils.SANDSTONE, 'c', OreDictUtils.CHEST }));
        
        if (craftPrismarine)
            GameRegistry.addRecipe(new ShapedOreRecipe(prismarine, new Object[] { "xyx", "ycy", "xyx", 'x', OreDictUtils.GEM_PRISMARINE, 'y', OreDictUtils.DUST_PRISMARINE, 'c', OreDictUtils.CHEST }));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {
        
        stateMap = new StateMapChest();
        ModUtils.registerBlockInvModel(glacial);
        ModelLoader.setCustomStateMapper(glacial, stateMap);
        ModUtils.registerBlockInvModel(glass);
        ModelLoader.setCustomStateMapper(glass, stateMap);
        ModUtils.registerBlockInvModel(jungle);
        ModelLoader.setCustomStateMapper(jungle, stateMap);
        ModUtils.registerBlockInvModel(magic);
        ModelLoader.setCustomStateMapper(magic, stateMap);
        ModUtils.registerBlockInvModel(nether);
        ModelLoader.setCustomStateMapper(nether, stateMap);
        ModUtils.registerBlockInvModel(royal);
        ModelLoader.setCustomStateMapper(royal, stateMap);
        ModUtils.registerBlockInvModel(sandstone);
        ModelLoader.setCustomStateMapper(sandstone, stateMap);
        ModUtils.registerBlockInvModel(prismarine);
        ModelLoader.setCustomStateMapper(prismarine, stateMap);
    }
    
    private Block createChest (Block block, String id) {
        
        ModUtils.registerBlock(block, id);
        OreDictionary.registerOre(OreDictUtils.CHEST, block);
        return block;
    }
}