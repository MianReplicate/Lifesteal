package mc.mian.lifesteal.common.network.custom;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.network.Context;
import mc.mian.lifesteal.common.network.NetworkRegistry;
import mc.mian.lifesteal.common.network.Side;
import mc.mian.lifesteal.platform.Services;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;

public class HeartGainedPayload implements CustomPacketPayload {
    public static CustomPacketPayload.Type<HeartGainedPayload> TYPE = new CustomPacketPayload.Type<HeartGainedPayload>(LSConstants.modLoc("heart_gained"));
    public static final StreamCodec<RegistryFriendlyByteBuf, HeartGainedPayload> STREAM_CODEC =
            StreamCodec.ofMember(HeartGainedPayload::encode, HeartGainedPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public HeartGainedPayload(){}

    public HeartGainedPayload(FriendlyByteBuf buf){

    }

    public void encode(FriendlyByteBuf buf){

    }

    public static void handle(Context context)
    {
        if(context.side() == Side.CLIENT){
            Minecraft minecraft = Minecraft.getInstance();

            minecraft.level.playLocalSound(minecraft.player.getX(), minecraft.player.getY(), minecraft.player.getZ(), SoundEvents.TOTEM_USE, minecraft.player.getSoundSource(), 1.0F, 1.0F, false);
            minecraft.player.displayItemActivation(LSItems.HEART_CRYSTAL.get().getDefaultInstance());
        }
    }
}
