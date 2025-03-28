package mc.mian.lifesteal.platform.services;

import mc.mian.lifesteal.registry.DeferredRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface IRegistryCreator {
    <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey);
}
