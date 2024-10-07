package mc.mian.lifesteal.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.configuration.ConfigHolder;
import mc.mian.lifesteal.fabric.event.CommandRegistry;
import mc.mian.lifesteal.fabric.event.LSEvents;
import mc.mian.lifesteal.world.gen.LSBiomeModifier;
import net.minecraftforge.fml.config.ModConfig;

public class LifestealFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(LifeSteal.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
        LSEvents.register();
        CommandRegistry.register();
        LSBiomeModifier.register();
    }
}