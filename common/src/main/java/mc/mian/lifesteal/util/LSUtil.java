package mc.mian.lifesteal.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import commonnetwork.api.Dispatcher;
import dev.architectury.injectables.annotations.ExpectPlatform;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.api.PlayerImpl;
import mc.mian.lifesteal.common.component.LSDataComponents;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.network.custom.HeartGainedPacket;
import mc.mian.lifesteal.data.LSData;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserBanList;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;

public class LSUtil {
    public static ResourceLocation modLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(LSConstants.MOD_ID, name);
    }
    public static ResourceKey<PlacedFeature> createPlacedFeature(String domain, String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }
    public static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeature(String domain, String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

    public static ResourceKey<LootTable> createLootTable(String domain, String name){
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

    public static ResourceKey<StructureTemplatePool> createTemplatePool(String domain, String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

    public static ResourceKey<StructureSet> createStructureSet(String domain, String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

    public static ResourceKey<Structure> createStructure(String domain, String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

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
            if(entry.getSource().matches(LSConstants.MOD_ID)){
                builder.put(entry.getUser(), KilledType.BANNED);
            }
        });

        serverPlayers.forEach(entry -> {
            if(entry.gameMode.getGameModeForPlayer() == GameType.SPECTATOR){
                if((long) LSData.get(entry).get().getValue(LSConstants.TIME_KILLED) > 0L){
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
        server.getPlayerList().getPlayers().forEach(player -> gameProfiles.add(player.getGameProfile()));
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
                CompoundTag tag = NbtIo.readCompressed(file.toPath(), NbtAccounter.unlimitedHeap());
                return tag;
            } else {
                throw new Exception("Soooo we couldn't get their data whomp whomp");
            }
        } catch (Exception var4){
            LSConstants.LOGGER.warn("Failed to retrieve " + gameProfile.getName()+"'s data");
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
            LSConstants.LOGGER.warn("Failed to save "+ gameProfile.getName() + "'s data");
        }
        return false;
    }

    private static boolean revivePlayerAtPos(MinecraftServer server, GameProfile gameProfile, BlockPos respawnPos, Level respawnLevel)
    {
        try
        {
            LSConstants.LOGGER.info("Attempting to set position and revive " + gameProfile.getName());
            CompoundTag compoundTag = getPlayerData(server, gameProfile);

            int i = NbtUtils.getDataVersion(compoundTag, -1);
            compoundTag = DataFixTypes.PLAYER.updateToCurrentVersion(server.getFixerUpper(), compoundTag, i);

            compoundTag.putBoolean("Revived", true);
            compoundTag.put("Pos", newDoubleList(respawnPos.getX(), respawnPos.getY(), respawnPos.getZ()));
            compoundTag.putString("Dimension", respawnLevel.dimension().location().getPath());

            if(savePlayerData(server, gameProfile, compoundTag)){
                LSConstants.LOGGER.info("Successfully set position and revived " + gameProfile.getName());
                return true;
            }

            throw new Exception("somehow it fucked up");
        } catch (Exception var4)
        {
            LSConstants.LOGGER.warn("Failed to set position and revive " + gameProfile.getName());
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
            serverPlayer.teleportTo(level, reviveAt.getX(), reviveAt.getY(), reviveAt.getZ(), Relative.ROTATION, serverPlayer.getYRot(), serverPlayer.getXRot(), true);
            ((PlayerImpl) serverPlayer).setRevived(true);
            successful = true;
        }

        if (successful) {
            if(level.getBlockEntity(reviveAt) instanceof SkullBlockEntity skullBlockEntity){
                IndestructibleUtil.addToPendingRemoval(skullBlockEntity.getBlockState());
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
        ItemStack itemStack = new ItemStack(LSItems.HEART_CRYSTAL.get());
        itemStack.set(LSDataComponents.RIPPED.get(), true);
        itemStack.set(LSDataComponents.UNFRESH.get(), true);
        itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.lifesteal.heart_crystal.named", killedPlayer.getName().getString()));

        ServerPlayer serverPlayer = (ServerPlayer) killedPlayer;
        serverPlayer.drop(itemStack, true, false);
    }

    public static boolean isMultiplayer(MinecraftServer server, boolean excludeLan){
        return (!server.isSingleplayer() || (!excludeLan && server.isPublished()));
    }

    public static void gainHealth(LivingEntity livingEntity, Integer health){
        if(livingEntity.level().isClientSide) return;

        LSData.get(livingEntity).ifPresent(lsData -> {
            int newheartDifference = health != null ? health : (int) lsData.getValue(LSConstants.HEALTH_DIFFERENCE) + LifeSteal.config.heartCrystalAmountGain.get();

            lsData.setValue(LSConstants.HEALTH_DIFFERENCE,newheartDifference);
            lsData.refreshHealth(false);

            if(livingEntity instanceof ServerPlayer serverPlayer)
                Dispatcher.sendToClient(new HeartGainedPacket(), serverPlayer);
        });
    }
}
