package net.goose.lifesteal.common.component;

import com.mojang.serialization.Codec;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;

public class ModDataComponents {
    public static final DeferredRegistry<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegistry.create(LifeSteal.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<Object>> UNFRESH = DATA_COMPONENTS.register("unfresh", () ->
            DataComponentType.builder().persistent(Codec.unit(false))
                    .networkSynchronized(StreamCodec.unit(false)).build());

    public static final RegistrySupplier<DataComponentType<Object>> DROPPED = DATA_COMPONENTS.register("dropped", () ->
            DataComponentType.builder().persistent(Codec.unit(false))
                    .networkSynchronized(StreamCodec.unit(false)).build());

    public static void register() {
        LifeSteal.LOGGER.debug("Registering DataComponents for " + LifeSteal.MOD_ID);
        DATA_COMPONENTS.register();
    }
}