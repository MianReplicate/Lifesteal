package mc.mian.lifesteal.data;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.api.AutoCapLSData;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
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

import javax.annotation.Nullable;
import java.util.HashMap;

public class ForgeLSData extends LSData implements AutoCapLSData {
    public final HashMap<ResourceLocation, Object> dataMap = new HashMap<>();

    public ForgeLSData(LivingEntity entity) {
        super(entity);
        this.dataMap.putIfAbsent(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
        this.dataMap.putIfAbsent(LSConstants.TIME_KILLED, 0L);
    }

    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        class HeartCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

            public static final ResourceLocation IDENTIFIER = LSConstants.modLoc(LSConstants.LIFESTEAL_DATA.getPath());
            private final AutoCapLSData backend = new ForgeLSData((LivingEntity) event.getObject());
            private final LazyOptional<AutoCapLSData> optionalData = LazyOptional.of(() -> backend);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return ForgeLSCapabilities.LIFESTEAL_DATA.orEmpty(cap, this.optionalData);
            }

            @Override
            public CompoundTag serializeNBT(HolderLookup.Provider arg) {
                return this.backend.serializeNBT();
            }

            @Override
            public void deserializeNBT(HolderLookup.Provider arg, CompoundTag nbt) {
                this.backend.deserializeNBT(nbt);
            }
        }

        final HeartCapProvider provider = new HeartCapProvider();

        event.addCapability(HeartCapProvider.IDENTIFIER, provider);
    }
}