package net.darkhax.darkutils.blocks;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.BlockCake;

public class BlockCakeBase extends BlockCake {
    
    public BlockCakeBase(String name) {
        
        this.setUnlocalizedName("darkutils." + name);
        this.setCreativeTab(DarkUtils.tab);
    }
}
