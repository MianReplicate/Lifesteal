package mc.mian.lifesteal.common.network;

import commonnetwork.api.Network;
import mc.mian.lifesteal.common.network.custom.HeartGainedPacket;

public class LSNetwork {
    public static void register(){
        Network.registerPacket(
                HeartGainedPacket.CHANNEL,
                HeartGainedPacket.class,
                HeartGainedPacket::encode,
                HeartGainedPacket::decode,
                HeartGainedPacket::handle
        );
    }
}
