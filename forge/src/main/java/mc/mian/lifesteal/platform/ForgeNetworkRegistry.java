package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.common.network.Context;
import mc.mian.lifesteal.common.network.NetworkRegistry;
import mc.mian.lifesteal.common.network.Side;
import mc.mian.lifesteal.platform.services.INetworkRegistry;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.payload.PayloadFlow;
import net.minecraftforge.network.payload.PayloadProtocol;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ForgeNetworkRegistry extends NetworkRegistry {
    private static final int PROTOCOL_VERSION = 0;
    public static Channel<CustomPacketPayload> CHANNEL;
    public static PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> CHANNEL_BUILDER = ChannelBuilder
            .named(Identifier.fromNamespaceAndPath(LSConstants.MOD_ID, "main"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .optional()
            .payloadChannel()
            .play()
            .bidirectional();
    public void build() {
        CHANNEL = CHANNEL_BUILDER.build();
    }
    @Override
    public <T extends CustomPacketPayload> void registerPacket(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Consumer<Context> handle) {
        super.registerPacket(type, streamCodec, handle);

        CHANNEL_BUILDER = CHANNEL_BUILDER.add(type, streamCodec, (_, context) ->
                handle.accept(new Context(context.isClientSide() ? Side.CLIENT : Side.SERVER, context.getSender(), context.isServerSide() ? Services.PLATFORM.getServer() : null)));
    }
    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        CHANNEL.send(payload, PacketDistributor.PLAYER.with(player));
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        CHANNEL.send(payload, PacketDistributor.SERVER.noArg());
    }
}
