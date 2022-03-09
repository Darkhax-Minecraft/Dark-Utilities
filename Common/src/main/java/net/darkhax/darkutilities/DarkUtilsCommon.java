package net.darkhax.darkutilities;

import net.darkhax.bookshelf.api.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class DarkUtilsCommon {

    public static final List<BiFunction<Player, Predicate<ItemStack>, Boolean>> charmResolvers = new ArrayList<>();

    private static DarkUtilsCommon instance;

    public final Content content;

    public DarkUtilsCommon() {

        this.content = new Content();
        Services.EVENTS.addItemTooltipListener(this::addDescriptionTooltips);

        // Test vanilla player inventory for charm items.
        charmResolvers.add(DarkUtilsCommon::hasItemInVanillaInventory);
    }

    public static DarkUtilsCommon getInstance() {

        if (instance == null) {

            instance = new DarkUtilsCommon();
        }

        return instance;
    }

    private void addDescriptionTooltips(ItemStack stack, List<Component> tooltip, TooltipFlag flag) {

        final Component description = this.content.tooltipCache.get(stack.getItem());

        if (description != null) {

            tooltip.add(description);
        }
    }

    public static boolean hasItem(Player player, Item item) {

        return charmResolvers.stream().anyMatch(func -> func.apply(player, s -> s.is(item)));
    }

    private static boolean hasItemInVanillaInventory(Player player, Predicate<ItemStack> predicate) {

        for (final ItemStack stack : player.getInventory().items) {

            if (predicate.test(stack)) {

                return true;
            }
        }

        for (final EquipmentSlot slotType : EquipmentSlot.values()) {

            if (predicate.test(player.getItemBySlot(slotType))) {

                return true;
            }
        }

        return false;
    }
}