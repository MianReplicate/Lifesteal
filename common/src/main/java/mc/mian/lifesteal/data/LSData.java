package mc.mian.lifesteal.data;

import com.mojang.authlib.GameProfile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.advancement.LSCriteria;
import mc.mian.lifesteal.api.ILSData;
import mc.mian.lifesteal.api.PlayerImpl;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.block.custom.ReviveHeadBlock;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
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
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.*;

public class LSData implements ILSData {
    private final HashMap<ResourceLocation, Object> dataMap = new HashMap<>();
    private final LivingEntity livingEntity;
    public LSData(final LivingEntity entity) {
        this.livingEntity = entity;
        this.dataMap.putIfAbsent(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
        this.dataMap.putIfAbsent(LSConstants.TIME_KILLED, 0L);
        this.dataMap.putIfAbsent(LSConstants.STORED_MODIFIER_UUID, UUID.randomUUID());
    }

    @ExpectPlatform
    public static Optional<LSData> get(Entity entity) {
        throw new AssertionError();
    }

    @Override
    public void tryRevivalEffects() {
        if (this.livingEntity instanceof ServerPlayer serverPlayer) {
            PlayerImpl playerImpl = ((PlayerImpl) serverPlayer);
            if(playerImpl.getRevived())
            {
                Level level = this.livingEntity.level();
                if (!level.isClientSide) {
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
                        setValue(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHeartDifferenceFromCrystal.get());
                    } else {
                        setValue(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
                    }
                    setValue(LSConstants.TIME_KILLED, 0L);
                    refreshHealth(true);
                    LSCriteria.BACK_FROM_THE_DEAD.trigger(serverPlayer);
                    playerImpl.setRevived(false);
                }
            }
        }
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
                    BlockState playerHeadState = LSBlocks.REVIVE_HEAD.get().defaultBlockState().setValue(ROTATION, Integer.valueOf(Mth.floor((double) ((180.0F + serverPlayer.getYRot()) * 16.0F / 360.0F) + 0.5) & 15));
                    if(!level.setBlockAndUpdate(targetPos, playerHeadState)) {
                        return null;
                    }
                    SkullBlockEntity playerHeadEntity = (SkullBlockEntity) ((ReviveHeadBlock)playerHeadState.getBlock()).newBlockEntity(targetPos, playerHeadState);
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
                ItemStack itemStack = new ItemStack(LSItems.REVIVE_HEAD_ITEM.get());
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putString("SkullOwner", serverPlayer.getName().toString());
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

    @Override
    public Collection<ResourceLocation> getKeys(){
        return this.dataMap.keySet();
    }

    @Override
    public <T> T getValue(ResourceLocation key) {
        return (T) this.dataMap.get(key);
    }

    @Override
    public <T> void setValue(ResourceLocation key, T value) {
        if (!this.livingEntity.level().isClientSide) {
            this.dataMap.put(key, value);
        }
    }

    // Returns the real amount of hitpoints a player has
    @Override
    public double getAmountOfModifiedHealth(boolean includeHealthDifference){
        return LSUtil.calculateRealValue(this.livingEntity.getAttribute(Attributes.MAX_HEALTH))
                - this.livingEntity.getAttribute(Attributes.MAX_HEALTH).getBaseValue()
                - (includeHealthDifference ? 0 : ((Integer) this.getValue(LSConstants.HEALTH_DIFFERENCE)).doubleValue());
    }

    // Returns the amount a player's HPDifference would have to be to get banned.
    @Override
    public double getHPDifferenceRequiredForBan(){
        return LifeSteal.config.banDynamicHearts.get() ?
                -(this.getAmountOfModifiedHealth(false)
                        + this.livingEntity.getAttribute(Attributes.MAX_HEALTH).getBaseValue()) : -20;
    }

    public boolean isBannable(){
        double hpDifference = ((Integer) getValue(LSConstants.HEALTH_DIFFERENCE)).doubleValue();
        double hpDifferenceNeeded = getHPDifferenceRequiredForBan();
        return hpDifference <= hpDifferenceNeeded;
    }

    @Override
    public void tick(){
        if(!this.livingEntity.level().isClientSide){
            if(isBannable()){
                if (this.livingEntity instanceof ServerPlayer serverPlayer) {
                    setValue(LSConstants.HEALTH_DIFFERENCE, LifeSteal.config.startingHealthDifference.get());
                    setValue(LSConstants.TIME_KILLED, System.currentTimeMillis());
                    refreshHealth(true);
                    MinecraftServer server = this.livingEntity.level().getServer();

                    MutableComponent deadcomponent = Component.translatable("bannedmessage.lifesteal.lost_max_hearts");

                    if(serverPlayer.isDeadOrDying())
                        serverPlayer.getInventory().dropAll();

                    if (LifeSteal.config.playersSpawnHeadUponDeath.get() && LSUtil.isMultiplayer(server, false)) {
                        BlockPos blockPos = spawnPlayerHead();
                        if(blockPos == null){
                            dropPlayerHead();
                        } else {
                            MutableComponent compPos = Component.translatable("bannedmessage.lifesteal.revive_head_location", blockPos.getX(), blockPos.getY(), blockPos.getZ());
                            deadcomponent = LSUtil.addComponents(deadcomponent, compPos);
                        }
                    }

                    if(LifeSteal.config.deathDuration.get() > 0){
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(new Date((long)getValue(LSConstants.TIME_KILLED) + LifeSteal.config.deathDuration.get()));
                        int AM_PM = instance.get(Calendar.AM_PM);
                        String formatAMPM;
                        if(AM_PM == Calendar.AM)
                            formatAMPM = "AM";
                        else
                            formatAMPM = "PM";

                        MutableComponent compPos = Component.translatable(
                                "bannedmessage.lifesteal.auto_revive_time",
                                instance.get(Calendar.HOUR)+":"+instance.get(Calendar.MINUTE)+" "+formatAMPM+", "+(instance.get(Calendar.MONTH)+1) +"/"+instance.get(Calendar.DATE)+"/"+instance.get(Calendar.YEAR));
                        deadcomponent = LSUtil.addComponents(deadcomponent, compPos);
                    }

                    if (LSUtil.isMultiplayer(server, true) && LifeSteal.config.uponDeathBanned.get() && !server.getPlayerList().getBans().isBanned(serverPlayer.getGameProfile())) {
                        UserBanList userbanlist = server.getPlayerList().getBans();
                        serverPlayer.getGameProfile();
                        GameProfile gameprofile = serverPlayer.getGameProfile();

                        UserBanListEntry userbanlistentry = new UserBanListEntry(gameprofile, null, LifeSteal.MOD_ID, null, deadcomponent == null ? null : deadcomponent.getString());
                        userbanlist.add(userbanlistentry);

                        if (serverPlayer != null) {
                            serverPlayer.connection.disconnect(deadcomponent);
                        }
                    } else{
                        if (!serverPlayer.isSpectator()){
                            serverPlayer.setGameMode(GameType.SPECTATOR);
                        }
                        this.livingEntity.sendSystemMessage(deadcomponent);
                    }
                }
            }
        }
    }

    @Override
    public void addOrUpdateHealthModifier(){
        AttributeInstance attribute = this.livingEntity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier modifier = new AttributeModifier(LSConstants.HEALTH_MODIFIER.toString(), ((Integer) this.getValue(LSConstants.HEALTH_DIFFERENCE)).doubleValue(), AttributeModifier.Operation.ADDITION);
        UUID modifierId = this.getValue(LSConstants.STORED_MODIFIER_UUID);
        if(modifierId != null){
            AttributeModifier toRemove = attribute.getModifier(modifierId);
            if(toRemove != null && toRemove.getName().equals(LSConstants.HEALTH_MODIFIER.toString())){
                attribute.removeModifier(toRemove);
            }
        }
        this.setValue(LSConstants.STORED_MODIFIER_UUID, modifier.getId());
        attribute.addPermanentModifier(modifier);
    }

    @Override
    public void refreshHealth(boolean healtoMax) {

        if (!this.livingEntity.level().isClientSide) {
            final int defaultHealthDifference = LifeSteal.config.startingHealthDifference.get();
            final int maximumHealthGainable = LifeSteal.config.maximumHealthGainable.get();
            final int maximumHealthLoseable = LifeSteal.config.maximumHealthLoseable.get();

            int healthDifference = getValue(LSConstants.HEALTH_DIFFERENCE);

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

            setValue(LSConstants.HEALTH_DIFFERENCE, healthDifference);
            addOrUpdateHealthModifier();

            if (healthDifference >= 20 && this.livingEntity instanceof ServerPlayer serverPlayer) {
                LSCriteria.GET_10_MAX_HEARTS.trigger(serverPlayer);
            }

            if (this.livingEntity.getHealth() > this.livingEntity.getMaxHealth() || healtoMax) {
                this.livingEntity.setHealth(this.livingEntity.getMaxHealth());
            }
        }

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(LSConstants.HEALTH_DIFFERENCE.getPath(), getValue(LSConstants.HEALTH_DIFFERENCE));
        tag.putLong(LSConstants.TIME_KILLED.getPath(), getValue(LSConstants.TIME_KILLED));
        tag.putUUID(LSConstants.STORED_MODIFIER_UUID.getPath(), getValue(LSConstants.STORED_MODIFIER_UUID));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setValue(LSConstants.HEALTH_DIFFERENCE, tag.getInt(LSConstants.HEALTH_DIFFERENCE.getPath()));
        setValue(LSConstants.TIME_KILLED, tag.getLong(LSConstants.TIME_KILLED.getPath()));
        setValue(LSConstants.STORED_MODIFIER_UUID, tag.getUUID(LSConstants.STORED_MODIFIER_UUID.getPath()));
    }
}