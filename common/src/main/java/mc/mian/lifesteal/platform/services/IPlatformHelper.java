package mc.mian.lifesteal.platform.services;

import net.minecraft.server.MinecraftServer;

import java.util.Collection;

public interface IPlatformHelper {
    boolean isProduction();
    boolean isModLoaded(String id);
    Collection<String> getModIds();
    MinecraftServer getServer();
    boolean isClient();
    boolean isServer();
}