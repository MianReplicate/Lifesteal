package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.platform.services.IRegistryCreator;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class FabricRegistryCreator implements IRegistryCreator {
    public <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static class Impl<T> extends DeferredRegistry<T> {

        private final String modid;
        private final Registry<T> registry;
        private final ResourceKey<? extends Registry<T>> registryKey;
        private final List<RegistrySupplier<T>> entries;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.modid = modid;
            this.registryKey = resourceKey;
            this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(resourceKey.identifier()).orElseThrow(() -> new NullPointerException("Registry " + resourceKey + " not found!")).value();
            this.entries = new ArrayList<>();
        }

        @Override
        public void register() {
            this.registry.registryLifecycle();
        }

        @Override
        public <R> RegistrySupplier<R> register(String id, Supplier<T> supplier) {
            Identifier registeredId = Identifier.fromNamespaceAndPath(this.modid, id);
            RegistrySupplier<T> registrySupplier = new RegistrySupplier<>(registeredId, ResourceKey.create(registryKey, registeredId), Registry.register(this.registry, registeredId, supplier.get()));
            this.entries.add(registrySupplier);
            return (RegistrySupplier<R>) registrySupplier;
        }

        @Override
        public Collection<RegistrySupplier<T>> getEntries() {
            return this.entries;
        }
    }
}
