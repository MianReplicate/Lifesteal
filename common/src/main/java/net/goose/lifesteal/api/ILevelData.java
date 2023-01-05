package net.goose.lifesteal.api;

import net.goose.lifesteal.util.Serializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.UUID;

public interface ILevelData extends Serializable<CompoundTag> {
    HashMap getMap();

    void setUUIDanditsBlockPos(UUID uuid, BlockPos blockPos);

    void removeUUIDanditsBlockPos(UUID uuid, BlockPos blockPos);
}
