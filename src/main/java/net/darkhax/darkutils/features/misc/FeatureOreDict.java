package net.darkhax.darkutils.features.misc;

import net.darkhax.darkutils.features.Feature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class FeatureOreDict extends Feature {
    
    /**
     * When enabled, adds wooden fences to the ore dictionary under the name fenceGateWood.
     */
    private boolean addFenceGates = true;
    
    /**
     * When enabled, adds new vanilla stone variants to the ore dictionary under the name
     * stone.
     */
    private boolean addStoneTypes = true;
    
    @Override
    public void onPreInit () {
        
        if (addFenceGates) {
            
            OreDictionary.registerOre("fenceGateWood", Blocks.OAK_FENCE_GATE);
            OreDictionary.registerOre("fenceGateWood", Blocks.ACACIA_FENCE_GATE);
            OreDictionary.registerOre("fenceGateWood", Blocks.BIRCH_FENCE_GATE);
            OreDictionary.registerOre("fenceGateWood", Blocks.DARK_OAK_FENCE_GATE);
            OreDictionary.registerOre("fenceGateWood", Blocks.JUNGLE_FENCE_GATE);
            OreDictionary.registerOre("fenceGateWood", Blocks.SPRUCE_FENCE_GATE);
        }
        
        if (addStoneTypes) {
            
            OreDictionary.registerOre("stone", new ItemStack(Blocks.STONE, 1, 1));
            OreDictionary.registerOre("stone", new ItemStack(Blocks.STONE, 1, 2));
            OreDictionary.registerOre("stone", new ItemStack(Blocks.STONE, 1, 3));
            OreDictionary.registerOre("stone", new ItemStack(Blocks.STONE, 1, 4));
            OreDictionary.registerOre("stone", new ItemStack(Blocks.STONE, 1, 5));
            OreDictionary.registerOre("stone", new ItemStack(Blocks.STONE, 1, 6));
        }
    }
    
    @Override
    public void setupConfiguration (Configuration config) {
        
        this.addFenceGates = config.getBoolean("oredictVanillaFenceGate", this.configName, true, "When enabled, DarkUtils will register vanilla's wooden fence gates with Forge's ore dictionary under the name fenceGateWood. Disabling this will prevent several recipes in DarkUtils from working.");
        this.addStoneTypes = config.getBoolean("oredictVanillaStone", this.configName, true, "When enabled, DarkUtils will register vanilla's stone blocks with Forge's ore dictionary using the name stone. Disabling this will prevent several recipes in DarkUtils from working.");
    }
}