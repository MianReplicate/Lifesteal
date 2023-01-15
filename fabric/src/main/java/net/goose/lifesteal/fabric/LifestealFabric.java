package net.goose.lifesteal.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ConfigHolder;
import net.goose.lifesteal.fabric.event.ModCommands;
import net.goose.lifesteal.fabric.event.ModEvents;
import net.goose.lifesteal.item.fabric.ModCreativeModeTab;
import net.goose.lifesteal.world.gen.ModBiomeModifier;
public class LifestealFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(LifeSteal.MOD_ID, net.minecraftforge.fml.config.ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
        ModEvents.register();
        ModCommands.register();
        ModBiomeModifier.register();
        ModCreativeModeTab.register();
    }
}