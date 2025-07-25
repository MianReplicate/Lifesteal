package mc.mian.lifesteal;

import mc.mian.lifesteal.common.advancement.LSCriteria;
import mc.mian.lifesteal.common.configuration.ConfigHolder;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.lang.invoke.MethodHandles;

@Mod(LSConstants.MOD_ID)
public class LifestealForge {
    public static BusGroup modEventGroup = FMLJavaModLoadingContext.get().getModBusGroup();

    public LifestealForge() {
        modEventGroup.register(MethodHandles.lookup(), this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
    }

    public static void registerCriteria(RegisterEvent registerEvent){
        LSConstants.LOGGER.debug("Initializing ModCriteria for " + LSConstants.MOD_ID);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.GET_10_MAX_HEARTS, () -> LSCriteria.GET_10_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS, () -> LSCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.BACK_FROM_THE_DEAD, () -> LSCriteria.BACK_FROM_THE_DEAD);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.REVIVED, () -> LSCriteria.REVIVED);
    }

    @SubscribeEvent
    public static void registerEvent(RegisterEvent registerEvent){
        // NeoForge is special so we have to register criteria this way
        registerCriteria(registerEvent);
    }
}