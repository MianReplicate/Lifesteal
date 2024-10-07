package mc.mian.lifesteal.forge.event;

import mc.mian.lifesteal.command.LSCommands;
import mc.mian.lifesteal.common.blockentity.LSBlockEntityTypes;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.data.forge.LSDataImpl;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class LSEventHandler {
    @Mod.EventBusSubscriber(modid = LSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModClient{
        @SubscribeEvent
        public static void OnRenderersRegister(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(LSBlockEntityTypes.EXPANDED_SKULL.get(), SkullBlockRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = LSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Common{
        @SubscribeEvent
        public static void attachEntityCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                LSDataImpl.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(LSDataImpl.class);
        }

        @SubscribeEvent
        public static void OnCommandsRegister(final RegisterCommandsEvent event) {
            LSCommands.registerCommands(command -> command.accept(event.getDispatcher()));
        }

        @SubscribeEvent
        public static void playerSpawnEvent(final PlayerEvent.PlayerRespawnEvent event){
            LSDataImpl.get(event.getEntity()).ifPresent(iLifestealData -> iLifestealData.refreshHealth(true));
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
    }
}
