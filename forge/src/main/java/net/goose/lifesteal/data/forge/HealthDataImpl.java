package net.goose.lifesteal.data.forge;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.IHealthData;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HealthDataImpl {

    public static Optional<IHealthData> get(final LivingEntity entity) {
        return entity.getCapability(ModCapabilities.HEART_CAP_CAPABILITY).resolve();
    }

    public static Optional<IHealthData> get(final Entity entity) {
        return entity.getCapability(ModCapabilities.HEART_CAP_CAPABILITY).resolve();
    }

    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        class HeartCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

            public static final ResourceLocation IDENTIFIER = new ResourceLocation(LifeSteal.MOD_ID, "healthdifference");
            private final IHealthData backend = new HealthData((LivingEntity) event.getObject());
            private final LazyOptional<IHealthData> optionalData = LazyOptional.of(() -> backend);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return ModCapabilities.HEART_CAP_CAPABILITY.orEmpty(cap, this.optionalData);
            }

            @Override
            public CompoundTag serializeNBT() {
                return this.backend.serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundTag nbt) {
                this.backend.deserializeNBT(nbt);
            }
        }

        final HeartCapProvider provider = new HeartCapProvider();

        event.addCapability(HeartCapProvider.IDENTIFIER, provider);
    }
}