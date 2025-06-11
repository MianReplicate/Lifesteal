package mc.mian.lifesteal.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.platform.Services;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Unique
    private static int lifesteal$tickTime = 0;
    @Shadow public abstract ServerLevel getLevel();

    @Shadow public abstract MinecraftServer getServer();

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BooleanSupplier hasTimeLeft, CallbackInfo ci){
        if(this.getLevel().dimension() == ServerLevel.OVERWORLD && !this.getLevel().isClientSide){
            IndestructibleUtil.setIndestructibilityState(LSBlocks.REVIVE_HEAD.getId().toString(), LifeSteal.config.unbreakableReviveHeads.get());
            IndestructibleUtil.setIndestructibilityState(LSBlocks.REVIVE_WALL_HEAD.getId().toString(), LifeSteal.config.unbreakableReviveHeads.get());

            lifesteal$tickTime++;
            if(lifesteal$tickTime %20==0){
                lifesteal$tickTime = 0;
                if(LifeSteal.config.deathDuration.get() != 0){
                    ImmutableMap<GameProfile, LSUtil.KilledType> gameProfiles = LSUtil.getDeadPlayers(this.getServer());
                    gameProfiles.forEach((profile, killedType) -> {
                        ServerPlayer player = this.getServer().getPlayerList().getPlayer(profile.getId());
                        long TimeKilled = 0L;
                        if(player != null){
                            TimeKilled = LSData.get(player).get().getValue(LSConstants.TIME_KILLED);
                        } else {
                            CompoundTag tag = LSUtil.getPlayerData(this.getServer(), profile);
                            if(tag != null){
                                TimeKilled = Services.DATA_HELPER.getLifestealDataFromTag(
                                        tag,
                                        LSConstants.TIME_KILLED.getPath(),
                                        CompoundTag::getLong).orElse(0L);
                            }
                        }
                        long TimePassed = System.currentTimeMillis() - TimeKilled;
                        if(TimePassed >= LifeSteal.config.deathDuration.get() * 1000){
                            LSUtil.revivePlayer(
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
