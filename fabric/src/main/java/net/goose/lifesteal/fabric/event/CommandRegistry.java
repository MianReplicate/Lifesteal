package net.goose.lifesteal.fabric.event;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.goose.lifesteal.command.ModCommands;

public class CommandRegistry {
    public static void register(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> ModCommands.registerCommands(command -> command.accept(dispatcher)));
    }
}
