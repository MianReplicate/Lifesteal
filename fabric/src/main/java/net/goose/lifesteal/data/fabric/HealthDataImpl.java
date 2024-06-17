package net.goose.lifesteal.data.fabric;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentV3;

import java.util.Objects;
import java.util.Optional;

public class HealthDataImpl extends HealthData implements ComponentV3 {
    private int healthDifference = LifeSteal.config.startingHealthDifference.get();
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

    public static int getHealthDifference(HealthData healthData) {
        return ((HealthDataImpl) healthData).healthDifference;
    }

    public static void setHealthDifference(HealthData healthData, int health) {
        ((HealthDataImpl) healthData).healthDifference = health;
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        this.deserializeNBT(tag);
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        CompoundTag nbt = this.serializeNBT();
        for (String key : nbt.getAllKeys()) {
            tag.put(key, Objects.requireNonNull(nbt.get(key)));
        }
    }
}