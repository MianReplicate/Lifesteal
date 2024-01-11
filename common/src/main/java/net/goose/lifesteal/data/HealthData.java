package net.goose.lifesteal.data;

import com.mojang.authlib.GameProfile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.api.IHealthData;
import net.goose.lifesteal.api.ILevelData;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.block.custom.ReviveHeadBlock;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.util.ComponentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.*;

public class HealthData implements IHealthData {
    private final LivingEntity livingEntity;
    private final String attributeModifierIdentifier = new String("LifeStealHealthModifier");
    public HealthData(final LivingEntity entity) {
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
    public void revivedTeleport(ILevelData iLevelData, boolean synchronize) {
        if (this.livingEntity instanceof ServerPlayer serverPlayer) {
            HashMap<ResourceKey<Level>, HashMap<UUID, BlockPos>> hashMap = iLevelData.getMap();

            hashMap.forEach((levelResourceKey, uuidBlockPosHashMap) -> {
                // Generally a player shouldn't be revived by multiple revive heads but if they are, buggy shit will happen lol
                if(uuidBlockPosHashMap.containsKey(this.livingEntity.getUUID()))
                {
                    Level level = this.livingEntity.getServer().overworld().registryAccess().registryOrThrow(Registries.DIMENSION).get(levelResourceKey);
                    if (!level.isClientSide) {
                        BlockPos blockPos = uuidBlockPosHashMap.get(this.livingEntity.getUUID());
                        if (blockPos != null) {
                            iLevelData.removeTeleport(level, this.livingEntity.getUUID(), blockPos);
                            if (serverPlayer.level() == level) {
                                serverPlayer.connection.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ(), serverPlayer.getXRot(), serverPlayer.getYRot());
                            } else {
                                serverPlayer.teleportTo((ServerLevel) level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), serverPlayer.getXRot(), serverPlayer.getYRot());
                            }
                            if (serverPlayer.isSpectator()) {
                                serverPlayer.setGameMode(GameType.SURVIVAL);
                            }
                            if (!LifeSteal.config.disableStatusEffects.get()) {
                                int tickTime = 600;
                                this.livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 3));
                                this.livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, tickTime, 3));
                                this.livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, tickTime, 3));
                            }
                            if (LifeSteal.config.customHeartDifferenceWhenRevived.get()) {
                                setHealthDifference(LifeSteal.config.startingHeartDifferenceFromCrystal.get());
                            } else {
                                setHealthDifference(LifeSteal.config.startingHealthDifference.get());
                            }
                            refreshHearts(true);
                            if (synchronize) {
                                serverPlayer.jumpFromGround();
                                serverPlayer.syncPacketPositionCodec(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                            }
                            ModCriteria.REVIVED.trigger(serverPlayer);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void revivedTeleport(ILevelData iLevelData) {
        revivedTeleport(iLevelData, true);
    }

    @Override
    public BlockPos spawnPlayerHead() {
        if (this.livingEntity instanceof ServerPlayer serverPlayer) {
            Level level = serverPlayer.level();
            if (!level.isClientSide) {
                BlockPos playerPos = serverPlayer.blockPosition();

                int y = playerPos.getY();

                if(y <= level.dimensionType().minY() || y >= level.getHeight())
                {
                    for(int i = 1; i < level.getHeight(); i++)
                    {
                        BlockPos pos = new BlockPos(playerPos.getX(), i, playerPos.getZ());

                        if(level.getBlockState(pos).isAir() || level.getBlockState(pos).getDestroySpeed(level, pos) > -1)
                        {
                            y = i;
                            break;
                        }
                    }
                }

                BlockPos targetPos = new BlockPos(playerPos.getX(), y, playerPos.getZ());

                if(level.getBlockState(targetPos).getDestroySpeed(level, targetPos) > -1)
                {
                    while(level.getBlockEntity(targetPos) != null)
                    {
                        targetPos = targetPos.above();
                    }

                    final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
                    BlockState playerHeadState = ModBlocks.REVIVE_HEAD.get().defaultBlockState().setValue(ROTATION, Integer.valueOf(Mth.floor((double) ((180.0F + serverPlayer.getYRot()) * 16.0F / 360.0F) + 0.5) & 15));
                    if(!level.setBlockAndUpdate(targetPos, playerHeadState)) {
                        return null;
                    }
                    ReviveSkullBlockEntity playerHeadEntity = (ReviveSkullBlockEntity) ((ReviveHeadBlock)playerHeadState.getBlock()).newBlockEntity(targetPos, playerHeadState);
                    playerHeadEntity.setOwner(serverPlayer.getGameProfile());
                    level.setBlockEntity(playerHeadEntity);

                    BlockPos currentPos = playerHeadEntity.getBlockPos();
                    LifeSteal.LOGGER.info(serverPlayer.getName().getString() + "'s revive head has been placed at" + " X: " + currentPos.getX() + " Y: " + currentPos.getY() + " Z: " + currentPos.getZ());

                    return currentPos;
                }
            }
        }
        return null;
    }
    @Override
    public boolean dropPlayerHead(){
        if (this.livingEntity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.level().isClientSide) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putString("SkullOwner", serverPlayer.getName().toString());

                ItemStack itemStack = new ItemStack(ModItems.REVIVE_HEAD_ITEM.get());
                itemStack.setTag(compoundTag);
                serverPlayer.drop(itemStack, true, false);
                return true;
            }
        }
        return false;
    }
    @Override
    public LivingEntity getLivingEntity() {
        return this.livingEntity;
    }

    @ExpectPlatform
    public static int getHealthDifference(HealthData healthData) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setHealthDifference(HealthData healthData, int health) {
        throw new AssertionError();
    }
    @Override
    public int getHealthDifference() {
        return getHealthDifference(this);
    }

    @Override
    public void setHealthDifference(int health) {
        if (!this.livingEntity.level().isClientSide) {
            setHealthDifference(this, health);
        }
    }

    // Returns the real amount of hitpoints a player has, includes every other mod's effect and ours.
    @Override
    public double getHeartModifiedTotal(boolean includeHeartDifference){
        AttributeInstance Attribute = this.livingEntity.getAttribute(Attributes.MAX_HEALTH);
        Set<AttributeModifier> attributemodifiers = Attribute.getModifiers();
        double healthModifiedTotal = includeHeartDifference ? getHealthDifference() : 0.0;

        if (!attributemodifiers.isEmpty()) {
            Iterator<AttributeModifier> attributeModifierIterator = attributemodifiers.iterator();

            AttributeModifier attributeModifier;
            while(attributeModifierIterator.hasNext()){
                attributeModifier = attributeModifierIterator.next();
                if(attributeModifier != null){
                    if (!attributeModifier.name.equals(attributeModifierIdentifier)) {
                        if (attributeModifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                            double amount = attributeModifier.getAmount();
                            healthModifiedTotal += amount;
                        } else if (attributeModifier.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                            healthModifiedTotal += this.livingEntity.getMaxHealth() / attributeModifier.getAmount();
                        } else if (attributeModifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                            healthModifiedTotal += Attribute.getBaseValue() * attributeModifier.getAmount();
                        }
                    }
                }
            }
        }

        return healthModifiedTotal;
    }

    // Returns the amount a player's HPDifference would have to be to get banned.
    @Override
    public double getHPDifferenceRequiredForBan(){
        double healthModified = this.getHeartModifiedTotal(false) + this.livingEntity.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
        return -healthModified;
    }

    @Override
    public void banForDeath(){
        if(!this.livingEntity.level().isClientSide){
            if (this.livingEntity instanceof ServerPlayer serverPlayer) {
                setHealthDifference(LifeSteal.config.startingHealthDifference.get());

                refreshHearts(true);

                MinecraftServer server = this.livingEntity.level().getServer();

                Component bannedcomponent = Component.translatable("bannedmessage.lifesteal.lost_max_hearts");
                Component fullcomponent = null;

                if (!server.isSingleplayer() && LifeSteal.config.uponDeathBanned.get() && !server.getPlayerList().getBans().isBanned(serverPlayer.getGameProfile())) {

                    if (LifeSteal.config.playersSpawnHeadUponDeath.get()) {
                        BlockPos blockPos = spawnPlayerHead();
                        if(blockPos == null){
                            dropPlayerHead();
                        } else {
                            Component compPos = Component.translatable("bannedmessage.lifesteal.revive_head_location", blockPos.getX(), blockPos.getY(), blockPos.getZ());
                            fullcomponent = ComponentUtil.addComponents(bannedcomponent, compPos, false);
                        }
                    }

                    if(fullcomponent == null){
                        fullcomponent = bannedcomponent;
                    }

                    if(serverPlayer.isDeadOrDying()){serverPlayer.getInventory().dropAll();}

                    UserBanList userbanlist = server.getPlayerList().getBans();
                    serverPlayer.getGameProfile();
                    GameProfile gameprofile = serverPlayer.getGameProfile();

                    UserBanListEntry userbanlistentry = new UserBanListEntry(gameprofile, null, LifeSteal.MOD_ID, null, fullcomponent == null ? null : fullcomponent.getString());
                    userbanlist.add(userbanlistentry);

                    if (serverPlayer != null) {
                        serverPlayer.connection.disconnect(fullcomponent);
                    }
                } else if (!serverPlayer.isSpectator()) {
                    if (LifeSteal.config.playersSpawnHeadUponDeath.get() && !server.isSingleplayer()) {
                        BlockPos blockPos = spawnPlayerHead();
                        if(blockPos == null){
                            dropPlayerHead();
                        } else {
                            Component compPos = Component.translatable("bannedmessage.lifesteal.revive_head_location", blockPos.getX(), blockPos.getY(), blockPos.getZ());
                            fullcomponent = ComponentUtil.addComponents(bannedcomponent, compPos, false);
                        }
                    }

                    if(fullcomponent == null){
                        fullcomponent = bannedcomponent;
                    }

                    if(serverPlayer.isDeadOrDying()){serverPlayer.getInventory().dropAll();}

                    serverPlayer.setGameMode(GameType.SPECTATOR);
                    this.livingEntity.sendSystemMessage(fullcomponent);
                }

            }
        }
    }

    @Override
    public void refreshHearts(boolean healtoMax) {

        if (!this.livingEntity.level().isClientSide) {
            final int defaultHealthDifference = LifeSteal.config.startingHealthDifference.get();
            final int maximumHealthGainable = LifeSteal.config.maximumHealthGainable.get();
            final int maximumHealthLoseable = LifeSteal.config.maximumHealthLoseable.get();

            int healthDifference = getHealthDifference();

            if (maximumHealthGainable > -1) {
                if (healthDifference - defaultHealthDifference >= maximumHealthGainable) {
                    healthDifference = maximumHealthGainable + defaultHealthDifference;

                    if (LifeSteal.config.tellPlayersIfReachedMaxHearts.get()) {
                        this.livingEntity.sendSystemMessage(Component.translatable("chat.message.lifesteal.reached_max_hearts"));
                    }
                }
            }

            if (maximumHealthLoseable >= 0) {
                healthDifference = Math.max(healthDifference, defaultHealthDifference - maximumHealthLoseable);
            }

            setHealthDifference(healthDifference);

            AttributeInstance Attribute = this.livingEntity.getAttribute(Attributes.MAX_HEALTH);
            Set<AttributeModifier> attributemodifiers = Attribute.getModifiers();

            if (attributemodifiers.isEmpty()) {
                AttributeModifier attributeModifier = new AttributeModifier(attributeModifierIdentifier, healthDifference, AttributeModifier.Operation.ADDITION);
                Attribute.addPermanentModifier(attributeModifier);
            }else{
                Iterator<AttributeModifier> attributeModifierIterator = attributemodifiers.iterator();
                boolean FoundAttribute = false;

                AttributeModifier attributeModifier;
                while(attributeModifierIterator.hasNext()){
                    attributeModifier = attributeModifierIterator.next();
                    if(attributeModifier != null){
                        if (attributeModifier.name.equals(attributeModifierIdentifier)) {
                            FoundAttribute = true;
                            Attribute.removeModifier(attributeModifier.getId());
                            AttributeModifier newmodifier = new AttributeModifier(attributeModifierIdentifier, healthDifference, AttributeModifier.Operation.ADDITION);
                            Attribute.addPermanentModifier(newmodifier);
                        }
                    }
                }

                if (!FoundAttribute) {
                    attributeModifier = new AttributeModifier(attributeModifierIdentifier, healthDifference, AttributeModifier.Operation.ADDITION);
                    Attribute.addPermanentModifier(attributeModifier);
                }
            }

            if (healthDifference >= 20 && this.livingEntity instanceof ServerPlayer serverPlayer) {
                ModCriteria.GET_10_MAX_HEARTS.trigger(serverPlayer);
            }

            if (this.livingEntity.getHealth() > this.livingEntity.getMaxHealth() || healtoMax) {
                this.livingEntity.setHealth(this.livingEntity.getMaxHealth());
            }
        }

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("heartdifference", getHealthDifference());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setHealthDifference(tag.getInt("heartdifference"));
    }
}