package net.goose.lifesteal.forge;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ConfigHolder;
import net.goose.lifesteal.data.forge.ModCapabilities;
import net.goose.lifesteal.data.forge.ModDataAttachments;
import net.goose.lifesteal.datagen.ModDataGenerators;
import net.goose.lifesteal.forge.event.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static net.goose.lifesteal.advancement.ModCriteria.*;

@Mod(LifeSteal.MOD_ID)
public class LifestealForge {
    public LifestealForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerEvent);
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();

        ModDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        eventBus.register(EventHandler.class);
        modEventBus.register(ModCapabilities.EventCapHandler.class);
        modEventBus.register(ModDataGenerators.class);

    }
}