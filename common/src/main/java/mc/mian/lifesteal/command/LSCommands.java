package mc.mian.lifesteal.command;

import com.mojang.brigadier.CommandDispatcher;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.command.commands.LSCommand;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

public class LSCommands {
    public static void registerCommands(Consumer<Consumer<CommandDispatcher<CommandSourceStack>>> command) {
        LifeSteal.LOGGER.debug("Registering ModCommands for " + LifeSteal.MOD_ID);
        command.accept(LSCommand::register);
    }
}
