package mc.mian.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<LSDataImpl> LIFESTEAL_DATA =
            ComponentRegistryV3.INSTANCE.getOrCreate(LSConstants.LIFESTEAL_DATA, LSDataImpl.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // Add the component to every PlayerEntity instance
        registry.registerForPlayers(LIFESTEAL_DATA, LSDataImpl::new, RespawnCopyStrategy.NEVER_COPY);
    }
}