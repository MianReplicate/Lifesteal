package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.api.ILSRetrieve;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.data.FabricLSData;
import mc.mian.lifesteal.platform.services.IDataHelper;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

public class FabricDataHelper implements IDataHelper {
    public Optional<LSData> get(LivingEntity entity) {
        try {
            return Optional.of(((ILSRetrieve)entity).lifesteal_1_21$getData());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Collection<Identifier> getKeys(LSData lifestealData){
        return ((FabricLSData) lifestealData).dataMap.keySet();
    }

    public <T> T getValue(LSData lifestealData, Identifier key) {
        return (T) ((FabricLSData) lifestealData).dataMap.get(key);
    }

    public <T> void setValue(LSData lifestealData, Identifier key, T value) {
        ((FabricLSData) lifestealData).dataMap.put(key, value);
    }

    public CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function){
        function.apply((CompoundTag) tag.get(LSConstants.LIFESTEAL_DATA.getPath()), key);
        return tag;
    }

    public <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function){
        return function.apply((CompoundTag) tag.get(LSConstants.LIFESTEAL_DATA.getPath()), key);
    }
}
