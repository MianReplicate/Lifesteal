package net.goose.lifesteal.command;

import com.mojang.brigadier.CommandDispatcher;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.command.commands.LifestealCommand;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

public class ModCommands {
    public static void registerCommands(Consumer<Consumer<CommandDispatcher<CommandSourceStack>>> command) {
        LifeSteal.LOGGER.debug("Registering ModCommands for " + LifeSteal.MOD_ID);
        command.accept(LifestealCommand::register);
    }
}
