package mc.mian.lifesteal.platform.services;

import mc.mian.lifesteal.data.LSData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

public interface IDataHelper {
    CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function);
    <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function);
    Collection<ResourceLocation> getKeys(LSData lifestealData);
    <T> T getValue(LSData lifestealData, ResourceLocation key);
    <T> void setValue(LSData lifestealData, ResourceLocation key, T value);
    Optional<LSData> get(LivingEntity entity);
}