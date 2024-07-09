package net.goose.lifesteal.fabric.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.data.LifestealData;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public class ModEvents {
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModEvents for " + LifeSteal.MOD_ID);
        ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> LifestealData.get(oldPlayer).ifPresent(oldData -> LifestealData.get(newPlayer).ifPresent(newData ->
        {
            Collection<ResourceLocation> keys = newData.getKeys();
            keys.forEach(key -> newData.setValue(key, oldData.getValue(key)));
            newData.refreshHealth(!alive);
        }))));

        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> {
            if(player.isCreative() && blockEntity instanceof ReviveSkullBlockEntity reviveEntity){
                reviveEntity.setDestroyed(true);
            }
            return true;
        }));
    }
}
