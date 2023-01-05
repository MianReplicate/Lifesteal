package net.goose.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;
import java.util.Optional;

public class HealthDataImpl extends HealthData implements ComponentV3 {
    public HealthDataImpl(LivingEntity livingEntity) {
        super(livingEntity);
    }

    public static Optional<HealthData> get(LivingEntity livingEntity) {
        try {
            return Optional.of(ModComponents.HEALTH_DATA.get(livingEntity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<HealthData> get(Entity entity) {
        try {
            return Optional.of(ModComponents.HEALTH_DATA.get(entity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        CompoundTag nbt = this.serializeNBT();
        for (String key : nbt.getAllKeys()) {
            tag.put(key, Objects.requireNonNull(nbt.get(key)));
        }
    }
}