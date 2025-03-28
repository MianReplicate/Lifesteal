package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.data.ForgeLSData;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.data.ForgeLSCapabilities;
import mc.mian.lifesteal.platform.services.IDataHelper;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

public class ForgeDataHelper implements IDataHelper {
    public Optional<LSData> get(final LivingEntity entity) {
        return Optional.ofNullable((LSData) entity.getCapability(ForgeLSCapabilities.LIFESTEAL_DATA).resolve().orElse(null));
    }

    public Collection<ResourceLocation> getKeys(LSData lifestealData){
        return ((ForgeLSData) lifestealData).dataMap.keySet();
    }

    public <T> T getValue(LSData lifestealData, ResourceLocation key) {
        return (T) ((ForgeLSData) lifestealData).dataMap.get(key);
    }

    public <T> void setValue(LSData lifestealData, ResourceLocation key, T value) {
        ((ForgeLSData) lifestealData).dataMap.put(key, value);
    }

    public CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function){
        function.apply((CompoundTag) ((CompoundTag)tag.get("ForgeCaps")).get(LSConstants.LIFESTEAL_DATA.toString()), key);
        return tag;
    }

    public <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function){
        return function.apply((CompoundTag) ((CompoundTag)tag.get("ForgeCaps")).get(LSConstants.LIFESTEAL_DATA.toString()),  key);
    }
}
