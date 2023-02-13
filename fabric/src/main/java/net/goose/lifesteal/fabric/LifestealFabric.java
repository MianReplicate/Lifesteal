package net.goose.lifesteal.fabric;

import net.fabricmc.api.ModInitializer;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ConfigHolder;
import net.goose.lifesteal.fabric.event.CommandRegistry;
import net.goose.lifesteal.fabric.event.ModEvents;
import net.goose.lifesteal.world.feature.ModBiomeModifier;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class LifestealFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModLoadingContext.registerConfig(LifeSteal.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
        ModEvents.register();
        CommandRegistry.register();
        ModBiomeModifier.register();
    }
}