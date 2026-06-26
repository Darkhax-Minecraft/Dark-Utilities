package net.darkhax.darkutilities.common.mixin;

import net.darkhax.bookshelf.common.api.util.TextHelper;
import net.darkhax.darkutilities.common.DarkUtils;
import net.darkhax.darkutilities.common.component.NameStyle;
import net.darkhax.darkutilities.common.features.charms.ItemCharm;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    public abstract Item getItem();

    @Inject(method = "inventoryTick", at = @At("RETURN"))
    private void onInventoryTick(Level level, Entity owner, @Nullable EquipmentSlot slot, CallbackInfo ci) {
        if (level.isClientSide() && this.getItem() instanceof ItemCharm charm && owner instanceof LivingEntity living) {
            charm.tickEffect((ItemStack) (Object) this, level, living);
        }
    }

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void displayName(CallbackInfoReturnable<Component> cir) {
        final ItemStack self = (ItemStack) (Object) this;
        final NameStyle nameStyle = self.get(NameStyle.COMPONENT.get());
        MutableComponent name = TextHelper.mutable(cir.getReturnValue());
        if (nameStyle != null) {
            name.withStyle(style -> {
                style = style.withFont(nameStyle.description());
                if (nameStyle.color().isPresent()) {
                    style = style.withColor(nameStyle.color().get().getTextColor());
                }
                return style;
            });
            cir.setReturnValue(name);
        }
    }

    @Inject(method = "addDetailsToTooltip(Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;Ljava/util/function/Consumer;)V", at = @At(value = "HEAD"))
    private void addTooltipEntries(Item.TooltipContext context, TooltipDisplay display, @Nullable Player player, TooltipFlag tooltipFlag, Consumer<Component> builder, CallbackInfo ci) {
        final Identifier id = BuiltInRegistries.ITEM.getKey(this.getItem());
        if (id.getNamespace().equals(DarkUtils.MOD_ID)) {
            builder.accept(Component.translatable("tooltip.darkutils." + id.getPath()).withStyle(ChatFormatting.GRAY));
        }
    }
}
