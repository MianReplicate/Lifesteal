package net.goose.lifesteal.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.UUID;

public interface ILevelData {
    HashMap<ResourceKey<Level>, HashMap<UUID, BlockPos>> getMap();

    void addTeleportTo(Level level, UUID uuid, BlockPos blockPos);

    void removeTeleport(Level level, UUID uuid, BlockPos blockPos);
}
