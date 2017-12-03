package net.darkhax.darkutils.features.material;

import net.darkhax.bookshelf.item.ItemSubType;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMaterial extends ItemSubType {

    public static String[] varients = new String[] { "wither", "unstable", "cream", "sugar" };

    public ItemMaterial () {

        super(varients);
    }

    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {

        final World world = target.getEntityWorld();

        if (!world.isRemote && stack.getMetadata() == 0 && target instanceof AbstractSkeleton && !(target instanceof EntityWitherSkeleton)) {

            final EntityWitherSkeleton witherSkeleton = new EntityWitherSkeleton(world);
            witherSkeleton.copyLocationAndAnglesFrom(target);
            witherSkeleton.setHealth(target.getHealth());

            for (final EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {

                if (target.hasItemInSlot(slot)) {

                    witherSkeleton.setItemStackToSlot(slot, target.getItemStackFromSlot(slot));
                    target.setItemStackToSlot(slot, ItemStack.EMPTY);
                    StackUtils.decreaseStackSize(stack, 1);
                }
            }

            world.removeEntity(target);
            world.spawnEntity(witherSkeleton);
            return true;
        }

        return false;
    }
}