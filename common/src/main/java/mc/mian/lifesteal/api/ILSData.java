package mc.mian.lifesteal.api;

import mc.mian.lifesteal.util.Serializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Collection;


public interface ILSData extends Serializable<CompoundTag> {
    void tryRevivalEffects();

    BlockPos spawnPlayerHead();
    boolean dropPlayerHead();
    LivingEntity getLivingEntity();
    double getAmountOfModifiedHealth(boolean includeHeartDifference);
    double getHPDifferenceRequiredForBan();
    void tick();
    Collection<ResourceLocation> getKeys();
    <T> T getValue(ResourceLocation key);
    <T> void setValue(ResourceLocation key, T value);
    void refreshHealth(boolean healtoMax);
    void addOrUpdateHealthModifier();
}


