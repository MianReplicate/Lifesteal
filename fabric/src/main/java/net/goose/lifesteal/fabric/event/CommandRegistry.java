package net.goose.lifesteal.fabric.event;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.goose.lifesteal.command.ModCommands;

public class CommandRegistry {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry) -> ModCommands.registerCommands(command -> command.accept(dispatcher)));
    }
}
