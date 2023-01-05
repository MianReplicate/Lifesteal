package net.goose.lifesteal.api;

import net.goose.lifesteal.util.Serializable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;


public interface IHealthData extends Serializable<CompoundTag> {

    void revivedTeleport(ServerLevel level, ILevelData iLevelData, boolean synchronize);

    void revivedTeleport(ServerLevel level, ILevelData iLevelData);

    void spawnPlayerHead(ServerPlayer serverPlayer);

    int getHeartDifference();

    void setHeartDifference(int hearts);

    void refreshHearts(boolean healtoMax);
}


