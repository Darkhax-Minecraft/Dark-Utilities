package net.darkhax.darkutilities.common.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.darkhax.bookshelf.common.api.util.TextHelper;
import net.darkhax.darkutilities.common.Constants;
import net.darkhax.darkutilities.common.component.NameStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    public abstract Item getItem();

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void displayName(CallbackInfoReturnable<Component> cir) {
        final ItemStack self = (ItemStack) (Object) this;
        final NameStyle nameStyle = self.get(NameStyle.COMPONENT.get());
        MutableComponent name = TextHelper.mutable(cir.getReturnValue());
        if (nameStyle != null) {
            name.withStyle(style -> {
                style = style.withFont(nameStyle.fontID());
                if (nameStyle.color().isPresent()) {
                    style = style.withColor(nameStyle.color().get().getTextColor());
                }
                return style;
            });
            cir.setReturnValue(name);
        }
    }

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V", shift = At.Shift.AFTER))
    private void addTooltipEntries(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> list) {
        final ResourceLocation id = BuiltInRegistries.ITEM.getKey(this.getItem());
        if (id.getNamespace().equals(Constants.MOD_ID)) {
            list.add(Component.translatable("tooltip.darkutils." + id.getPath()).withStyle(ChatFormatting.GRAY));
        }
    }
}
