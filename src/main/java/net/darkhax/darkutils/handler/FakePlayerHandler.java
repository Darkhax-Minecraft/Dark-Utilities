package net.darkhax.darkutils.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.darkhax.darkutils.DarkUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = "darkutils")
public class FakePlayerHandler {

    /**
     * The username for the dark utils fake player.
     */
    public static final String USERNAME = "FAKE_PLAYER_DARK_UTILS";

    /**
     * The game profile for the dark utils fake player.
     */
    public static GameProfile PROFILE = new GameProfile(UUID.nameUUIDFromBytes(USERNAME.getBytes()), USERNAME);

    /**
     * A map of all the fake players for all the worlds.
     */
    private static Map<WorldServer, EntityPlayer> FAKE_PLAYERS = new HashMap<>();

    /**
     * Gets a fake player for a world. If one doesn't exist, it is created.
     *
     * @param world The world to get the player for.
     * @return The fake player for that world.
     */
    public static EntityPlayer getFakePlayer (WorldServer world) {

        return FAKE_PLAYERS.computeIfAbsent(world, key -> {
            return new FakePlayerDU(key);
        });
    }

    /**
     * Attacks an entity using the fake player as a damage source.
     *
     * @param target The entity to damage.
     * @param amount The amount of damage to do.
     */
    public static void causePlayerDamage (EntityLivingBase target, float amount) {

        try {

            if (target.getEntityWorld() instanceof WorldServer) {

                target.attackEntityFrom(DamageSource.causePlayerDamage(getFakePlayer((WorldServer) target.getEntityWorld())), amount);
            }
        }

        catch (final Exception e) {

            DarkUtils.LOGGER.warn("There was an error trying to damage " + target);
            DarkUtils.LOGGER.catching(e);
        }
    }

    @SubscribeEvent
    public static void onWorldUnload (WorldEvent.Unload event) {

        // Stop tracking a fake player in a world when that world is unloaded.
        if (event.getWorld() instanceof WorldServer) {

            FAKE_PLAYERS.remove(event.getWorld());
        }
    }

    private static class FakePlayerDU extends FakePlayer {

        public FakePlayerDU (WorldServer world) {

            super(world, PROFILE);
        }
    }
}