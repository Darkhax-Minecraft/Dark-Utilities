package net.darkhax.darkutils.items;

import java.util.List;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.addons.thaumcraft.ThaumcraftAddon;
import net.darkhax.darkutils.libs.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMysteriousPotion extends Item {
    
    public static String[] varients = new String[] { "cure", "zombie" };
    
    public ItemMysteriousPotion() {
        
        this.setCreativeTab(DarkUtils.tab);
        this.setUnlocalizedName("darkutils.potion");
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        
        if (stack.getMetadata() == 0) {
            
            if (entity instanceof EntityZombie) {
                
                if (player.worldObj.isRemote)
                    return true;
                    
                EntityZombie zombie = (EntityZombie) entity;
                
                if (zombie.isVillager()) {
                    
                    ItemStackUtils.decreaseStackSize(stack, 1);
                    zombie.startConversion(Constants.RANDOM.nextInt(2401) + 3600);
                    return true;
                }
            }
            
            else if (Loader.isModLoaded("Thaumcraft"))
                return ThaumcraftAddon.cureHook(stack, player, entity);
        }
        
        if (entity instanceof EntityVillager && stack.getMetadata() == 1) {
            
            if (player.worldObj.isRemote)
                return true;
                
            ItemStackUtils.decreaseStackSize(stack, 1);
            
            EntityVillager villager = (EntityVillager) entity;
            EntityZombie zombie = new EntityZombie(player.worldObj);
            zombie.copyLocationAndAnglesFrom(entity);
            zombie.setVillager(true);
            zombie.setNoAI(villager.isAIDisabled());
            
            if (villager.isChild())
                zombie.setChild(true);
                
            if (villager.hasCustomName()) {
                
                zombie.setCustomNameTag(villager.getCustomNameTag());
                zombie.setAlwaysRenderNameTag(villager.getAlwaysRenderNameTag());
            }
            
            villager.setDead();
            player.worldObj.spawnEntityInWorld(zombie);
            player.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, zombie.getPosition(), 0);
            return true;
        }
        
        return false;
    }
    
    @Override
    public ItemStack onItemUseFinish (ItemStack stack, World world, EntityPlayer player) {
        
        if (!player.capabilities.isCreativeMode)
            ItemStackUtils.decreaseStackSize(stack, 1);
            
        if (!world.isRemote) {
            
            int meta = stack.getMetadata();
            
            if (meta == 0)
                player.addPotionEffect(new PotionEffect(Potion.saturation.id, 400, 0));
                
            if (meta == 1)
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 400, 0));
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
    public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player) {
        
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack (ItemStack stack, int renderPass) {
        
        return renderPass > 0 ? 16777215 : (stack.getMetadata() == 0) ? 16710911 : 7371335;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List<ItemStack> subItems) {
        
        for (int meta = 0; meta < varients.length; meta++)
            subItems.add(new ItemStack(this, 1, meta));
    }
}