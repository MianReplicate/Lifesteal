package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.LifestealNeoForge;
import mc.mian.lifesteal.common.network.Context;
import mc.mian.lifesteal.common.network.NetworkRegistry;
import mc.mian.lifesteal.common.network.Side;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Consumer;

public class NeoForgeNetworkRegistry extends NetworkRegistry {
    @Override
    public <T extends CustomPacketPayload> void registerPacket(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Consumer<Context> handle) {
        super.registerPacket(type, streamCodec, handle);
    }
    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }

    public static void registerPayload(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(LSConstants.MOD_ID);
        ((NeoForgeNetworkRegistry)Services.NETWORK_REGISTRY).typesToData.forEach((type, pair) ->
                registrar.optional().playBidirectional((CustomPacketPayload.Type)type, (StreamCodec<? super RegistryFriendlyByteBuf, ? extends CustomPacketPayload>) pair.getFirst(),
                        (_, context) -> pair.getSecond().accept(new Context(Side.SERVER, (ServerPlayer) context.player(), Services.PLATFORM.getServer())),
                        (_, context) -> pair.getSecond().accept(new Context(Side.CLIENT, null, null))));
    }
}
