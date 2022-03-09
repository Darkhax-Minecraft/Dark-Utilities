package net.darkhax.darkutilities.features.tomes;

import net.darkhax.darkutilities.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutilities.mixin.AccessorPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TomeEffects {

    public static final TomeEffect<Player, InteractionResultHolder<ItemStack>> RESET_ENCHANTMENT_SEED = TomeEffect.withExpCost(TomeEffect.withCooldown(TomeEffects::resetEnchantmentSeed, 20 * 30), 20);

    public static final TomeEffect<BlockPos, InteractionResult> HIDE_BLOCK = TomeEffect.withCooldown(TomeEffects::hideBlocks, 20);
    public static final TomeEffect<Entity, InteractionResult> HIDE_ENTITY = TomeEffect.withCooldown(TomeEffects::hideEntity, 100);

    @Nullable
    private static InteractionResultHolder<ItemStack> resetEnchantmentSeed(ItemStack usedStack, Player player, InteractionHand hand, Player target) {

        if (player instanceof AccessorPlayer accessor) {

            accessor.darkutils$setEnchantmentSeed(player.getRandom().nextInt());
            return InteractionResultHolder.success(usedStack);
        }

        return null;
    }

    @Nullable
    private static InteractionResult hideBlocks(ItemStack usedStack, Player player, InteractionHand hand, BlockPos target) {

        final BlockState state = player.level.getBlockState(target);

        if (state.getBlock() instanceof BlockFlatTile && !state.getValue(BlockFlatTile.HIDDEN)) {

            player.level.setBlockAndUpdate(target, state.setValue(BlockFlatTile.HIDDEN, true));
            return InteractionResult.SUCCESS;
        }

        return null;
    }

    @Nullable
    private static InteractionResult hideEntity(ItemStack usedStack, Player player, InteractionHand hand, Entity target) {

        if ((target instanceof ItemFrame || target instanceof ArmorStand) && !target.isInvisible()) {

            target.setInvisible(true);
            return InteractionResult.SUCCESS;
        }

        else if (target instanceof LivingEntity living && !living.hasEffect(MobEffects.INVISIBILITY)) {

            living.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20 * 60 * 3));
            return InteractionResult.SUCCESS;
        }

        return null;
    }
}