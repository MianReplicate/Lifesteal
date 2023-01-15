package net.goose.lifesteal.data;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.goose.lifesteal.api.ILevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class LevelData implements ILevelData {
    private final Level level;

    public LevelData(@Nullable final Level level) {
        this.level = level;
    }

    private final HashMap<UUID, BlockPos> bannedMap = new HashMap<>();

    @ExpectPlatform
    public static Optional<LevelData> get(Level level) {
        throw new AssertionError();
    }

    @Override
    public HashMap getMap() {
        return bannedMap;
    }

    @Override
    public void setUUIDanditsBlockPos(UUID uuid, BlockPos blockPos) {
        if (!bannedMap.containsKey(uuid)) {
            bannedMap.put(uuid, blockPos);
        }
    }

    @Override
    public void removeUUIDanditsBlockPos(UUID uuid, BlockPos blockPos) {
        if (bannedMap.containsKey(uuid)) {
            bannedMap.remove(uuid, blockPos);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        for (UUID uuid : bannedMap.keySet()) {

            CompoundTag playerCompoundTag = new CompoundTag();
            BlockPos blockPos = bannedMap.get(uuid);
            playerCompoundTag.putUUID("Key", uuid);
            playerCompoundTag.put("Value", NbtUtils.writeBlockPos(blockPos));

            listTag.add(playerCompoundTag);
        }

        compoundTag.put("Map", listTag);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        ListTag listTag = (ListTag) tag.get("Map");
        listTag.forEach((tag1) -> {
            CompoundTag compoundTag = (CompoundTag) tag1;
            UUID uuid = compoundTag.getUUID("Key");
            BlockPos blockPos = NbtUtils.readBlockPos(compoundTag.getCompound("Value"));

            setUUIDanditsBlockPos(uuid, blockPos);
        });
    }
}
