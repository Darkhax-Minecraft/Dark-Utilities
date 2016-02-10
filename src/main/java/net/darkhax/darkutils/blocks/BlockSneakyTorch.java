package net.darkhax.darkutils.blocks;

import net.minecraft.block.material.Material;

public class BlockSneakyTorch extends BlockSneaky {
    
    public BlockSneakyTorch() {
        
        super(Material.circuits);
        this.setUnlocalizedName("darkutils.sneaky.torch");
        this.setHardness(0.0F);
        this.setResistance(0F);
        this.setLightLevel(0.9375F);
        this.setStepSound(soundTypeWood);
    }
}