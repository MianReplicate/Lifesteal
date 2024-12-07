package mc.mian.lifesteal.mixin;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.api.PlayerImpl;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, priority = 1)
public abstract class PlayerMixin extends LivingEntity implements PlayerImpl {
    @Shadow public abstract boolean killedEntity(ServerLevel level, LivingEntity entity);

    private boolean revived;
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
        LSData.get(this).ifPresent(iLifestealData -> iLifestealData.refreshHealth(false));
    }

    public void increaseHealth(LivingEntity killerPlayer, int hitpoint, LivingEntity killedPlayer) {
        final int maximumhitpointsGainable = LifeSteal.config.maximumHealthGainable.get();
        boolean alreadyGiven = false;

        LSData lsData = LSData.get(killerPlayer).orElseGet(null);

        if(lsData != null){
            if (maximumhitpointsGainable > -1 && LifeSteal.config.playerDropsHeartCrystalWhenKillerHasMax.get() && !LifeSteal.config.playerDropsHeartCrystalWhenKilled.get()) {
                if ((int)lsData.getValue(LSConstants.HEALTH_DIFFERENCE) + hitpoint > LifeSteal.config.startingHealthDifference.get() + maximumhitpointsGainable) {
                    LSUtil.ripHeartCrystalFromPlayer(killedPlayer);
                    alreadyGiven = true;
                }
            }

            if (!alreadyGiven) {
                if (!LifeSteal.config.playerDropsHeartCrystalWhenKilled.get()) {
                    LSUtil.gainHealth(killerPlayer, (int)lsData.getValue(LSConstants.HEALTH_DIFFERENCE) + hitpoint);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickMethod(final CallbackInfo info){
        if(!this.level().isClientSide){
            LSData.get(this).ifPresent(lifestealData -> {
                // Are we at the amount where player should be banned based on their stats?
                if(((Integer) lifestealData.getValue(LSConstants.HEALTH_DIFFERENCE)).doubleValue() <= lifestealData.getHPDifferenceRequiredForBan()){
                    lifestealData.tick();
                }
                lifestealData.tryRevivalEffects();
            });
        }
    }
    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void onDeath(final CallbackInfo info) {

        final int maximumheartsLoseable = LifeSteal.config.maximumHealthLoseable.get();
        final int startingHitPointDifference = LifeSteal.config.startingHealthDifference.get();
        final int amountOfHealthLostUponLossConfig = LifeSteal.config.amountOfHealthLostUponLoss.get();
        final boolean playersGainHeartsifKillednoHeart = LifeSteal.config.playersGainHeartsifKillednoHeart.get();
        final boolean disableLifesteal = LifeSteal.config.disableLifesteal.get();
        final boolean loseHeartsWhenKilledByPlayer = LifeSteal.config.loseHeartsWhenKilledByPlayer.get();
        final boolean loseHeartsWhenKilledByMob = LifeSteal.config.loseHeartsWhenKilledByMob.get();
        final boolean loseHeartsWhenKilledByEnvironment = LifeSteal.config.loseHeartsWhenKilledByEnvironment.get();

        LivingEntity killedEntity = this;

        LSData.get(killedEntity).ifPresent(lifestealData -> {
            if (killedEntity instanceof ServerPlayer) {
                if (!killedEntity.isAlive()) {
                    int HeartDifference = lifestealData.getValue(LSConstants.HEALTH_DIFFERENCE);

                    LivingEntity killerEntity = killedEntity.getLastHurtByMob();
                    boolean killerEntityIsPlayer = killerEntity instanceof ServerPlayer;
                    ServerPlayer killerPlayer = killerEntityIsPlayer ? (ServerPlayer) killerEntity: null;

                    int amountOfHealthLostUponLoss;

                    if (maximumheartsLoseable < 0) {
                        if (20 + HeartDifference - amountOfHealthLostUponLossConfig >= 0 || playersGainHeartsifKillednoHeart) {
                            amountOfHealthLostUponLoss = amountOfHealthLostUponLossConfig;
                        } else {
                            amountOfHealthLostUponLoss = 20 + HeartDifference;
                        }
                    } else {
                        if (20 + HeartDifference - amountOfHealthLostUponLossConfig >= (20 + startingHitPointDifference) - maximumheartsLoseable || playersGainHeartsifKillednoHeart) {
                            amountOfHealthLostUponLoss = amountOfHealthLostUponLossConfig;
                        } else {
                            amountOfHealthLostUponLoss = HeartDifference + maximumheartsLoseable;
                        }
                    }

                    // THE CODE BELOW IS FOR INCREASING THE KILLER ENTITY HITPOINTDIFFERENCE IF THEY EXIST
                    if (killerEntity != null) { // IF THERE IS A KILLER ENTITY
                        if (killerEntity != killedEntity) { // IF IT'S NOT THEMSELVES (Shooting themselves with an arrow lol)
                            // EVERYTHING BELOW THIS COMMENT IS CODE FOR MAKING THE KILLER PERSON'S HEART DIFFERENCE GO UP.
                            if (killerEntityIsPlayer && !disableLifesteal) {
                                if (playersGainHeartsifKillednoHeart) {
                                    increaseHealth(killerEntity, amountOfHealthLostUponLoss, killedEntity);
                                } else {
                                    if (maximumheartsLoseable > -1) {
                                        if (startingHitPointDifference + HeartDifference > -maximumheartsLoseable) {
                                            increaseHealth(killerEntity, amountOfHealthLostUponLoss, killedEntity);
                                        } else {
                                            killerPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.no_more_hearts_to_steal"));
                                        }

                                    } else {
                                        increaseHealth(killerEntity, amountOfHealthLostUponLoss, killedEntity);
                                    }
                                }
                            }
                        }
                    }

                    // THE CODE BELOW IS FOR REDUCING THE KILLED ENTITY'S HITPOINTDIFFERENCE

                    if (loseHeartsWhenKilledByPlayer || loseHeartsWhenKilledByMob || loseHeartsWhenKilledByEnvironment) {
                        if (killerEntity != null) { // IF KILLER ENTITY EXISTS
                            if (killedEntity != killerEntity) { // IF KILLER ENTITY ISNT SELF/ IF A KILLER KILLED OUR GUY
                                if (killerEntityIsPlayer) { // IF THEY ARE A PLAYER
                                    if (!loseHeartsWhenKilledByPlayer) {
                                        return;
                                    }
                                } else if (!loseHeartsWhenKilledByMob) {
                                    return;
                                }
                            } else if (!loseHeartsWhenKilledByPlayer) {
                                return;
                            }
                        } else if (!loseHeartsWhenKilledByEnvironment) {
                            return;
                        }
                    } else {
                        return;
                    }

                    lifestealData.setValue(LSConstants.HEALTH_DIFFERENCE,(int)lifestealData.getValue(LSConstants.HEALTH_DIFFERENCE) - amountOfHealthLostUponLoss);
                    lifestealData.refreshHealth(false);
                    if (LifeSteal.config.playerDropsHeartCrystalWhenKilled.get()) {
                        LSUtil.ripHeartCrystalFromPlayer(killedEntity);
                    }
                }
            }
        });
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void addOurDataTooLol(CompoundTag compoundTag, final CallbackInfo info){
        compoundTag.putBoolean("Revived", this.getRevived());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void loadOurDataTooLol(CompoundTag compoundTag, final CallbackInfo info){
       this.setRevived(compoundTag.getBoolean("Revived"));
    }

    @Override
    public void setRevived(boolean bool) {
        this.revived = bool;
    }

    @Override
    public boolean getRevived() {
        return this.revived;
    }
}
