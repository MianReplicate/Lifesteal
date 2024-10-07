package mc.mian.lifesteal.util.fabric;

import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.nbt.CompoundTag;

import java.util.function.BiFunction;

public class LSUtilImpl {
    public static CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function){
        function.apply((CompoundTag) ((CompoundTag)tag.get("cardinal_components")).get(LSConstants.LIFESTEAL_DATA.toString()), key);
        return tag;
    }

    public static <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function){
        return function.apply((CompoundTag) ((CompoundTag)tag.get("cardinal_components")).get(LSConstants.LIFESTEAL_DATA.toString()), key);
    }
}
