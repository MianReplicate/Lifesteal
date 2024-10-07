package mc.mian.lifesteal.data.forge;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.api.ILSData;
import mc.mian.lifesteal.api.ILSDataForge;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class LSDataImpl extends LSData implements ILSDataForge {
    private final HashMap<ResourceLocation, Object> dataMap = new HashMap<>();

    public LSDataImpl(LivingEntity entity) {
        super(entity);
        this.dataMap.putIfAbsent(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
        this.dataMap.putIfAbsent(LSConstants.TIME_KILLED, 0L);
    }

    public static Optional<LSData> get(final Entity entity) {
        return Optional.ofNullable((LSData) entity.getCapability(LSCapabilities.LIFESTEAL_DATA).resolve().orElse(null));
    }

    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        class HeartCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

            public static final ResourceLocation IDENTIFIER = LSUtil.modLoc(LSConstants.LIFESTEAL_DATA.getPath());
            private final ILSDataForge backend = new LSDataImpl((LivingEntity) event.getObject());
            private final LazyOptional<ILSDataForge> optionalData = LazyOptional.of(() -> backend);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return LSCapabilities.LIFESTEAL_DATA.orEmpty(cap, this.optionalData);
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

    public static Collection<ResourceLocation> getKeys(LSData lifestealData){
        return ((LSDataImpl) lifestealData).dataMap.keySet();
    }

    public static <T> T getValue(LSData lifestealData, ResourceLocation key) {
        return (T) ((LSDataImpl) lifestealData).dataMap.get(key);
    }

    public static <T> void setValue(LSData lifestealData, ResourceLocation key, T value) {
        ((LSDataImpl) lifestealData).dataMap.put(key, value);
    }
}