package net.goose.lifesteal.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ConfigHolder;
import net.goose.lifesteal.fabric.event.CommandRegistry;
import net.goose.lifesteal.fabric.event.ModEvents;
import net.goose.lifesteal.util.ModResources;
import net.goose.lifesteal.world.gen.ModBiomeModifier;
import net.minecraft.advancements.CriteriaTriggers;
import net.neoforged.fml.config.ModConfig;

import static net.goose.lifesteal.advancement.ModCriteria.*;

public class LifestealFabric implements ModInitializer {

    public static void registerCriteria(){
        LifeSteal.LOGGER.debug("Initializing ModCriteria for " + LifeSteal.MOD_ID);
        CriteriaTriggers.register(ModResources.GET_10_MAX_HEARTS.toString(), GET_10_MAX_HEARTS);
        CriteriaTriggers.register(ModResources.USE_TOTEM_WHILE_20_MAX_HEARTS.toString(), USE_TOTEM_WHILE_20_MAX_HEARTS);
        CriteriaTriggers.register(ModResources.REVIVED.toString(), REVIVED);
    }
    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(LifeSteal.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
        ModEvents.register();
        CommandRegistry.register();
        ModBiomeModifier.register();
        registerCriteria();
    }
}