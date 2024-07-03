package net.goose.lifesteal.data.forge;

import net.goose.lifesteal.api.ILifestealData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCapabilities {
    public static final Capability<ILifestealData> LIFESTEAL_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static class EventCapHandler {
        @SubscribeEvent
        public static void attachentityCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                LifestealDataImpl.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(LifestealDataImpl.class);
        }
    }
}