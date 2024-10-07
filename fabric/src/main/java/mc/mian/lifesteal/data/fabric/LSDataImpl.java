package mc.mian.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import mc.mian.lifesteal.data.LSData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class LSDataImpl extends LSData implements ComponentV3 {
    public LSDataImpl(LivingEntity livingEntity) {
        super(livingEntity);
    }

    public static Optional<LSData> get(Entity entity) {
        try {
            return Optional.of(LSComponents.LIFESTEAL_DATA.get(entity));
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