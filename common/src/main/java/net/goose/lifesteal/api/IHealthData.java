package net.goose.lifesteal.api;

import net.goose.lifesteal.util.Serializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;


public interface IHealthData extends Serializable<CompoundTag> {
    void revivedTeleport(ServerLevel level, ILevelData iLevelData, boolean synchronize);

    void revivedTeleport(ServerLevel level, ILevelData iLevelData);

    BlockPos spawnPlayerHead(ServerPlayer serverPlayer);
    boolean dropPlayerHead(ServerPlayer serverPlayer);
    LivingEntity getLivingEntity();

    int getHeartDifference();

    void setHeartDifference(int hearts);

    void refreshHearts(boolean healtoMax);
}


