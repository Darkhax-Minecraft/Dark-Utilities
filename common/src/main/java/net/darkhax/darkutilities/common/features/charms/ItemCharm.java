package net.darkhax.darkutilities.common.features.charms;

import net.darkhax.darkutilities.common.DarkUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class ItemCharm extends Item {

    public static final TagKey<Item> GLUTTONY = TagKey.create(Registries.ITEM, DarkUtils.id("charm_gluttony"));

    private final CharmEffect effect;

    public ItemCharm(Properties properties, CharmEffect effect) {
        super(properties);
        this.effect = effect;
    }

    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(itemStack, level, owner, slot);
        this.tickEffect(itemStack, level, owner);
    }

    public void tickEffect(ItemStack itemStack, Level level, Entity owner) {
        if (this.effect != null && owner instanceof LivingEntity living) {
            this.effect.onUserTick(itemStack, level, living);
        }
    }
}