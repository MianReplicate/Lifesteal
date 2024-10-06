package mc.mian.lifesteal.neoforge.event;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.command.LSCommands;
import mc.mian.lifesteal.common.blockentity.LSBlockEntityTypes;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.data.neoforge.LSCapabilities;
import mc.mian.lifesteal.data.neoforge.LSDataImpl;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class LSEventHandlers {
    @EventBusSubscriber(modid = LSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModClient{
        @SubscribeEvent
        public static void OnRenderersRegister(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(LSBlockEntityTypes.EXPANDED_SKULL.get(), SkullBlockRenderer::new);
        }
    }

    @EventBusSubscriber(modid = LSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class Mod{
        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.registerEntity(LSCapabilities.LIFESTEAL_DATA, EntityType.PLAYER, (entity, context) -> new LSData(entity));
        }
    }

    @EventBusSubscriber(modid = LSConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class Common{
        @SubscribeEvent
        public static void OnCommandsRegister(final RegisterCommandsEvent event) {
            LSCommands.registerCommands(command -> command.accept(event.getDispatcher()));
        }

        @SubscribeEvent
        public static void playerSpawnEvent(final PlayerEvent.PlayerRespawnEvent event){
            LSDataImpl.get(event.getEntity()).ifPresent(iLifestealData -> iLifestealData.refreshHealth(true));
        }
    }
}
