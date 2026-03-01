package mc.mian.lifesteal.platform.services;

import mc.mian.lifesteal.common.data.LSData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

public interface IDataHelper {
    CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function);
    <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function);
    Collection<Identifier> getKeys(LSData lifestealData);
    <T> T getValue(LSData lifestealData, Identifier key);
    <T> void setValue(LSData lifestealData, Identifier key, T value);
    Optional<LSData> get(LivingEntity entity);
}