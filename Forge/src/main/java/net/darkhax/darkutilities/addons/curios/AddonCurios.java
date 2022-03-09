package net.darkhax.darkutilities.addons.curios;

import net.darkhax.darkutilities.DarkUtilsCommon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.function.Predicate;

public class AddonCurios {

    public static final String CURIOS_MOD_ID = "curios";

    @SubscribeEvent
    public static void onIMCEnqueue(InterModEnqueueEvent event) {

        InterModComms.sendTo(CURIOS_MOD_ID, SlotTypeMessage.REGISTER_TYPE, SlotTypePreset.CHARM.getMessageBuilder()::build);
        DarkUtilsCommon.charmResolvers.add(AddonCurios::hasCurio);
    }

    private static boolean hasCurio(Player player, Predicate<ItemStack> predicate) {

        return !CuriosApi.getCuriosHelper().findCurios(player, predicate).isEmpty();
    }
}
