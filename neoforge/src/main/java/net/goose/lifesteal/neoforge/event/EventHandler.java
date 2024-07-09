package net.goose.lifesteal.neoforge.event;

import net.goose.lifesteal.command.ModCommands;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.data.neoforge.LifestealDataImpl;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@SuppressWarnings("unused")
public class EventHandler {
    @SubscribeEvent
    public static void OnCommandsRegister(final RegisterCommandsEvent event) {
        ModCommands.registerCommands(command -> command.accept(event.getDispatcher()));
    }

    @SubscribeEvent
    public static void playerDestroyEvent(final BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        if(player.isCreative() && player.level().getBlockEntity(event.getPos()) instanceof ReviveSkullBlockEntity blockEntity){
            blockEntity.setDestroyed(true);
        }
    }

    @SubscribeEvent
    public static void playerSpawnEvent(final PlayerEvent.PlayerRespawnEvent event){
        LifestealDataImpl.get(event.getEntity()).ifPresent(iLifestealData -> iLifestealData.refreshHealth(true));
    }
}
