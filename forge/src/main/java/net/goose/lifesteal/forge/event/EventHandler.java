package net.goose.lifesteal.forge.event;

import net.goose.lifesteal.command.ModCommands;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("unused")
public class EventHandler {
    @SubscribeEvent
    public static void OnCommandsRegister(RegisterCommandsEvent event) {
        ModCommands.registerCommands(command -> command.accept(event.getDispatcher()));
    }

    @SubscribeEvent
    public static void playerCloneEvent(final PlayerEvent.Clone event) {

        boolean wasDeath = event.isWasDeath();

        LivingEntity oldPlayer = event.getOriginal();
        oldPlayer.reviveCaps();
        LivingEntity newPlayer = (LivingEntity) event.getEntity();

        HealthData.get(oldPlayer).ifPresent(oldHeartDifference -> HealthData.get(newPlayer).ifPresent(IHeartCap ->
                {
                    IHeartCap.setHeartDifference(oldHeartDifference.getHeartDifference());
                    IHeartCap.refreshHearts(false);
                }
        ));

        if (wasDeath) {
            newPlayer.heal(newPlayer.getMaxHealth());
        }

        oldPlayer.invalidateCaps();
    }
}
