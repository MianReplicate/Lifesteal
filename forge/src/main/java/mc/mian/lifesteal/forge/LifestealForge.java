package mc.mian.lifesteal.forge;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.configuration.ConfigHolder;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import static mc.mian.lifesteal.advancement.LSCriteria.*;

@Mod(LSConstants.MOD_ID)
public class LifestealForge {
    public static IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static IEventBus commonEventBus = MinecraftForge.EVENT_BUS;

    public LifestealForge() {
        modEventBus.addListener(this::registerEvent);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
    }

    public static void registerCriteria(RegisterEvent registerEvent){
        LSConstants.LOGGER.debug("Initializing ModCriteria for " + LSConstants.MOD_ID);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.GET_10_MAX_HEARTS, () -> GET_10_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS, () -> USE_TOTEM_WHILE_20_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.BACK_FROM_THE_DEAD, () -> BACK_FROM_THE_DEAD);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.REVIVED, () -> REVIVED);
    }

    @SubscribeEvent
    public void registerEvent(RegisterEvent registerEvent){
        // NeoForge is special so we have to register criteria this way
        registerCriteria(registerEvent);
    }
}