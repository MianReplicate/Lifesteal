package net.goose.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.goose.lifesteal.data.LevelData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Optional;

public class LevelDataImpl extends LevelData implements ComponentV3 {
    public LevelDataImpl(Level level) {
        super(level);
    }

    public static Optional<LevelData> get(Level level) {
        try {
            return Optional.of(ModComponents.LEVEL_DATA.get(level));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        CompoundTag nbt = this.serializeNBT();
        for (String key : nbt.getAllKeys()) {
            tag.put(key, Objects.requireNonNull(nbt.get(key)));
        }
    }
}
