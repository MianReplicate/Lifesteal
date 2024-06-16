package net.goose.lifesteal.fabric.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.data.HealthData;

public class ModEvents {
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModEvents for " + LifeSteal.MOD_ID);
        ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> HealthData.get(oldPlayer).ifPresent(oldData -> HealthData.get(newPlayer).ifPresent(newData ->
        {
            newData.setHeartDifference(oldData.getHeartDifference());
            newData.refreshHearts(true);
        }))));

        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> {
            if(player.isCreative() && blockEntity instanceof ReviveSkullBlockEntity reviveEntity){
                reviveEntity.setDestroyed(true);
            }
            return true;
        }));
    }
}
