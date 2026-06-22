package mc.mian.lifesteal.event;

import mc.mian.lifesteal.common.command.LSCommands;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.data.NeoForgeLSCapabilities;
import mc.mian.lifesteal.data.NeoForgeLSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class LSEventHandlers {
    @EventBusSubscriber(modid = LSConstants.MOD_ID)
    public static class Common{
        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.registerEntity(NeoForgeLSCapabilities.LIFESTEAL_DATA, EntityTypes.PLAYER, (entity, context) -> new LSData(entity));
        }

        @SubscribeEvent
        public static void OnCommandsRegister(final RegisterCommandsEvent event) {
            LSCommands.registerCommands(command -> command.accept(event.getDispatcher()));
        }

        @SubscribeEvent
        public static void playerSpawnEvent(final PlayerEvent.PlayerRespawnEvent event){
            NeoForgeLSData.get(event.getEntity()).ifPresent(iLifestealData -> iLifestealData.refreshHealth(true));
        }
    }
}
