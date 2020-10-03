package net.darkhax.darkutils.features.charms;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import net.darkhax.darkutils.addons.curios.CuriosAddon;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModList;

public class ItemCharm extends Item {
    
    @Nullable
    private BiConsumer<Entity, ItemStack> inventoryTickEffect;
    
    public ItemCharm() {
        
        super(new Properties().maxStackSize(1).rarity(Rarity.RARE));
    }
    
    public ItemCharm setTickingEffect (BiConsumer<Entity, ItemStack> inventoryTickEffect) {
        
        this.inventoryTickEffect = inventoryTickEffect;
        return this;
    }
    
    public <T extends Event> ItemCharm addEvent (Consumer<T> consumer) {
        
        MinecraftForge.EVENT_BUS.addListener(consumer);
        return this;
    }
    
    @Override
    public void inventoryTick (ItemStack stack, World world, Entity user, int slot, boolean selected) {
        
        if (this.inventoryTickEffect != null) {
            
            this.inventoryTickEffect.accept(user, stack);
        }
    }

    @Nullable
    public BiConsumer<Entity, ItemStack> getInventoryTickEffect() {

        return this.inventoryTickEffect;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {

        if (ModList.get().isLoaded("curios")) {

            return CuriosAddon.getCapabilityProvider(this);
        }

        return null;
    }
}