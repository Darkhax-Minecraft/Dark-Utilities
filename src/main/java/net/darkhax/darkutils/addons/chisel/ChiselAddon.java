package net.darkhax.darkutils.addons.chisel;

import mezz.jei.api.IModRegistry;
import net.darkhax.darkutils.addons.ModAddon;
import net.minecraft.block.Block;
import team.chisel.api.carving.CarvingUtils;

public class ChiselAddon implements ModAddon {
    
    @Override
    public void onPreInit () {
    
    }
    
    @Override
    public void onInit () {
    
    }
    
    @Override
    public void onPostInit () {
    
    }
    
    @Override
    public void onClientPreInit () {
    
    }
    
    @Override
    public void onJEIReady (IModRegistry registry) {
    
    }
    
    /**
     * Registers a Block with the Chisel mod. This method is intended for use with blocks that
     * have multiple variants, such as wool or planks.
     * 
     * @param group The group to add this block to.
     * @param block The block to add to the group.
     * @param maxMeta The highest meta used for the block you want to add. If your block has 5
     *            variants using meta 0 through 4, you will want to use 5.
     */
    private static void addCarvingVariant (String group, Block block, int maxMeta) {
        
        for (int meta = 0; meta < maxMeta; meta++)
            CarvingUtils.chisel.addVariation(group, block.getStateFromMeta(meta), 5);
    }
}