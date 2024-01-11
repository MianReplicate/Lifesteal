package net.goose.lifesteal.data;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.ILevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * LevelData should always be saved on the Overworld for persisting data
 */

public class LevelData extends SavedData implements ILevelData {
    private final HashMap<ResourceKey<Level>, HashMap<UUID, BlockPos>> levelMap = new HashMap<>();

    @Override
    public HashMap getMap() {
        return this.levelMap;
    }

    @Override
    public void addTeleportTo(Level level, UUID uuid, BlockPos blockPos) {
        ResourceKey<Level> levelResourceKey = level.dimension();
        if (!levelMap.containsKey(levelResourceKey))
            levelMap.put(levelResourceKey, new HashMap<>());
        HashMap<UUID, BlockPos> bannedMap = levelMap.get(levelResourceKey);

        if (!bannedMap.containsKey(uuid))
            bannedMap.put(uuid, blockPos);
        // idk if map in other map is updated but for now lets assume it is
    }

    @Override
    public void removeTeleport(Level level, UUID uuid, BlockPos blockPos) {
        ResourceKey<Level> levelResourceKey = level.dimension();
        if (levelMap.containsKey(levelResourceKey))
            levelMap.get(levelResourceKey).remove(uuid);
    }

    private static Factory<?> factory(MinecraftServer server){
        return new Factory<>(
                LevelData::new,
                (tag) -> LevelData.deserializeNBT(server, tag),
                null);
    }

    public static LevelData getServerState(MinecraftServer server) {

        DimensionDataStorage dimensionDataStorage = server.getLevel(Level.OVERWORLD).getDataStorage();
        LevelData state = (LevelData) dimensionDataStorage.computeIfAbsent(factory(server), LifeSteal.MOD_ID);

        state.setDirty();
        return state;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        for (ResourceKey<Level> levelResourceKey : levelMap.keySet()) {
            CompoundTag levelCompoundTag = new CompoundTag();

            for (UUID uuid : levelMap.get(levelResourceKey).keySet()){
                BlockPos blockPos = levelMap.get(levelResourceKey).get(uuid);
                levelCompoundTag.put(uuid.toString(), NbtUtils.writeBlockPos(blockPos));
            }
            compoundTag.put(levelResourceKey.location().toString(), levelCompoundTag);
        }

        return compoundTag;
    }

    public static LevelData deserializeNBT(MinecraftServer server, CompoundTag tag) {
        LevelData levelData = new LevelData();

        Set<String> levelKeys = tag.getAllKeys();

        levelKeys.forEach((levelKey) -> {
            Registry<Level> levelRegistry = server.overworld().registryAccess().registryOrThrow(Registries.DIMENSION);
            ResourceKey<Level> resourceKey = levelRegistry.get(new ResourceLocation(levelKey)).dimension();
            HashMap<UUID, BlockPos> hashMap = new HashMap<>();

            CompoundTag levelCompoundTag = tag.getCompound(levelKey);
            Set<String> uuidKeys = levelCompoundTag.getAllKeys();
            uuidKeys.forEach((uuidKey) ->
                hashMap.put(UUID.fromString(uuidKey), NbtUtils.readBlockPos(levelCompoundTag.getCompound(uuidKey)))
            );
            levelData.levelMap.put(resourceKey, hashMap);
        });
        return levelData;
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
