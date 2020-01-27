package net.darkhax.darkutils.temp;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = DarkUtils.MOD_ID, bus = Bus.FORGE)
public class FakePlayerFactory {
    
    private static Map<GameProfile, FakePlayer> fakePlayers = Maps.newHashMap();
    
    public static FakePlayer get (ServerWorld world, GameProfile username) {
        
        if (!fakePlayers.containsKey(username)) {
            
            final FakePlayer fakePlayer = new DUFakePlayer(world, username);
            fakePlayers.put(username, fakePlayer);
        }
        
        return fakePlayers.get(username);
    }
    
    public static void unloadWorld (ServerWorld world) {
        
        fakePlayers.entrySet().removeIf(entry -> entry.getValue().world == world);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDimensionUnload (WorldEvent.Unload event) {
        
        if (event.getWorld() instanceof ServerWorld) {
            
            unloadWorld((ServerWorld) event.getWorld());
        }
    }
}