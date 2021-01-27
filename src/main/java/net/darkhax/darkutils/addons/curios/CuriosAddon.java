package net.darkhax.darkutils.addons.curios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.darkhax.darkutils.features.charms.ItemCharm;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosAddon {
    public static final String CURIOS_MOD_ID = "curios";
    
    @SubscribeEvent
    public static void onIMCEnqueue (InterModEnqueueEvent event) {
        
        InterModComms.sendTo(CURIOS_MOD_ID, SlotTypeMessage.REGISTER_TYPE, SlotTypePreset.CHARM.getMessageBuilder()::build);
    }
    
    public static ICapabilityProvider getCapabilityProvider (ItemCharm item) {
        
        return new CharmCapabilityProvider(item);
    }
    
    public static List<ItemStack> getStacksFromPlayer (PlayerEntity player, final Item item) {
        
        final List<ItemStack> stacks = new ArrayList<>();
        
        final LazyOptional<ICuriosItemHandler> curios = CuriosApi.getCuriosHelper().getCuriosHandler(player);
        
        curios.map(ICuriosItemHandler::getCurios).map(Map::values).ifPresent(handlers -> {
            
            for (final ICurioStacksHandler curiosStack : handlers) {
                
                final IDynamicStackHandler stackHandler = curiosStack.getStacks();
                
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    
                    final ItemStack stack = stackHandler.getStackInSlot(i);
                    
                    if (stack.getItem() == item) {
                        
                        stacks.add(stack);
                    }
                }
            }
        });
        
        return stacks;
    }
}
