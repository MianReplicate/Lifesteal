package mc.mian.lifesteal.common.component;

import com.mojang.serialization.Codec;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

import java.util.function.UnaryOperator;

public class LSDataComponents {
    public static final DeferredRegistry<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegistry.create(LSConstants.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    // Unfresh is given when withdrawing Heart Crystals. This prevents the status effects given from a Heart Crystal to happen.
    public static final RegistrySupplier<DataComponentType<Boolean>> UNFRESH = register("unfresh",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    // Ripped is given when a Heart Crystal was created by killing a player. This bypasses the configs that disable eating Heart Crystals.
    public static final RegistrySupplier<DataComponentType<Boolean>> RIPPED = register("ripped",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    private static <T> RegistrySupplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder){
        return DATA_COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }

    public static void register() {
        LSConstants.LOGGER.debug("Registering DataComponents for " + LSConstants.MOD_ID);
        DATA_COMPONENTS.register();
    }
}