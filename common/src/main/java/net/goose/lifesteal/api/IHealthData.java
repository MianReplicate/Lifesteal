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
    double getHeartModifiedTotal(boolean includeHeartDifference);
    double getHPDifferenceRequiredForBan();
    void banForDeath();
    int getHealthDifference();

    void setHealthDifference(int hearts);

    void refreshHearts(boolean healtoMax);
}


