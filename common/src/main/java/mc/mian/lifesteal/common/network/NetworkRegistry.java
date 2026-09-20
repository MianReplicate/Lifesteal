package mc.mian.lifesteal.common.network;

import com.mojang.datafixers.util.Pair;
import mc.mian.lifesteal.platform.services.INetworkRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.HashMap;
import java.util.function.Consumer;

public abstract class NetworkRegistry implements INetworkRegistry {
    public HashMap<CustomPacketPayload.Type<?>, Pair<StreamCodec<RegistryFriendlyByteBuf, ?>, Consumer<Context>>> typesToData = new HashMap<>();

    @Override
    public <T extends CustomPacketPayload> void registerPacket(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Consumer<Context> handle) {
        typesToData.put(type, Pair.of(streamCodec, handle));
    }
}
