package mc.mian.lifesteal.command;

import com.mojang.brigadier.CommandDispatcher;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.command.commands.LifestealCommand;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

public class LSCommands {
    public static void registerCommands(Consumer<Consumer<CommandDispatcher<CommandSourceStack>>> command) {
        LSConstants.LOGGER.debug("Registering ModCommands for " + LSConstants.MOD_ID);
        command.accept(LifestealCommand::register);
    }
}
