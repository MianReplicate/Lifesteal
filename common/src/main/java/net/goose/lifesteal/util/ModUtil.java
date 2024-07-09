package net.goose.lifesteal.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.PlayerImpl;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.LifestealData;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
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
import java.util.*;
import java.util.function.BiFunction;

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

    public static ImmutableMap<GameProfile, KilledType> getDeadPlayers(MinecraftServer server){
        ImmutableMap.Builder<GameProfile, KilledType> builder = ImmutableMap.builder();
        PlayerList playerList = server.getPlayerList();
        List<ServerPlayer> serverPlayers = playerList.getPlayers();

        UserBanList userBanList = playerList.getBans();
        userBanList.getEntries().forEach(entry -> {
            if(entry.getSource().matches(LifeSteal.MOD_ID)){
                builder.put(entry.getUser(), KilledType.BANNED);
            }
        });

        serverPlayers.forEach(entry -> {
            if(entry.gameMode.getGameModeForPlayer() == GameType.SPECTATOR){
                if((long) LifestealData.get(entry).get().getValue(ModResources.TIME_KILLED) > 0L){
                    builder.put(entry.getGameProfile(), KilledType.SPECTATOR);
                }
            }
        });
        ImmutableMap<GameProfile, KilledType> map = builder.build();
        return map;
    }

    @ExpectPlatform
    public static CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function){
        throw new AssertionError("i just fucked your mom hewehhehehehehhehe");
    }

    @ExpectPlatform
    public static <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function){
        throw new AssertionError("i just fucked your mom hewehhehehehehhehe");
    }

    public static List<GameProfile> getGameProfiles(MinecraftServer server, boolean includedSaved){
        ArrayList<GameProfile> gameProfiles = new ArrayList<>();
        server.getPlayerList().getPlayers().forEach(player -> {
            gameProfiles.add(player.getGameProfile());
        });
        if(includedSaved){
            LevelStorageSource.LevelStorageAccess levelStorageAccess = server.storageSource;
            File playerDir = levelStorageAccess.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
            Arrays.stream(playerDir.listFiles()).toList().forEach(file -> {
                String[] splitString = file.getName().split(".dat");
                GameProfile gameProfile = server.getProfileCache().get(UUID.fromString(splitString[0])).get();
                if(!gameProfiles.contains(gameProfile)){
                    gameProfiles.add(gameProfile);
                }
            });
        }
        return gameProfiles;
    }

    public static CompoundTag getPlayerData(MinecraftServer server, GameProfile gameProfile){
        try{
            LevelStorageSource.LevelStorageAccess levelStorageAccess = server.storageSource;
            File playerDir = levelStorageAccess.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
            String uuidString = gameProfile.getId().toString();
            File file = new File(playerDir, uuidString + ".dat");
            if (file.exists() && file.isFile())
            {
                CompoundTag tag = NbtIo.readCompressed(file);
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
            NbtIo.writeCompressed(compoundTag, path2.toFile());
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
            if(index == components.length - 1) {
                currentComponent.append(" ");
                currentComponent.append(components[index]);
            }
        }
        return currentComponent;
    }

    public static boolean revivePlayer(ServerLevel level, BlockPos reviveAt, GameProfile profileToUnban, boolean enableLightningEffect, boolean silentRevive, @Nullable Player optionalReviver) {
        boolean successful = false;

        MinecraftServer server = level.getServer();
        ServerPlayer serverPlayer = server.getPlayerList().getPlayer(profileToUnban.getId());
        UserBanList userBanList = server.getPlayerList().getBans();

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
            successful = true;
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
        CompoundTag compoundTag = itemStack.getOrCreateTagElement(LifeSteal.MOD_ID);
        compoundTag.putBoolean(ModResources.RIPPED, true);
        compoundTag.putBoolean(ModResources.UNFRESH, true);
        itemStack.setHoverName(Component.translatable("item.lifesteal.heart_crystal.named", killedPlayer.getName().getString()));

        ServerPlayer serverPlayer = (ServerPlayer) killedPlayer;
        serverPlayer.drop(itemStack, true, false);
    }

    public static boolean isMultiplayer(MinecraftServer server, boolean excludeLan){
        return (!server.isSingleplayer() || (!excludeLan && server.isPublished()));
    }
}
