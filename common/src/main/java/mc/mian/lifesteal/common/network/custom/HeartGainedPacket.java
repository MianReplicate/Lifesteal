package mc.mian.lifesteal.common.network.custom;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class HeartGainedPacket {
    public static final ResourceLocation CHANNEL = LSUtil.modLoc("heart_gained");
    public static final StreamCodec<FriendlyByteBuf, HeartGainedPacket> STREAM_CODEC =
            StreamCodec.ofMember(HeartGainedPacket::encode, HeartGainedPacket::new);

    public static CustomPacketPayload.Type<CustomPacketPayload> type(){
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public HeartGainedPacket(){}

    public HeartGainedPacket(FriendlyByteBuf buf){

    }

    public void encode(FriendlyByteBuf buf){

    }

    public static void handle(PacketContext<HeartGainedPacket> ctx)
    {
        if (Side.CLIENT.equals(ctx.side())) {
            Minecraft minecraft = Minecraft.getInstance();

            minecraft.level.playLocalSound(minecraft.player.getX(), minecraft.player.getY(), minecraft.player.getZ(), SoundEvents.TOTEM_USE, minecraft.player.getSoundSource(), 1.0F, 1.0F, false);
            minecraft.gameRenderer.displayItemActivation(LSItems.HEART_CRYSTAL.get().getDefaultInstance());
        }
    }
}
