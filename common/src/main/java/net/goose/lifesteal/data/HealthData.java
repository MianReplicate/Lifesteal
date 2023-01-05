package net.goose.lifesteal.data;

import com.mojang.authlib.GameProfile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.api.IHealthData;
import net.goose.lifesteal.api.ILevelData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class HealthData implements IHealthData {
    private final LivingEntity livingEntity;
    private int heartDifference = LifeSteal.config.startingHeartDifference.get();

    public HealthData(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @ExpectPlatform
    public static Optional<HealthData> get(LivingEntity livingEntity) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static Optional<HealthData> get(Entity entity) {
        throw new AssertionError();
    }
    @Override
    public void revivedTeleport(ServerLevel level, ILevelData iLevelData, boolean synchronize) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (!level.isClientSide) {
                HashMap hashMap = iLevelData.getMap();
                BlockPos blockPos = (BlockPos) hashMap.get(livingEntity.getUUID());

                if (blockPos != null) {
                    iLevelData.removeUUIDanditsBlockPos(livingEntity.getUUID(), blockPos);
                    if (serverPlayer.getLevel() == level) {
                        serverPlayer.connection.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ(), serverPlayer.getXRot(), serverPlayer.getYRot());
                    } else {
                        serverPlayer.teleportTo(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), serverPlayer.getXRot(), serverPlayer.getYRot());
                    }
                    if (serverPlayer.isSpectator()) {
                        serverPlayer.setGameMode(GameType.SURVIVAL);
                    }
                    int tickTime = 600;
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 3));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, tickTime, 3));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, tickTime, 3));
                    if (synchronize) {
                        serverPlayer.jumpFromGround();
                        serverPlayer.syncPacketPositionCodec(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    }
                    ModCriteria.REVIVED.trigger(serverPlayer);
                }
            }
        }
    }

    @Override
    public void revivedTeleport(ServerLevel level, ILevelData iLevelData) {
        revivedTeleport(level, iLevelData, true);
    }

    @Override
    public void spawnPlayerHead(ServerPlayer serverPlayer) {
        Level level = serverPlayer.level;
        BlockPos blockPos = serverPlayer.blockPosition();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity != null) {
            blockEntity.setRemoved();
        }
        final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
        LifeSteal.LOGGER.info(String.valueOf(-(double) (serverPlayer.getYRot() * 16.0F / 360.0F)));
        BlockState playerHeadState = Blocks.PLAYER_HEAD.defaultBlockState().setValue(ROTATION, Integer.valueOf(Mth.floor((double)((180.0F + serverPlayer.getYRot()) * 16.0F / 360.0F) + 0.5) & 15));
        level.setBlockAndUpdate(blockPos, playerHeadState);
        SkullBlockEntity playerHeadEntity = new SkullBlockEntity(blockPos, playerHeadState);
        playerHeadEntity.setOwner(serverPlayer.getGameProfile());
        level.setBlockEntity(playerHeadEntity);
    }

    @Override
    public int getHeartDifference() {
        return this.heartDifference;
    }

    @Override
    public void setHeartDifference(int hearts) {
        if (!livingEntity.level.isClientSide) {
            this.heartDifference = hearts;
        }
    }

    @Override
    public void refreshHearts(boolean healtoMax) {

        if (!livingEntity.level.isClientSide) {
            final int defaultheartDifference = LifeSteal.config.startingHeartDifference.get();
            final int maximumheartsGainable = LifeSteal.config.maximumamountofheartsGainable.get();
            final int minimumamountofheartscanlose = LifeSteal.config.maximumamountofheartsLoseable.get();

            AttributeInstance Attribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
            Set<AttributeModifier> attributemodifiers = Attribute.getModifiers();

            if (maximumheartsGainable > 0) {
                if (this.heartDifference - defaultheartDifference >= maximumheartsGainable) {
                    this.heartDifference = maximumheartsGainable + defaultheartDifference;

                    if (LifeSteal.config.tellPlayersIfReachedMaxHearts.get()) {
                        livingEntity.sendSystemMessage(Component.translatable("chat.message.lifesteal.reached_max_hearts"));
                    }
                }
            }

            if (minimumamountofheartscanlose >= 0) {
                if (this.heartDifference < defaultheartDifference - minimumamountofheartscanlose) {
                    this.heartDifference = defaultheartDifference - minimumamountofheartscanlose;
                }
            }

            if (!attributemodifiers.isEmpty()) {
                Iterator<AttributeModifier> attributeModifierIterator = attributemodifiers.iterator();

                boolean FoundAttribute = false;

                while (attributeModifierIterator.hasNext()) {

                    AttributeModifier attributeModifier = attributeModifierIterator.next();
                    if (attributeModifier != null && attributeModifier.getName().equals("LifeStealHealthModifier")) {
                        FoundAttribute = true;

                        Attribute.removeModifier(attributeModifier);

                        AttributeModifier newmodifier = new AttributeModifier("LifeStealHealthModifier", this.heartDifference, AttributeModifier.Operation.ADDITION);

                        Attribute.addPermanentModifier(newmodifier);
                    }
                }

                if (!FoundAttribute) {

                    AttributeModifier attributeModifier = new AttributeModifier("LifeStealHealthModifier", this.heartDifference, AttributeModifier.Operation.ADDITION);

                    Attribute.addPermanentModifier(attributeModifier);
                }
            } else {

                AttributeModifier attributeModifier = new AttributeModifier("LifeStealHealthModifier", this.heartDifference, AttributeModifier.Operation.ADDITION);

                Attribute.addPermanentModifier(attributeModifier);
            }

            if (this.heartDifference >= 20 && livingEntity instanceof ServerPlayer serverPlayer) {
                ModCriteria.GET_10_MAX_HEARTS.trigger(serverPlayer);
            }

            if (livingEntity.getHealth() > livingEntity.getMaxHealth() || healtoMax) {
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }

            if (livingEntity.getMaxHealth() <= 1 && this.heartDifference <= -20) {
                if (livingEntity instanceof ServerPlayer serverPlayer) {

                    this.heartDifference = defaultheartDifference;

                    refreshHearts(true);

                    MinecraftServer server = livingEntity.level.getServer();

                    if (!server.isSingleplayer()) {

                        if (LifeSteal.config.playersSpawnHeadUponDeath.get()) {
                            spawnPlayerHead(serverPlayer);
                        }

                        serverPlayer.getInventory().dropAll();

                        @Nullable Component component = Component.translatable("bannedmessage.lifesteal.lost_max_hearts");
                        UserBanList userbanlist = server.getPlayerList().getBans();
                        serverPlayer.getGameProfile();
                        GameProfile gameprofile = serverPlayer.getGameProfile();

                        UserBanListEntry userbanlistentry = new UserBanListEntry(gameprofile, null, "Lifesteal", null, component == null ? null : component.getString());
                        userbanlist.add(userbanlistentry);

                        if (serverPlayer != null) {
                            serverPlayer.connection.disconnect(Component.translatable("bannedmessage.lifesteal.lost_max_hearts"));
                        }
                    } else if (!serverPlayer.isSpectator()) {
                        serverPlayer.setGameMode(GameType.SPECTATOR);
                        livingEntity.sendSystemMessage(Component.translatable("chat.message.lifesteal.lost_max_hearts"));
                    }

                }
            }
        }

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("heartdifference", getHeartDifference());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setHeartDifference(tag.getInt("heartdifference"));
    }
}
