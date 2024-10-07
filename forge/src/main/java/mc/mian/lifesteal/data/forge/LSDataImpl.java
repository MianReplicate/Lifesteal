package mc.mian.lifesteal.data.forge;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.api.ILSData;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
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

import javax.annotation.Nullable;
import java.util.Optional;

public class LSDataImpl {

    public static Optional<ILSData> get(final Entity entity) {
        return entity.getCapability(ModCapabilities.LIFESTEAL_DATA).resolve();
    }

    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        class HeartCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

            public static final ResourceLocation IDENTIFIER = new ResourceLocation(LifeSteal.MOD_ID, LSConstants.LIFESTEAL_DATA.getPath());
            private final ILSData backend = new LSData((LivingEntity) event.getObject());
            private final LazyOptional<ILSData> optionalData = LazyOptional.of(() -> backend);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return ModCapabilities.LIFESTEAL_DATA.orEmpty(cap, this.optionalData);
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