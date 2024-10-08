package mc.mian.lifesteal.fabric.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import mc.mian.lifesteal.data.LSData;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public class LSEvents {
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModEvents for " + LifeSteal.MOD_ID);
        ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> LSData.get(oldPlayer).ifPresent(oldData -> LSData.get(newPlayer).ifPresent(newData ->
        {
            Collection<ResourceLocation> keys = newData.getKeys();
            keys.forEach(key -> newData.setValue(key, oldData.getValue(key)));
            newData.refreshHealth(!alive);
        }))));
    }
}
