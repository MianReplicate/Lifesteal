package net.goose.lifesteal.data.forge;

import net.goose.lifesteal.api.ILifestealData;
import net.goose.lifesteal.data.LifestealData;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {
    public static final EntityCapability<ILifestealData, Void> LIFESTEAL_DATA =
            EntityCapability.createVoid(
                    ModResources.LIFESTEAL_DATA,
                    ILifestealData.class
            );
    public static class EventCapHandler {
        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.registerEntity(LIFESTEAL_DATA, EntityType.PLAYER, (entity, context) -> new LifestealData(entity));
        }
    }
}