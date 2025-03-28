package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.platform.services.IPlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

import java.util.Collection;

public class FabricPlatformHelper implements IPlatformHelper {
    public static MinecraftServer MINECRAFT_SERVER;

    public boolean isProduction() {
        return !FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public Collection<String> getModIds() {
        return FabricLoader.getInstance().getAllMods().stream()
                .map(ModContainer::getMetadata)
                .map(ModMetadata::getId).toList();
    }

    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    public MinecraftServer getServer() {
        MinecraftServer server;
        if (isClient()) {
            server = getServerFromClient();
        } else {
            server = MINECRAFT_SERVER;
        }
        return server;
    }

    public void init() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> MINECRAFT_SERVER = server);
    }

    @Environment(EnvType.CLIENT)
    private MinecraftServer getServerFromClient() {
        return Minecraft.getInstance().getSingleplayerServer();
    }

}
