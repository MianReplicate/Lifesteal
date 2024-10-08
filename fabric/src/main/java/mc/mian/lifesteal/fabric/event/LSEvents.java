package mc.mian.lifesteal.fabric.event;

import mc.mian.lifesteal.util.LSConstants;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import mc.mian.lifesteal.data.LSData;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public class LSEvents {
    public static void register() {
        LSConstants.LOGGER.debug("Registering ModEvents for " + LSConstants.MOD_ID);
        ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> LSData.get(oldPlayer).ifPresent(oldData -> LSData.get(newPlayer).ifPresent(newData ->
        {
            Collection<ResourceLocation> keys = newData.getKeys();
            keys.forEach(key -> newData.setValue(key, oldData.getValue(key)));
            newData.refreshHealth(!alive);
        }))));
    }
}
