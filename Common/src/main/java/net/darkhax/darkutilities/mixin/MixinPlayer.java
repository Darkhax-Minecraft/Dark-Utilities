package net.darkhax.darkutilities.mixin;

import net.darkhax.darkutilities.DarkUtilsCommon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends Entity {

    public MixinPlayer(EntityType<?> type, Level level) {

        // No Op
        super(type, level);
    }

    @Inject(method = "getPortalWaitTime()I", at = @At("HEAD"), cancellable = true)
    public void getPortalWaitTime(CallbackInfoReturnable<Integer> callback) {

        if (this.isInsidePortal && DarkUtilsCommon.getInstance().content.portalCharm.doesEntityHave(this)) {

            callback.setReturnValue(1);
        }
    }
}
