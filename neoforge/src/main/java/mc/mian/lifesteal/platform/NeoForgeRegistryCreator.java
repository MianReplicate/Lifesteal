package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.LifestealNeoForge;
import mc.mian.lifesteal.platform.services.IRegistryCreator;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class NeoForgeRegistryCreator implements IRegistryCreator {

    public <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    @SuppressWarnings("unchecked")
    public static class Impl<T> extends DeferredRegistry<T> {

        private final DeferredRegister<T> register;
        private final List<RegistrySupplier<T>> entries;
        private final ResourceKey<? extends Registry<T>> registryKey;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.register = DeferredRegister.create(resourceKey, modid);
            this.entries = new ArrayList<>();
            this.registryKey = resourceKey;
        }

        @Override
        public void register() {
            this.register.register(LifestealNeoForge.modEventBus);
        }


        @Override
        public <R> RegistrySupplier<R> register(String id, Supplier<T> supplier) {
            var orig = this.register.register(id, supplier);
            var registrySupplier = new RegistrySupplier<>(orig.getId(), ResourceKey.create(registryKey, orig.getId()), orig);
            this.entries.add(registrySupplier);
            return (RegistrySupplier<R>) registrySupplier;
        }

        @Override
        public Collection<RegistrySupplier<T>> getEntries() {
            return this.entries;
        }
    }


}