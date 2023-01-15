package net.goose.lifesteal.forge;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ConfigHolder;
import net.goose.lifesteal.data.forge.ModCapabilities;
import net.goose.lifesteal.forge.event.EventHandler;
import net.goose.lifesteal.item.forge.ModCreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LifeSteal.MOD_ID)
public class LifestealForge {
    public LifestealForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(ModCreativeModeTab::register);
    }
}