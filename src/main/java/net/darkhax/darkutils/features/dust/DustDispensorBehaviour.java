package net.darkhax.darkutils.features.dust;

import net.minecraft.block.BlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;

public class DustDispensorBehaviour extends OptionalDispenseBehavior {
    
    public static final DustDispensorBehaviour BEHAVIOR = new DustDispensorBehaviour();
    
    @Override
    public ItemStack dispenseStack (IBlockSource source, ItemStack stack) {
        
        final BlockState state = source.getBlockState();
        
        if (DustHandler.tryBlockConversion(source.getWorld(), source.getBlockPos().offset(state.get(BlockStateProperties.FACING)), stack)) {
            
            stack.shrink(1);
            this.successful = true;
        }
        
        else {
            
            this.successful = false;
        }
        
        return stack;
    }
}