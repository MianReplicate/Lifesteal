package net.goose.lifesteal.data.fabric;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ModConfig;
import net.goose.lifesteal.data.LifestealData;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentV3;

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

    public static Collection<ResourceLocation> getKeys(LifestealData lifestealData){
        return ((LifestealDataImpl) lifestealData).dataMap.keySet();
    }

    public static <T> T getValue(LifestealData lifestealData, ResourceLocation key) {
        return (T) ((LifestealDataImpl) lifestealData).dataMap.get(key);
    }

    public static <T> void setValue(LifestealData lifestealData, ResourceLocation key, T value) {
        ((LifestealDataImpl) lifestealData).dataMap.put(key, value);
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