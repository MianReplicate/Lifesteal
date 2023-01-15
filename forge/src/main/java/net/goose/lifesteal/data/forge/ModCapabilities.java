package net.goose.lifesteal.data.forge;

import net.goose.lifesteal.api.IHealthData;
import net.goose.lifesteal.api.ILevelData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCapabilities {
    public static final Capability<IHealthData> HEART_CAP_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<ILevelData> LEVEL_CAP_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static class EventCapHandler {
        @SubscribeEvent
        public static void attachentityCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                HealthDataImpl.attach(event);
            }
        }

        @SubscribeEvent
        public static void attachlevelCapabilities(final AttachCapabilitiesEvent<Level> event) {
            if (event.getObject() instanceof Level) {
                LevelDataImpl.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(IHealthData.class);
            event.register(ILevelData.class);
        }
    }
}