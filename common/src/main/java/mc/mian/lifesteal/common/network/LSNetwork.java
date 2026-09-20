package mc.mian.lifesteal.common.network;

import mc.mian.lifesteal.common.network.custom.HeartGainedPayload;
import mc.mian.lifesteal.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;
import java.util.function.Consumer;

public class LSNetwork {
    public static void register(){
        Services.NETWORK_REGISTRY.registerPacket(
                HeartGainedPayload.TYPE,
                HeartGainedPayload.STREAM_CODEC,
                HeartGainedPayload::handle
        );
    }
}
