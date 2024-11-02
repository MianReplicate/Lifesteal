package mc.mian.lifesteal.data.fabric;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.api.ILSRetrieve;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class LSDataImpl extends LSData {
    private final HashMap<ResourceLocation, Object> dataMap = new HashMap<>();
    public LSDataImpl(LivingEntity livingEntity) {
        super(livingEntity);
        this.dataMap.putIfAbsent(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
        this.dataMap.putIfAbsent(LSConstants.TIME_KILLED, 0L);
    }

    public static Optional<LSData> get(LivingEntity entity) {
        try {
            return Optional.of(((ILSRetrieve)entity).lifesteal_1_21$getData());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Collection<ResourceLocation> getKeys(LSData lifestealData){
        return ((LSDataImpl) lifestealData).dataMap.keySet();
    }

    public static <T> T getValue(LSData lifestealData, ResourceLocation key) {
        return (T) ((LSDataImpl) lifestealData).dataMap.get(key);
    }

    public static <T> void setValue(LSData lifestealData, ResourceLocation key, T value) {
        ((LSDataImpl) lifestealData).dataMap.put(key, value);
    }

    public void writeToNbt(CompoundTag compoundTag) {
        CompoundTag nbt = this.serializeNBT();
        compoundTag.put(LSConstants.LIFESTEAL_DATA.getPath(), nbt);
    }
}