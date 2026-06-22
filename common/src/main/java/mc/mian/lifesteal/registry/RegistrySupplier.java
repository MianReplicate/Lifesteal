package mc.mian.lifesteal.registry;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class RegistrySupplier<T> implements Supplier<T> {

    private final Identifier id;
    private Supplier<T> supplier;
    private T raw;
    private ResourceKey<T> key;

    public RegistrySupplier(Identifier id, ResourceKey<T> key){
        this.id = id;
        this.key = key;
    }

    public RegistrySupplier(Identifier id, ResourceKey<T> key, Supplier<T> supplier) {
        this(id, key);
        this.supplier = supplier;
    }

    public RegistrySupplier(Identifier id, ResourceKey<T> key, T raw) {
        this(id, key);
        this.raw = raw;
    }

    public Identifier getId() {
        return this.id;
    }

    public ResourceKey<T> getKey(){
        return key;
    }

    @Override
    public T get() {
        return this.raw != null ? this.raw : this.supplier.get();
    }
}