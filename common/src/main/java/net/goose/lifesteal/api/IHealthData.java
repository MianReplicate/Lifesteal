package net.goose.lifesteal.api;

import net.goose.lifesteal.util.Serializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;


public interface IHealthData extends Serializable<CompoundTag> {
    void tryRevivalEffects();

    BlockPos spawnPlayerHead();
    boolean dropPlayerHead();
    LivingEntity getLivingEntity();
    double getHealthModifiedTotal(boolean includeHeartDifference);
    double getHPDifferenceRequiredForBan();
    void removePlayer();
    int getHealthDifference();

    void setHealthDifference(int hearts);

    void refreshHealth(boolean healtoMax);
}


