package net.darkhax.darkutils.addons.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.darkutils.addons.AddonHandler;
import net.darkhax.darkutils.blocks.BlockFilter;
import net.darkhax.darkutils.blocks.BlockTrapEffect;
import net.darkhax.darkutils.handler.ContentHandler;
import net.darkhax.darkutils.items.ItemMaterial;
import net.darkhax.darkutils.items.ItemMysteriousPotion;
import net.darkhax.darkutils.items.ItemRingEnchanted;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class DarkUtilsJEIPlugin extends BlankModPlugin {
    
    @Override
    public void register (IModRegistry registry) {
        
        registry.addDescription(new ItemStack(ContentHandler.blockEnderTether), "jei.darkutils.endertether.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockTrapMovement), "jei.darkutils.trap.vector.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockTrapMovementFast), "jei.darkutils.trap.vector.fast.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockTrapMovementHyper), "jei.darkutils.trap.vector.hyper.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockGrate), "jei.darkutils.grate.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockTimer), "jei.darkutils.timer.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockAntiSlime), "jei.darkutils.antislime.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockDetector), "jei.darkutils.detector.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockSneakyBlock), "jei.darkutils.sneaky.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockSneakyLever), "jei.darkutils.sneaky.lever.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockSneakyGhost), "jei.darkutils.sneaky.ghost.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockSneakyTorch), "jei.darkutils.sneaky.torch.desc");
        registry.addDescription(new ItemStack(ContentHandler.blockSneakyObsidian), "jei.darkutils.sneaky.obsidian.desc");
        
        for (BlockTrapEffect.EnumType type : BlockTrapEffect.EnumType.values())
            registry.addDescription(new ItemStack(ContentHandler.blockTrap, 1, type.meta), "jei.darkutils.trap." + type.type + ".desc");
            
        for (BlockFilter.EnumType type : BlockFilter.EnumType.values())
            registry.addDescription(new ItemStack(ContentHandler.blockFilter, 1, type.meta), "jei.darkutils.filter." + type.type + ".desc");
            
        for (int meta = 0; meta < ItemMaterial.varients.length; meta++)
            registry.addDescription(new ItemStack(ContentHandler.itemMaterial, 1, meta), "jei.darkutils.material." + ItemMaterial.varients[meta] + ".desc");
            
        for (int meta = 0; meta < ItemMysteriousPotion.varients.length; meta++)
            registry.addDescription(new ItemStack(ContentHandler.itemPotion, 1, meta), "jei.darkutils.potion." + ItemMysteriousPotion.varients[meta] + ".desc");
            
        for (int meta = 0; meta < ItemRingEnchanted.varients.length; meta++)
            registry.addDescription(new ItemStack(ContentHandler.itemEnchantedRing, 1, meta), "jei.darkutils.ring." + ItemRingEnchanted.varients[meta] + ".desc");
            
        AddonHandler.onJEIReady(registry);
    }
}