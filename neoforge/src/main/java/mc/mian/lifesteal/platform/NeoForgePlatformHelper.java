package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.platform.services.IPlatformHelper;
import net.minecraft.server.MinecraftServer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.Collection;

public class NeoForgePlatformHelper implements IPlatformHelper {
    public boolean isProduction() {
        return FMLLoader.getCurrent().isProduction();
    }

    public boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public Collection<String> getModIds() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }

    public boolean isClient() {
        return FMLEnvironment.getDist() == Dist.CLIENT;
    }

    public boolean isServer() {
        return FMLEnvironment.getDist() == Dist.DEDICATED_SERVER;
    }

    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
