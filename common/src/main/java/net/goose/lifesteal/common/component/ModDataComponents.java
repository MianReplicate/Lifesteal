package net.goose.lifesteal.common.component;

import com.mojang.serialization.Codec;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class ModDataComponents {
    public static final DeferredRegistry<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegistry.create(LifeSteal.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    // Unfresh is given when withdrawing Heart Crystals. This prevents the status effects given from a Heart Crystal to happen.
    public static final RegistrySupplier<DataComponentType<Object>> UNFRESH = DATA_COMPONENTS.register("unfresh", () ->
            DataComponentType.builder().persistent(Codec.unit(false))
                    .build());

    // Ripped is given when a Heart Crystal was created by killing a player. This bypasses the configs that disable eating Heart Crystals.
    public static final RegistrySupplier<DataComponentType<Object>> RIPPED = DATA_COMPONENTS.register("ripped", () ->
            DataComponentType.builder().persistent(Codec.unit(false))
                    .build());

    public static void register() {
        LifeSteal.LOGGER.debug("Registering DataComponents for " + LifeSteal.MOD_ID);
        DATA_COMPONENTS.register();
    }
}