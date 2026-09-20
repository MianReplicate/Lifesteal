package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.common.network.Context;
import mc.mian.lifesteal.common.network.NetworkRegistry;
import mc.mian.lifesteal.common.network.Side;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class FabricNetworkRegistry extends NetworkRegistry {
    @Override
    public <T extends CustomPacketPayload> void registerPacket(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Consumer<Context> handle) {
        super.registerPacket(type, streamCodec, handle);

        PayloadTypeRegistry.clientboundPlay().register(type, streamCodec);
        PayloadTypeRegistry.serverboundPlay().register(type, streamCodec);
    }

    public void registerServer(){
        typesToData.forEach((type, pair) -> ServerPlayNetworking.registerGlobalReceiver(type, (_, context) ->
                pair.getSecond().accept(new Context(Side.SERVER, context.player(), context.server()))));
    }

    public void registerClient(){
        typesToData.forEach((type, pair) -> ClientPlayNetworking.registerGlobalReceiver(type, (_, context) ->
                pair.getSecond().accept(new Context(Side.CLIENT, null, null))));
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }
}
