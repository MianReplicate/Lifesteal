package mc.mian.lifesteal.common.network;

import commonnetwork.api.Network;
import mc.mian.lifesteal.common.network.custom.HeartGainedPacket;

public class LSNetwork {
    public static void register(){
        Network.registerPacket(
                HeartGainedPacket.type(),
                HeartGainedPacket.class,
                HeartGainedPacket.STREAM_CODEC,
                HeartGainedPacket::handle
        );
    }
}
