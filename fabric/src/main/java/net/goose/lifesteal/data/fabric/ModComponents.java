package net.goose.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.goose.lifesteal.util.ModResources;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<LifestealDataImpl> LIFESTEAL_DATA =
            ComponentRegistryV3.INSTANCE.getOrCreate(ModResources.LIFESTEAL_DATA, LifestealDataImpl.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // Add the component to every PlayerEntity instance
        registry.registerForPlayers(LIFESTEAL_DATA, LifestealDataImpl::new, RespawnCopyStrategy.NEVER_COPY);
    }
}