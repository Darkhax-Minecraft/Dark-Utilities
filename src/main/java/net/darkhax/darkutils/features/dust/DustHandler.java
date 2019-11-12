package net.darkhax.darkutils.features.dust;

import net.darkhax.bookshelf.util.WorldUtils;
import net.darkhax.darkutils.DarkUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;

public class DustHandler {
    
    public static void onPlayerUseItem (PlayerInteractEvent.RightClickBlock event) {
        
        final boolean didConvert = tryBlockConversion(event.getWorld(), event.getPos(), event.getItemStack());
        
        if (didConvert) {
            
            event.setUseBlock(Result.DENY);
            event.setUseItem(Result.DENY);
        }
    }
    
    public static boolean tryBlockConversion (World world, BlockPos pos, ItemStack item) {
        
        final BlockState oldState = world.getBlockState(pos);
        
        // Loop through all dust recipes
        for (final RecipeDustChange recipe : WorldUtils.getRecipeList(DarkUtils.content.recipeTypeDustChange, world.getRecipeManager())) {
            
            // Test if the dust recipe is valid
            if (recipe.isValid(oldState, item)) {
                
                if (!world.isRemote) {
                    
                    BlockState newState = recipe.getOutput(world.getRandom()).getDefaultState();
                    
                    // Copy all applicable properties of the existing state to the converted
                    // state.
                    for (final IProperty oldProperty : oldState.getProperties()) {
                        
                        if (newState.has(oldProperty)) {
                            
                            newState = newState.with(oldProperty, oldState.get(oldProperty));
                        }
                    }
                    
                    world.playEvent(2001, pos, Block.getStateId(newState));
                    world.setBlockState(pos, newState, 11);
                    item.shrink(1);
                }
                
                return true;
            }
        }
        
        return false;
    }
}
