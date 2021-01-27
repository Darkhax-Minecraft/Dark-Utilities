package net.darkhax.darkutils.temp;

import com.mojang.authlib.GameProfile;

import net.minecraft.potion.EffectInstance;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

public class DUFakePlayer extends FakePlayer {
    
    public DUFakePlayer(ServerWorld world, GameProfile name) {
        
        super(world, name);
        this.abilities.disableDamage = true;
    }
    
    @Override
    public boolean isPotionApplicable (EffectInstance potioneffectIn) {
        
        return false;
    }
}