package net.goose.lifesteal.common.item.custom;

import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.PlayerImpl;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.data.HealthData;
import net.goose.lifesteal.data.PlayerLocationData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserBanList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReviveCrystalItem extends Item {
    public ReviveCrystalItem(Properties properties) {
        super(properties);
    }

    public AtomicBoolean revivePlayer(ServerLevel level, BlockPos blockPos, GameProfile gameProfile, Player player, @Nullable UserBanList userBanList) {
        AtomicBoolean successful = new AtomicBoolean(false);

        ServerPlayer serverPlayer = (ServerPlayer) level.getPlayerByUUID(gameProfile.getId());

        if (serverPlayer == null) {
            if (userBanList != null) {
                if (userBanList.isBanned(gameProfile)) {
                    if (userBanList.get(gameProfile).getSource().matches(LifeSteal.MOD_ID)) {
                        if(PlayerLocationData.saveNewLocation(level.getServer(), gameProfile, blockPos, level))
                        {
                            successful.set(true);
                            userBanList.remove(gameProfile);
                        }
                    }
                }
            }
        } else {
            serverPlayer.teleportTo(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
            ((PlayerImpl) serverPlayer).setRevived(true);
            LivingEntity livingEntity = (LivingEntity) serverPlayer.getCamera();

            if (livingEntity != null) {
                successful.set(true);
                HealthData.get(livingEntity).ifPresent(HealthData::revivedTeleport);
            }
        }

        if (successful.get()) {
            ((ReviveSkullBlockEntity) level.getBlockEntity(blockPos)).setDestroyed(true);
            level.removeBlock(blockPos, true);
            if (!LifeSteal.config.disableLightningEffect.get()) {
                Entity entity = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                Vec3 vec3 = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                entity.setPos(vec3);
                level.addFreshEntity(entity);
            }

            if (!LifeSteal.config.silentlyRevivePlayer.get()) {
                Component component = Component.translatable("chat.message.lifesteal.revived_player", gameProfile.getName()).withStyle(ChatFormatting.YELLOW);
                level.getServer().getPlayerList().broadcastSystemMessage(component, false);
            } else {
                player.displayClientMessage(Component.translatable("gui.lifesteal.revived"), true);
            }
        }

        return successful;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        if (!useOnContext.getLevel().isClientSide) {
            Level level = useOnContext.getLevel();
            Player player = useOnContext.getPlayer();

            if (level.getServer().isSingleplayer()) {
                player.displayClientMessage(Component.translatable("gui.lifesteal.multiplayer_only"), true);
                return super.useOn(useOnContext);
            }

            if (LifeSteal.config.disableReviveCrystals.get()) {
                player.displayClientMessage(Component.translatable("gui.lifesteal.revive_crystal_disabled"), true);
                return super.useOn(useOnContext);
            }

            ItemStack itemStack = useOnContext.getItemInHand();
            BlockPos blockPos = useOnContext.getClickedPos();
            Block block = level.getBlockState(blockPos).getBlock();

            if (block == ModBlocks.REVIVE_HEAD.get() || block == ModBlocks.REVIVE_WALL_HEAD.get()) {
                ReviveSkullBlockEntity blockEntity = (ReviveSkullBlockEntity) level.getBlockEntity(blockPos);
                ResolvableProfile gameprofile = blockEntity.getOwnerProfile();

                if (gameprofile != null) {
                    UserBanList userBanList = level.getServer().getPlayerList().getBans();

                    AtomicBoolean successful = revivePlayer((ServerLevel) level, blockPos, gameprofile.gameProfile(), player, userBanList);
                    if (successful.get()) {
                        itemStack.shrink(1);
                    } else {
                        player.displayClientMessage(Component.translatable("gui.lifesteal.error_revive_block"), true);
                    }
                } else {
                    player.displayClientMessage(Component.translatable("gui.lifesteal.null_revive_block"), true);
                }
            } else {
                player.displayClientMessage(Component.translatable("gui.lifesteal.invaild_revive_block"), true);
            }
        }
        return super.useOn(useOnContext);
    }
}
