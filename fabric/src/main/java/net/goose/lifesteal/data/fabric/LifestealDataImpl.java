package net.goose.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.LifestealData;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class LifestealDataImpl extends LifestealData implements ComponentV3 {
    private final HashMap<ResourceLocation, Object> dataMap = new HashMap<>();
    public LifestealDataImpl(LivingEntity livingEntity) {
        super(livingEntity);
    }

    public static Optional<LifestealData> get(Entity entity) {
        try {
            LifestealDataImpl lifestealData = ModComponents.LIFESTEAL_DATA.get(entity);
            lifestealData.dataMap.putIfAbsent(ModResources.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
            lifestealData.dataMap.putIfAbsent(ModResources.TIME_KILLED, 0L);

            return Optional.of(lifestealData);
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