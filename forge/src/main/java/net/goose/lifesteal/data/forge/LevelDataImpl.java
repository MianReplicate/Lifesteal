package net.goose.lifesteal.data.forge;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.ILevelData;
import net.goose.lifesteal.data.LevelData;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LevelDataImpl {
    public static Optional<ILevelData> get(final Level level) {
        return level.getCapability(ModCapabilities.LEVEL_CAP_CAPABILITY).resolve();
    }

    public static void attach(final AttachCapabilitiesEvent<Level> event) {
        class LevelCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

            public static final ResourceLocation IDENTIFIER = new ResourceLocation(LifeSteal.MOD_ID, "levelmap");
            private final ILevelData backend = new LevelData(event.getObject());
            private final LazyOptional<ILevelData> optionalData = LazyOptional.of(() -> backend);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return ModCapabilities.LEVEL_CAP_CAPABILITY.orEmpty(cap, this.optionalData);
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

        final LevelCapProvider provider = new LevelCapProvider();

        event.addCapability(LevelCapProvider.IDENTIFIER, provider);
    }
}
