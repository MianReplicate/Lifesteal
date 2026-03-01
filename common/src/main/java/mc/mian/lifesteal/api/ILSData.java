package mc.mian.lifesteal.api;

import mc.mian.lifesteal.util.Serializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

public interface ILSData extends Serializable<CompoundTag> {
    void tryRevivalEffects();

    BlockPos spawnPlayerHead();
    boolean dropPlayerHead();
    LivingEntity getLivingEntity();
    double getAmountOfModifiedHealth(boolean includeHeartDifference);
    double getHPDifferenceRequiredForBan();
    void tick();
    Collection<Identifier> getKeys();
    <T> T getValue(Identifier key);
    <T> void setValue(Identifier key, T value);
    void refreshHealth(boolean healtoMax);
}


