package net.goose.lifesteal.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.LifestealData;
import net.goose.lifesteal.util.ModResources;
import net.goose.lifesteal.util.ModUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    private static int tickTime = 0;
    @Shadow public abstract ServerLevel getLevel();

    @Shadow @Nonnull public abstract MinecraftServer getServer();

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BooleanSupplier hasTimeLeft, CallbackInfo ci){
        if(this.getLevel().dimension() == ServerLevel.OVERWORLD && !this.getLevel().isClientSide){
            tickTime++;
            if(tickTime%20==0){
                tickTime = 0;
                if(LifeSteal.config.deathDuration.get() != 0){
                    ImmutableMap<GameProfile, ModUtil.KilledType> gameProfiles = ModUtil.getDeadPlayers(this.getServer());
                    gameProfiles.forEach((profile, killedType) -> {
                        ServerPlayer player = this.getServer().getPlayerList().getPlayer(profile.getId());
                        long TimeKilled = 0L;
                        if(player != null){
                            TimeKilled = LifestealData.get(player).get().getValue(ModResources.TIME_KILLED);
                        } else {
                            CompoundTag tag = ModUtil.getPlayerData(this.getServer(), profile);
                            if(tag != null){
                                TimeKilled = ModUtil.getLifestealDataFromTag(
                                        tag,
                                        ModResources.TIME_KILLED.getPath(),
                                        CompoundTag::getLong);
                            }
                        }
                        long TimePassed = System.currentTimeMillis() - TimeKilled;
                        if(TimePassed >= LifeSteal.config.deathDuration.get() * 1000){
                            ModUtil.revivePlayer(
                                    this.getLevel(),
                                    this.getLevel().getSharedSpawnPos(),
                                    profile,
                                    false,
                                    true,
                                    null
                            );
                        }
                    });
                }
            }
        }
    }
}
