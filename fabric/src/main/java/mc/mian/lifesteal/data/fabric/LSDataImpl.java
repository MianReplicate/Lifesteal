package mc.mian.lifesteal.data.fabric;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentV3;

import java.util.*;

public class LSDataImpl extends LSData implements ComponentV3 {
    private final HashMap<ResourceLocation, Object> dataMap = new HashMap<>();
    public LSDataImpl(LivingEntity livingEntity) {
        super(livingEntity);
        this.dataMap.putIfAbsent(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
        this.dataMap.putIfAbsent(LSConstants.TIME_KILLED, 0L);
    }

    public static Optional<LSData> get(Entity entity) {
        try {
            return Optional.of(LSComponents.LIFESTEAL_DATA.get(entity));
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

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        this.deserializeNBT(tag);
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        CompoundTag nbt = this.serializeNBT();
        for (String key : nbt.getAllKeys()) {
            tag.put(key, Objects.requireNonNull(nbt.get(key)));
        }
    }
}