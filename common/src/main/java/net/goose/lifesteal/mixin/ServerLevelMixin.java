package net.goose.lifesteal.mixin;

import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
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
                    HashMap<GameProfile, ModUtil.KilledType> gameProfiles = ModUtil.getDeadPlayers(this.getServer(), true, true);
                    gameProfiles.forEach((profile, killedType) -> {
                        CompoundTag tag = ModUtil.getPlayerData(this.getServer(), profile);
                        long TimeKilled = tag.getLong("TimeKilled");
                        long TimePassed = System.currentTimeMillis() - TimeKilled;
                        if(TimePassed >= LifeSteal.config.deathDuration.get() * 1000){
                            LifeSteal.LOGGER.info("Enough time has passed");
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
