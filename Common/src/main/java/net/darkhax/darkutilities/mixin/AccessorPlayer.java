package net.darkhax.darkutilities.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Player.class)
public interface AccessorPlayer {

    @Accessor("sleepCounter")
    void darkutils$setSleepTimer(int newTimer);

    @Accessor("enchantmentSeed")
    void darkutils$setEnchantmentSeed(int newSeed);
}