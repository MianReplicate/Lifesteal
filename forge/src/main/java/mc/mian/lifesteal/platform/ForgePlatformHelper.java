package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.platform.services.IPlatformHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Collection;

public class ForgePlatformHelper implements IPlatformHelper {
    public boolean isProduction() {
        return FMLLoader.isProduction();
    }

    public boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public Collection<String> getModIds() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }

    public boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    public boolean isServer() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
