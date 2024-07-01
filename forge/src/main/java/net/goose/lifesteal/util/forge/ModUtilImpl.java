package net.goose.lifesteal.util.forge;

import net.minecraft.nbt.CompoundTag;

import java.util.function.BiFunction;

public class ModUtilImpl {
    public static CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function){
        function.apply((CompoundTag) tag.get("neoforge:attachments"), "lifesteal:" + key);
        return tag;
    }

    public static <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function){
        return function.apply((CompoundTag) tag.get("neoforge:attachments"), "lifesteal:" + key);
    }
}
