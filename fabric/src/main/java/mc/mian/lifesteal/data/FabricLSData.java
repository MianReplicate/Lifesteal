package mc.mian.lifesteal.data;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.*;

public class FabricLSData extends LSData {
    public final HashMap<Identifier, Object> dataMap = new HashMap<>();
    public FabricLSData(LivingEntity livingEntity) {
        super(livingEntity);
        this.dataMap.putIfAbsent(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
        this.dataMap.putIfAbsent(LSConstants.TIME_KILLED, 0L);
    }
}