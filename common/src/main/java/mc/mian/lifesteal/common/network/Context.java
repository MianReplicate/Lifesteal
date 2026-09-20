package mc.mian.lifesteal.common.network;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public record Context(Side side, ServerPlayer sender, MinecraftServer server) {
}
