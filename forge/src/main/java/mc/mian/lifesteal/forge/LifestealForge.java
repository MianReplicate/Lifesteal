package mc.mian.lifesteal.forge;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.configuration.ConfigHolder;
import mc.mian.lifesteal.datagen.LSDataGenerators;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LifeSteal.MOD_ID)
public class LifestealForge {
    public static final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final IEventBus commonEventBus = MinecraftForge.EVENT_BUS;

    public LifestealForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();

        modEventBus.register(LSDataGenerators.class);
    }
}