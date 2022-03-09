package net.darkhax.darkutilities.features.tomes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ItemTome extends Item {

    @Nullable
    private TomeEffect<Player, InteractionResultHolder<ItemStack>> userEffect;

    @Nullable
    private TomeEffect<Entity, InteractionResult> entityEffect;

    @Nullable
    private TomeEffect<BlockPos, InteractionResult> blockEffect;

    public ItemTome() {

        this(null, null, null, new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    public ItemTome(@Nullable TomeEffect<Player, InteractionResultHolder<ItemStack>> userEffect, @Nullable TomeEffect<Entity, InteractionResult> entityEffect, @Nullable TomeEffect<BlockPos, InteractionResult> blockEffect) {

        this(userEffect, entityEffect, blockEffect, new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    public ItemTome(@Nullable TomeEffect<Player, InteractionResultHolder<ItemStack>> userEffect, @Nullable TomeEffect<Entity, InteractionResult> entityEffect, @Nullable TomeEffect<BlockPos, InteractionResult> blockEffect, Properties properties) {

        super(properties);
        this.userEffect = userEffect;
        this.entityEffect = entityEffect;
        this.blockEffect = blockEffect;
    }

    public ItemTome withUserEffect(TomeEffect<Player, InteractionResultHolder<ItemStack>> userEffect) {

        this.userEffect = userEffect;
        return this;
    }

    public ItemTome withBlockEffect(TomeEffect<BlockPos, InteractionResult> blockEffect) {

        this.blockEffect = blockEffect;
        return this;
    }

    public ItemTome withEntityEffect(TomeEffect<Entity, InteractionResult> entityEffect) {

        this.entityEffect = entityEffect;
        return this;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity target, InteractionHand hand) {

        InteractionResult result = null;

        if (this.entityEffect != null) {

            result = this.entityEffect.apply(stack, user, hand, target);
        }

        return result != null ? result : super.interactLivingEntity(stack, user, target, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        InteractionResult result = null;

        if (this.blockEffect != null) {

            result = this.blockEffect.apply(context.getItemInHand(), context.getPlayer(), context.getHand(), context.getClickedPos());
        }

        return result != null ? result : super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        InteractionResultHolder<ItemStack> result = null;

        if (this.userEffect != null) {

            result = this.userEffect.apply(player.getItemInHand(hand), player, hand, player);
        }

        return result != null ? result : super.use(world, player, hand);
    }
}