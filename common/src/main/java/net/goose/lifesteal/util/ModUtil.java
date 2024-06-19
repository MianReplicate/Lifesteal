package net.goose.lifesteal.util;

import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.PlayerImpl;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.common.component.ModDataComponents;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.HealthData;
import net.goose.lifesteal.data.PlayerLocationData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class ModUtil {
    public static MutableComponent addComponents(MutableComponent... components){
        MutableComponent currentComponent = components[0];
        for(int index = 1; index < components.length; index++) {
            if(index == components.length - 1)
                currentComponent.append(components[index]);
        }
        return currentComponent;
    }

    public static boolean revivePlayer(ServerLevel level, BlockPos reviveAt, GameProfile profileToUnban, boolean enableLightningEffect, boolean silentRevive, @Nullable Player optionalReviver, @Nullable UserBanList userbanList) {
        boolean successful = false;

        ServerPlayer serverPlayer = (ServerPlayer) level.getPlayerByUUID(profileToUnban.getId());

        if (serverPlayer == null) {
            if (userbanList != null) {
                if (userbanList.isBanned(profileToUnban)) {
                    if (userbanList.get(profileToUnban).getSource().matches(LifeSteal.MOD_ID)) {
                        if(PlayerLocationData.saveNewLocation(level.getServer(), profileToUnban, reviveAt, level))
                        {
                            successful = true;
                            userbanList.remove(profileToUnban);
                        }
                    }
                }
            }
        } else {
            serverPlayer.teleportTo(level, reviveAt.getX(), reviveAt.getY(), reviveAt.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
            ((PlayerImpl) serverPlayer).setRevived(true);
            LivingEntity livingEntity = (LivingEntity) serverPlayer.getCamera();

            if (livingEntity != null) {
                successful = true;
                HealthData.get(livingEntity).ifPresent(HealthData::tryRevivalEffects);
            }
        }

        if (successful) {
            if(level.getBlockEntity(reviveAt) instanceof ReviveSkullBlockEntity skullBlockEntity){
                skullBlockEntity.setDestroyed(true);
                level.removeBlock(reviveAt, true);
            }
            if (enableLightningEffect) {
                Entity entity = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                Vec3 vec3 = new Vec3(reviveAt.getX(), reviveAt.getY(), reviveAt.getZ());
                entity.setPos(vec3);
                level.addFreshEntity(entity);
            }

            if (!silentRevive) {
                Component component = Component.translatable("chat.message.lifesteal.revived_player", profileToUnban.getName()).withStyle(ChatFormatting.YELLOW);
                level.getServer().getPlayerList().broadcastSystemMessage(component, false);
            } else if(optionalReviver != null) {
                optionalReviver.displayClientMessage(Component.translatable("gui.lifesteal.revived"), true);
            }
        }

        return successful;
    }

    public static void ripHeartCrystalFromPlayer(LivingEntity killedPlayer) {
        ItemStack itemStack = new ItemStack(ModItems.HEART_CRYSTAL.get());
        itemStack.set(ModDataComponents.RIPPED.get(), true);
        itemStack.set(ModDataComponents.UNFRESH.get(), true);
        itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.lifesteal.heart_crystal.named", killedPlayer.getName().getString()));

        ServerPlayer serverPlayer = (ServerPlayer) killedPlayer;
        serverPlayer.drop(itemStack, true, false);
    }
}
