package net.darkhax.darkutilities.common.features.charms;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.darkutilities.common.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ItemCharm extends Item {

    public static final Supplier<Item> GLUTTONY = CachedSupplier.of(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "charm_gluttony"));
    
    private final CharmEffect effect;

    public ItemCharm() {
        this(null);
    }

    public ItemCharm(CharmEffect effect) {
        this(new Properties().stacksTo(1), effect);
    }

    public ItemCharm(Properties properties, CharmEffect effect) {
        super(properties);
        this.effect = effect;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity user, int slotIndex, boolean selected) {
        super.inventoryTick(stack, world, user, slotIndex, selected);
        if (this.effect != null && user instanceof LivingEntity living) {
            this.effect.onUserTick(stack, world, living);
        }
    }
}