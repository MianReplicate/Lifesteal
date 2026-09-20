package mc.mian.lifesteal.platform.services;

import mc.mian.lifesteal.common.network.Context;
import mc.mian.lifesteal.common.network.custom.HeartGainedPayload;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.function.Consumer;

public interface INetworkRegistry {
    <T extends CustomPacketPayload> void registerPacket(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Consumer<Context> handle);
    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);
    void sendToServer(CustomPacketPayload payload);

    default void sendToPlayers(List<ServerPlayer> players, CustomPacketPayload payload){
        for(ServerPlayer player: players){
            sendToPlayer(player, payload);
        }
    }
}
