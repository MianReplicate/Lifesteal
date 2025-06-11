package mc.mian.lifesteal.data;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class FabricLSData extends LSData {
    public final HashMap<ResourceLocation, Object> dataMap = new HashMap<>();
    public FabricLSData(LivingEntity livingEntity) {
        super(livingEntity);
        this.dataMap.putIfAbsent(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
        this.dataMap.putIfAbsent(LSConstants.TIME_KILLED, 0L);
    }

    public void writeToNbt(CompoundTag compoundTag) {
        CompoundTag nbt = this.serializeNBT();
        compoundTag.put(LSConstants.LIFESTEAL_DATA.getPath(), nbt);
    }
}