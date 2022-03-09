package net.darkhax.darkutilities.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public interface IItemBlockProvider {

    BlockItem createItemBlock(Block block);
}
