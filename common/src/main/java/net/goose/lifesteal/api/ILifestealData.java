package net.goose.lifesteal.api;

import net.goose.lifesteal.util.Serializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;


public interface ILifestealData extends Serializable<CompoundTag> {
    void tryRevivalEffects();

    BlockPos spawnPlayerHead();
    boolean dropPlayerHead();
    LivingEntity getLivingEntity();
    int getHealthModifiedTotal(boolean includeHeartDifference);
    int getHPDifferenceRequiredForBan();
    void killPlayerPermanently();
    Collection<ResourceLocation> getKeys();
    <T> T getValue(ResourceLocation key);
    <T> void setValue(ResourceLocation key, T value);
    void refreshHealth(boolean healtoMax);
}


