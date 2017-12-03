package net.darkhax.darkutils.features.material;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class BehaviorDispenseWitherDust extends BehaviorDefaultDispenseItem {

    @Override
    public ItemStack dispenseStack (IBlockSource source, ItemStack stack) {

        if (stack.getMetadata() == 0) {

            final BlockPos pos = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));

            for (final EntityLiving living : source.getWorld().getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos))) {

                ItemMaterial.transformEntity(source.getWorld(), living, stack);
            }
        }

        return stack;
    }
}
