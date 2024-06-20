package net.goose.lifesteal.util;

import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.PlayerImpl;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.common.component.ModDataComponents;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserBanList;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModUtil {
    public enum KilledType{
        BANNED, SPECTATOR
    }

    public static ListTag newDoubleList(double... numbers) {
        ListTag listTag = new ListTag();
        double[] var3 = numbers;
        int var4 = numbers.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            double d = var3[var5];
            listTag.add(DoubleTag.valueOf(d));
        }

        return listTag;
    }

    public static HashMap<GameProfile, KilledType> getDeadPlayers(MinecraftServer server, boolean includeBanned, boolean includeSpectators){
        HashMap<GameProfile, KilledType> map = new HashMap<>(Integer.MAX_VALUE);
        PlayerList playerList = server.getPlayerList();
        List<ServerPlayer> serverPlayers = playerList.getPlayers();

        if(includeBanned){
            UserBanList userBanList = playerList.getBans();
            userBanList.getEntries().forEach(entry -> {
                if(entry.getSource().matches(LifeSteal.MOD_ID)){
                    map.put(entry.getUser(), KilledType.BANNED);
                }
            });
        }

        if(includeSpectators){
            serverPlayers.forEach(entry -> {
                if(entry.gameMode.getGameModeForPlayer() == GameType.SPECTATOR){
                    CompoundTag tag = ModUtil.getPlayerData(server, entry.getGameProfile());
                    if(tag != null && tag.getLong("TimeKilled") > 0){
                        map.put(entry.getGameProfile(), KilledType.SPECTATOR);
                    }
                }
            });
        }

        return map;
    }

    public static CompoundTag getPlayerData(MinecraftServer server, GameProfile gameProfile){
        try{
            LifeSteal.LOGGER.info(1);
            LevelStorageSource.LevelStorageAccess levelStorageAccess = server.storageSource;
            LifeSteal.LOGGER.info(2);
            File playerDir = levelStorageAccess.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
            LifeSteal.LOGGER.info(3);
            String uuidString = gameProfile.getId().toString();
            LifeSteal.LOGGER.info(4);
            File file = new File(playerDir, uuidString + ".dat");
            LifeSteal.LOGGER.info(5);
            if (file.exists() && file.isFile())
            {
                LifeSteal.LOGGER.info(6);
                CompoundTag tag = NbtIo.readCompressed(file.toPath(), NbtAccounter.unlimitedHeap());
                LifeSteal.LOGGER.info(7);
                return tag;
            } else {
                throw new Exception("Soooo we couldn't get their data whomp whomp");
            }
        } catch (Exception var4){
            LifeSteal.LOGGER.warn("Failed to retrieve " + gameProfile.getName()+"'s data");
        }
        return null;
    }

    public static boolean savePlayerData(MinecraftServer server, GameProfile gameProfile, CompoundTag compoundTag) {
        try {
            LevelStorageSource.LevelStorageAccess levelStorageAccess = server.storageSource;
            File playerDir = levelStorageAccess.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
            String uuidString = gameProfile.getId().toString();

            Path path = playerDir.toPath();
            Path path2 = Files.createTempFile(path, uuidString + "-", ".dat");
            NbtIo.writeCompressed(compoundTag, path2);
            Path path3 = path.resolve(uuidString + ".dat");
            Path path4 = path.resolve(uuidString + ".dat_old");
            Util.safeReplaceFile(path3, path2, path4);
            return true;
        } catch(Exception var4) {
            LifeSteal.LOGGER.warn("Failed to save "+ gameProfile.getName() + "'s data");
        }
        return false;
    }

    private static boolean revivePlayerAtPos(MinecraftServer server, GameProfile gameProfile, BlockPos respawnPos, Level respawnLevel)
    {
        try
        {
            LifeSteal.LOGGER.info("Attempting to set position and revive " + gameProfile.getName());
            CompoundTag compoundTag = getPlayerData(server, gameProfile);

            int i = NbtUtils.getDataVersion(compoundTag, -1);
            compoundTag = DataFixTypes.PLAYER.updateToCurrentVersion(server.getFixerUpper(), compoundTag, i);

            compoundTag.remove("TimeKilled");
            compoundTag.putBoolean("Revived", true);
            compoundTag.put("Pos", newDoubleList(respawnPos.getX(), respawnPos.getY(), respawnPos.getZ()));
            compoundTag.putString("Dimension", respawnLevel.dimension().location().getPath());

            if(savePlayerData(server, gameProfile, compoundTag)){
                LifeSteal.LOGGER.info("Successfully set position and revived " + gameProfile.getName());
                return true;
            }

            throw new Exception("somehow it fucked up");
        } catch (Exception var4)
        {
            LifeSteal.LOGGER.warn("Failed to set position and revive " + gameProfile.getName());
        }
        return false;
    }

    public static MutableComponent addComponents(MutableComponent... components){
        MutableComponent currentComponent = components[0];
        for(int index = 1; index < components.length; index++) {
            if(index == components.length - 1)
                currentComponent.append(components[index]);
        }
        return currentComponent;
    }

    public static boolean revivePlayer(ServerLevel level, BlockPos reviveAt, GameProfile profileToUnban, boolean enableLightningEffect, boolean silentRevive, @Nullable Player optionalReviver) {
        boolean successful = false;

        ServerPlayer serverPlayer = (ServerPlayer) level.getPlayerByUUID(profileToUnban.getId());
        UserBanList userBanList = level.getServer().getPlayerList().getBans();

        if(userBanList.isBanned(profileToUnban)){
            userBanList.remove(profileToUnban);
        }

        if (serverPlayer == null) {
            if(revivePlayerAtPos(level.getServer(), profileToUnban, reviveAt, level))
            {
                successful = true;
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
