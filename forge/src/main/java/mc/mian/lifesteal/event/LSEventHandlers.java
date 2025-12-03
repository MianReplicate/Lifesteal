package mc.mian.lifesteal.event;

import mc.mian.lifesteal.common.command.LSCommands;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.data.ForgeLSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

public class LSEventHandlers {
    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = LSConstants.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.FORGE)
    public static class Common{
        @SubscribeEvent
        public static void OnCommandsRegister(final RegisterCommandsEvent event) {
            LSCommands.registerCommands(command -> command.accept(event.getDispatcher()));
        }

        @SubscribeEvent
        public static void playerSpawnEvent(final PlayerEvent.PlayerRespawnEvent event){
            ForgeLSData.get(event.getEntity()).ifPresent(iLifestealData -> iLifestealData.refreshHealth(true));
        }

        @SubscribeEvent
        public static void playerCloneEvent(final PlayerEvent.Clone event){
            LivingEntity oldPlayer = event.getOriginal();
            oldPlayer.reviveCaps();
            LivingEntity newPlayer = event.getEntity();

            LSData.get(oldPlayer).ifPresent(oldData -> LSData.get(newPlayer).ifPresent(newData ->
                    {
                        newData.setValue(LSConstants.HEALTH_DIFFERENCE, oldData.getValue(LSConstants.HEALTH_DIFFERENCE));
                        newData.refreshHealth(false);
                    }
            ));

            oldPlayer.invalidateCaps();
        }

        @SubscribeEvent
        public static void attachEntityCapabilities(final AttachCapabilitiesEvent.Entities event) {
            if (event.getObject() instanceof Player) {
                ForgeLSData.attach(event);
            }
        }
    }
}
