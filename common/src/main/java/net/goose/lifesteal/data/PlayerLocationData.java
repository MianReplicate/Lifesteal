package net.goose.lifesteal.data;

import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.LifeSteal;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayerLocationData
{
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
    public static boolean saveNewLocation(MinecraftServer server, GameProfile gameProfile, BlockPos respawnPos, Level respawnLevel)
    {
        try
        {
            LifeSteal.LOGGER.info("Attempting to save new location for " + gameProfile.getName());
            LevelStorageSource.LevelStorageAccess levelStorageAccess = server.storageSource;
            File playerDir = levelStorageAccess.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
            String uuidString = gameProfile.getId().toString();
            CompoundTag compoundTag;

            File file = new File(playerDir, uuidString + ".dat");
            if (file.exists() && file.isFile())
            {
                compoundTag = NbtIo.readCompressed(file.toPath(), NbtAccounter.unlimitedHeap());

                int i = NbtUtils.getDataVersion(compoundTag, -1);
                compoundTag = DataFixTypes.PLAYER.updateToCurrentVersion(server.getFixerUpper(), compoundTag, i);

                compoundTag.putBoolean("Revived", true);
                compoundTag.put("Pos", newDoubleList(respawnPos.getX(), respawnPos.getY(), respawnPos.getZ()));
                compoundTag.putString("Dimension", respawnLevel.dimension().location().getPath());

                Path path = playerDir.toPath();
                Path path2 = Files.createTempFile(path, uuidString+ "-", ".dat");
                NbtIo.writeCompressed(compoundTag, path2);
                Path path3 = path.resolve(uuidString + ".dat");
                Path path4 = path.resolve(uuidString + ".dat_old");
                Util.safeReplaceFile(path3, path2, path4);

                LifeSteal.LOGGER.info("Successfully saved new location data for " + gameProfile.getName());
                return true;
            } else
            {
                LifeSteal.LOGGER.warn(gameProfile.getName() + " doesn't have any data to grab?? This may be a bug.. oh no");
            }
        } catch (Exception var4)
        {
            LifeSteal.LOGGER.warn("Failed to save new location onto " + gameProfile.getName());
        }
        return false;
    }
}
