package net.goose.lifesteal.data.neoforge;

import net.goose.lifesteal.api.IHealthData;
import net.goose.lifesteal.data.HealthData;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {
    public static final EntityCapability<IHealthData, Void> HEALTH_CAP =
            EntityCapability.createVoid(
                    ModResources.modLoc("health_cap"),
                    IHealthData.class
            );
    public static class EventCapHandler {
        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.registerEntity(HEALTH_CAP, EntityType.PLAYER, (entity, context) -> new HealthData(entity));
        }
    }
}