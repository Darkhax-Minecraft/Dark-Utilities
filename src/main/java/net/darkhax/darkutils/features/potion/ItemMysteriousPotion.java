package net.darkhax.darkutils.features.potion;

import java.util.List;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.libs.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMysteriousPotion extends Item {

    public static String[] varients = new String[] { "cure", "zombie" };

    public ItemMysteriousPotion() {

        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {

        if (stack.getMetadata() == 0)
            if (target instanceof EntityZombie) {

                if (player.worldObj.isRemote)
                    return true;

                final EntityZombie zombie = (EntityZombie) target;

                if (zombie.isVillager()) {

                    ItemStackUtils.decreaseStackSize(stack, 1);
                    zombie.startConversion(Constants.RANDOM.nextInt(2401) + 3600);
                    return true;
                }
            }

        if (target instanceof EntityVillager && stack.getMetadata() == 1) {

            if (player.worldObj.isRemote)
                return true;

            ItemStackUtils.decreaseStackSize(stack, 1);

            final EntityVillager villager = (EntityVillager) target;
            final EntityZombie zombie = new EntityZombie(player.worldObj);
            zombie.copyLocationAndAnglesFrom(target);
            zombie.setVillagerType(villager.getProfessionForge());
            zombie.setNoAI(villager.isAIDisabled());

            if (villager.isChild())
                zombie.setChild(true);

            if (villager.hasCustomName()) {

                zombie.setCustomNameTag(villager.getCustomNameTag());
                zombie.setAlwaysRenderNameTag(villager.getAlwaysRenderNameTag());
            }

            villager.setDead();
            player.worldObj.spawnEntityInWorld(zombie);
            return true;
        }

        return false;
    }

    @Override
    public ItemStack onItemUseFinish (ItemStack stack, World world, EntityLivingBase user) {

        if (user instanceof EntityPlayer && !((EntityPlayer) user).capabilities.isCreativeMode)
            ItemStackUtils.decreaseStackSize(stack, 1);

        if (!world.isRemote) {

            final int meta = stack.getMetadata();

            if (meta == 0)
                user.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 400, 0));

            if (meta == 1)
                user.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400, 0));
        }

        return stack;
    }

    @Override
    public int getMaxItemUseDuration (ItemStack stack) {

        return 32;
    }

    @Override
    public EnumAction getItemUseAction (ItemStack stack) {

        return EnumAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick (ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        playerIn.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List<ItemStack> subItems) {

        for (int meta = 0; meta < varients.length; meta++)
            subItems.add(new ItemStack(this, 1, meta));
    }
}