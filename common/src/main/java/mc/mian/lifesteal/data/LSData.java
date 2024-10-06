package mc.mian.lifesteal.data;

import com.mojang.authlib.GameProfile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.advancement.LSCriteria;
import mc.mian.lifesteal.api.ILifestealData;
import mc.mian.lifesteal.api.PlayerImpl;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.block.custom.ReviveHeadBlock;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LSData implements ILifestealData {
    private final LivingEntity livingEntity;
    public LSData(final LivingEntity entity) {
        this.livingEntity = entity;
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
                    playerHeadEntity.setOwner(new ResolvableProfile(serverPlayer.getGameProfile()));
                    level.setBlockEntity(playerHeadEntity);

                    BlockPos currentPos = playerHeadEntity.getBlockPos();
                    LSConstants.LOGGER.info(serverPlayer.getName().getString() + "'s revive head has been placed at" + " X: " + currentPos.getX() + " Y: " + currentPos.getY() + " Z: " + currentPos.getZ());

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
                itemStack.set(DataComponents.PROFILE, new ResolvableProfile(serverPlayer.getGameProfile()));
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
    public static Collection<ResourceLocation> getKeys(LSData lifestealData){
        throw new AssertionError("i just fucked your DAD hehehHAHAHAHAH");
    }

    @ExpectPlatform
    public static <T> T getValue(LSData lifestealData, ResourceLocation key) {
        throw new AssertionError("i just fucked your MOM MNAUDHAIUWHDIUAWHDIAUWD");
    }

    @ExpectPlatform
    public static <T> void setValue(LSData lifestealData, ResourceLocation key, T value) {
        throw new AssertionError("joe mama");
    }
    @Override
    public <T> T getValue(ResourceLocation key) {
        return getValue(this, key);
    }

    @Override
    public <T> void setValue(ResourceLocation key, T value) {
        if (!this.livingEntity.level().isClientSide) {
            setValue(this, key, value);
        }
    }

    // this is untested, i have no idea if it fuckin works at all lol. It works on Fabric (at least it seems) but idk about Neo cuz Neo no need it
    @Override
    public Collection<ResourceLocation> getKeys() {
        return getKeys(this);
    }

    // Returns the real amount of hitpoints a player has, includes every other mod's effect and ours.
    @Override
    public int getHealthModifiedTotal(boolean includeHealthDifference){
        AttributeInstance attribute = this.livingEntity.getAttribute(Attributes.MAX_HEALTH);
        AtomicInteger healthModifiedTotal = includeHealthDifference ?
                new AtomicInteger(getValue(LSConstants.HEALTH_DIFFERENCE)) :
                new AtomicInteger(0);

        attribute.getModifiers().forEach(modifier -> {
            if(!modifier.is(LSConstants.HEALTH_MODIFIER)){
                if (modifier.operation() == AttributeModifier.Operation.ADD_VALUE) {
                    double amount = modifier.amount();
                    healthModifiedTotal.addAndGet((int) Math.round(amount));
                } else if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    healthModifiedTotal.addAndGet((int) Math.round(this.livingEntity.getMaxHealth() * modifier.amount()));
                } else if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) {
                    healthModifiedTotal.addAndGet((int) Math.round(attribute.getBaseValue() * modifier.amount()));
                }
            }
        });

        return healthModifiedTotal.get();
    }

    // Returns the amount a player's HPDifference would have to be to get banned.
    @Override
    public int getHPDifferenceRequiredForBan(){
        int healthModified = this.getHealthModifiedTotal(false) + (int) this.livingEntity.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
        return -healthModified;
    }

    @Override
    public void killPlayerPermanently(){
        if(!this.livingEntity.level().isClientSide){
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

                    UserBanListEntry userbanlistentry = new UserBanListEntry(gameprofile, null, LSConstants.MOD_ID, null, deadcomponent == null ? null : deadcomponent.getString());
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

            AttributeInstance attribute = this.livingEntity.getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier modifier = new AttributeModifier(LSConstants.HEALTH_MODIFIER, healthDifference, AttributeModifier.Operation.ADD_VALUE);
            attribute.addOrReplacePermanentModifier(modifier);

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
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setValue(LSConstants.HEALTH_DIFFERENCE, tag.getInt(LSConstants.HEALTH_DIFFERENCE.getPath()));
        setValue(LSConstants.TIME_KILLED, tag.getLong(LSConstants.TIME_KILLED.getPath()));
    }
}