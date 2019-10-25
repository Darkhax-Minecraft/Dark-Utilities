package net.darkhax.darkutils.addons.curio;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

public class CurioAddon implements ICurioAddon {
    
    @Override
    public boolean isCurioApiAvailable () {
        
        return true;
    }
    
    @Override
    public boolean hasCurioItem (Item item, LivingEntity user) {
        
        return !this.getCurioOfType(item, user).isEmpty();
    }
    
    @Override
    public ItemStack getCurioOfType (Item item, LivingEntity user) {
        
        final Optional<ImmutableTriple<String, Integer, ItemStack>> curio = CuriosAPI.getCurioEquipped(item, user);
        return curio.isPresent() ? curio.get().getRight() : ItemStack.EMPTY;
    }
    
    @Override
    public void enqueCommunication () {
        
        InterModComms.sendTo("curios", "register_type", () -> new CurioIMCMessage("charm").setSize(2).setEnabled(true).setHidden(false));
    }
}