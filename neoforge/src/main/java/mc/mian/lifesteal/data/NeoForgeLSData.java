package mc.mian.lifesteal.data;

import mc.mian.lifesteal.api.ILSData;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public class NeoForgeLSData {
    public static Optional<ILSData> get(final LivingEntity entity) {
        return Optional.ofNullable(entity.getCapability(NeoForgeLSCapabilities.LIFESTEAL_DATA));
    }
}