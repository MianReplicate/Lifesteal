package mc.mian.lifesteal;

import fuzs.forgeconfigapiport.neoforge.api.v5.ForgeConfigRegistry;
import mc.mian.lifesteal.common.advancement.LSCriteria;
import mc.mian.lifesteal.common.configuration.ConfigHolder;
import mc.mian.lifesteal.data.LSDataAttachments;
import mc.mian.lifesteal.datagen.LSDataGenerators;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(LSConstants.MOD_ID)
public class LifestealNeoForge {
    public static IEventBus modEventBus;
    public static IEventBus commonEventBus = NeoForge.EVENT_BUS;

    public LifestealNeoForge(IEventBus modEventBusParam) {
        modEventBus = modEventBusParam;
        modEventBus.addListener(this::registerEvent);

        ForgeConfigRegistry.INSTANCE.register(LSConstants.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();

        LSDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        modEventBus.register(LSDataGenerators.class);
    }

    public static void registerCriteria(RegisterEvent registerEvent){
        LSConstants.LOGGER.debug("Initializing ModCriteria for " + LSConstants.MOD_ID);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.GET_10_MAX_HEARTS, () ->
                LSCriteria.GET_10_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS, () ->
                LSCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.BACK_FROM_THE_DEAD, () ->
                LSCriteria.BACK_FROM_THE_DEAD);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), LSConstants.REVIVED, () ->
                LSCriteria.REVIVED);
    }

    @SubscribeEvent
    public void registerEvent(RegisterEvent registerEvent){
        // NeoForge is special so we have to register criteria this way
        registerCriteria(registerEvent);
    }
}