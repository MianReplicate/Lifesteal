package net.goose.lifesteal.mixin;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void onDeath(final CallbackInfo info) {

        LifeSteal.LOGGER.info("RUNNING!");

        final int maximumheartsGainable = LifeSteal.config.maximumamountofheartsGainable.get();
        final int maximumheartsLoseable = LifeSteal.config.maximumamountofheartsLoseable.get();
        final int startingHitPointDifference = LifeSteal.config.startingHeartDifference.get();
        final int amountOfHealthLostUponLossConfig = LifeSteal.config.amountOfHealthLostUponLoss.get();
        final boolean playersGainHeartsifKillednoHeart = LifeSteal.config.playersGainHeartsifKillednoHeart.get();
        final boolean disableLifesteal = LifeSteal.config.disableLifesteal.get();
        final boolean disableHeartLoss = LifeSteal.config.disableHeartLoss.get();
        final boolean loseHeartsOnlyWhenKilledByEntity = LifeSteal.config.loseHeartsOnlyWhenKilledByEntity.get();
        final boolean loseHeartsOnlyWhenKilledByPlayer = LifeSteal.config.loseHeartsOnlyWhenKilledByPlayer.get();

        LivingEntity killedEntity = this;

        HealthData.get(killedEntity).ifPresent(healthData -> {
            if (killedEntity instanceof ServerPlayer) {
                if (!killedEntity.isAlive()) {
                    int HeartDifference = healthData.getHeartDifference();

                    LivingEntity killerEntity = killedEntity.getLastHurtByMob();
                    boolean killerEntityIsPlayer = killerEntity instanceof ServerPlayer;
                    ServerPlayer serverPlayer;
                    if (killerEntityIsPlayer) {
                        serverPlayer = (ServerPlayer) killerEntity;
                    } else {
                        serverPlayer = null;
                    }

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

                    if (killerEntity != null) { // IF THERE IS A KILLER ENTITY
                        if (killerEntity != killedEntity) { // IF IT'S NOT THEMSELVES (Shooting themselves with an arrow lol)
                            // EVERYTHING BELOW THIS COMMENT IS CODE FOR MAKING THE KILLER PERSON'S HEART DIFFERENCE GO UP.
                            if (killerEntityIsPlayer && !disableLifesteal) {
                                HealthData.get(killerEntity).ifPresent(killerData -> {
                                    if (playersGainHeartsifKillednoHeart) {
                                        killerData.setHeartDifference(killerData.getHeartDifference() + amountOfHealthLostUponLoss);
                                        killerData.refreshHearts(false);

                                    } else {

                                        if (!disableHeartLoss) {
                                            if (maximumheartsLoseable > -1) {
                                                if (startingHitPointDifference + HeartDifference > -maximumheartsLoseable) {
                                                    killerData.setHeartDifference(killerData.getHeartDifference() + amountOfHealthLostUponLoss);
                                                    killerData.refreshHearts(false);
                                                } else {
                                                    serverPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.no_more_hearts_to_steal"));
                                                }

                                            } else {
                                                killerData.setHeartDifference(killerData.getHeartDifference() + amountOfHealthLostUponLoss);
                                                killerData.refreshHearts(false);
                                            }
                                        } else {
                                            serverPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.no_more_hearts_to_steal"));
                                        }
                                    }

                                });
                            }

                            // EVERYTHING BELOW THIS COMMENT IS CODE FOR LOWERING THE KILLED PERSON'S HEART DIFFERENCE IF THERE WAS A KILLER ENTITY
                            if (!disableHeartLoss) {
                                if (loseHeartsOnlyWhenKilledByPlayer && !loseHeartsOnlyWhenKilledByEntity) {
                                    if (killerEntityIsPlayer && !disableLifesteal) {
                                        healthData.setHeartDifference(healthData.getHeartDifference() - amountOfHealthLostUponLoss);
                                        healthData.refreshHearts(false);
                                    }
                                } else {
                                    if (disableLifesteal) {
                                        if (!killerEntityIsPlayer) {
                                            healthData.setHeartDifference(healthData.getHeartDifference() - amountOfHealthLostUponLoss);
                                            healthData.refreshHearts(false);
                                        }
                                    } else {
                                        healthData.setHeartDifference(healthData.getHeartDifference() - amountOfHealthLostUponLoss);
                                        healthData.refreshHearts(false);
                                    }
                                }
                            }
                        } else if (!disableHeartLoss) { // IF THIS IS THEMSELVES
                            healthData.setHeartDifference(healthData.getHeartDifference() - amountOfHealthLostUponLoss);
                            healthData.refreshHearts(false);
                        }
                    } else {
                        if (!loseHeartsOnlyWhenKilledByEntity && !loseHeartsOnlyWhenKilledByPlayer && !disableHeartLoss) {
                            healthData.setHeartDifference(healthData.getHeartDifference() - amountOfHealthLostUponLoss);
                            healthData.refreshHearts(false);
                        }
                    }

                }
            }
        });
    }
}
