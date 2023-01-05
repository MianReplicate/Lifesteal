package net.goose.lifesteal.command;

import com.mojang.brigadier.CommandDispatcher;
import net.goose.lifesteal.command.commands.LifestealCommand;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

public class ModCommands {
    public static void registerCommands(Consumer<Consumer<CommandDispatcher<CommandSourceStack>>> command) {
        command.accept(LifestealCommand::register);
    }
}
