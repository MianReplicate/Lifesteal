package net.goose.lifesteal.forge.event;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.client.render.ReviveHeadBER;
import net.goose.lifesteal.command.ModCommands;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        LivingEntity newPlayer = event.getEntity();

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
