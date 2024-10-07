package mc.mian.lifesteal.fabric.event;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import mc.mian.lifesteal.command.LSCommands;

public class CommandRegistry {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> LSCommands.registerCommands(command -> command.accept(dispatcher)));
    }
}
