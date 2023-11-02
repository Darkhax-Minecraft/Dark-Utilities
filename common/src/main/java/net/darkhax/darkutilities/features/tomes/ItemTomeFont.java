package net.darkhax.darkutilities.features.tomes;

import net.darkhax.bookshelf.api.util.TextHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.UnaryOperator;

public class ItemTomeFont extends ItemTome {

    private final ResourceLocation fontId;
    private final Component fontPreview;

    public ItemTomeFont(ResourceLocation fontId) {

        super(null, fontifyEntity(fontId), fontifyBlock(fontId));
        this.fontId = fontId;
        this.fontPreview = TextHelper.applyFont(Component.translatable("font." + fontId.getNamespace() + "." + fontId.getPath() + ".preview"), this.fontId);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {

        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(this.fontPreview);
    }

    public static TomeEffect<BlockPos, InteractionResult> fontifyBlock(ResourceLocation fontId) {

        return (stack, player, hand, target) -> {

            final BlockState block = player.level().getBlockState(target);
            final BlockEntity blockEntity = player.level().getBlockEntity(target);

            if (blockEntity instanceof BaseContainerBlockEntity container) {

                if (stack.hasCustomHoverName()) {

                    container.setCustomName(TextHelper.applyFont(stack.getHoverName(), fontId));
                    player.level().levelEvent(3002, target, -1);
                    return InteractionResult.SUCCESS;
                }

                container.setCustomName(TextHelper.applyFont(container.getDisplayName(), fontId));
                player.level().levelEvent(3002, target, -1);
                return InteractionResult.SUCCESS;
            }

            else if (blockEntity instanceof SignBlockEntity sign) {

                sign.updateText(applySignFont(fontId), true);
                sign.updateText(applySignFont(fontId), false);
                sign.getLevel().sendBlockUpdated(sign.getBlockPos(), sign.getBlockState(), sign.getBlockState(), 3);
                player.level().levelEvent(3002, target, -1);
            }

            return null;
        };
    }

    private static UnaryOperator<SignText> applySignFont(ResourceLocation fontId) {
        return text -> {

            SignText newText = text;

            for (int i = 0; i < 4; i++) {

                final Component lineText = text.getMessage(i, false);

                if (lineText.getContents() == ComponentContents.EMPTY) {

                    continue;
                }

                newText = newText.setMessage(i, TextHelper.applyFont(lineText.copy(), fontId));
            }

            return newText;
        };
    }

    public static TomeEffect<Entity, InteractionResult> fontifyEntity(ResourceLocation fontId) {

        return (stack, player, hand, target) -> {

            if (stack.hasCustomHoverName()) {

                target.setCustomName(TextHelper.applyFont(stack.getHoverName(), fontId));
                return InteractionResult.SUCCESS;
            }

            else if (target.hasCustomName()) {

                target.setCustomName(TextHelper.applyFont(target.getCustomName(), fontId));
                return InteractionResult.SUCCESS;
            }

            return null;
        };
    }
}
