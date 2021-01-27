package net.darkhax.darkutils.addons.curios;

import java.util.function.BiConsumer;

import net.darkhax.darkutils.features.charms.ItemCharm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CharmCapabilityProvider implements ICapabilityProvider {
    
    private final ItemCharm item;
    
    private final CharmCapability cap;
    
    private final LazyOptional<ICurio> capOptional;
    
    public CharmCapabilityProvider(ItemCharm item) {
        
        this.item = item;
        this.cap = new CharmCapability();
        this.capOptional = LazyOptional.of( () -> this.cap);
    }
    
    @Override
    public <T> LazyOptional<T> getCapability (Capability<T> cap, Direction side) {
        
        return CuriosCapability.ITEM.orEmpty(cap, this.capOptional);
    }
    
    class CharmCapability implements ICurio {
        @Override
        public void curioTick (String identifier, int index, LivingEntity livingEntity) {
            
            CuriosApi.getCuriosHelper().findEquippedCurio(CharmCapabilityProvider.this.item, livingEntity).ifPresent(slot -> {
                
                final BiConsumer<Entity, ItemStack> tickEffect = CharmCapabilityProvider.this.item.getInventoryTickEffect();
                
                if (tickEffect != null) {
                    
                    tickEffect.accept(livingEntity, slot.getRight());
                }
                
            });
        }
    }
}
