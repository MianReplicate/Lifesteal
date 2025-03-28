package mc.mian.lifesteal.registry;

import mc.mian.lifesteal.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class DeferredRegistry<T> {

    public abstract void register();

    public abstract <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier);

    public abstract Collection<RegistrySupplier<T>> getEntries();

    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return Services.REGISTRY_CREATOR.create(modid, resourceKey);
    }

}