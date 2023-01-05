package net.goose.lifesteal.event;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.goose.lifesteal.LifeSteal;

public class ModCommands {
    public static void register(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> net.goose.lifesteal.command.ModCommands.registerCommands(command -> command.accept(dispatcher)));
    }
}
