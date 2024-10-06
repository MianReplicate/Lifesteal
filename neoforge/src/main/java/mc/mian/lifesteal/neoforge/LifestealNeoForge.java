package mc.mian.lifesteal.neoforge;

import fuzs.forgeconfigapiport.neoforge.api.forge.v4.ForgeConfigRegistry;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.configuration.ConfigHolder;
import mc.mian.lifesteal.data.neoforge.LSCapabilities;
import mc.mian.lifesteal.data.neoforge.LSDataAttachments;
import mc.mian.lifesteal.datagen.LSDataGenerators;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;

import static mc.mian.lifesteal.advancement.LSCriteria.*;

@Mod(LSConstants.MOD_ID)
public class LifestealNeoForge {
    public static IEventBus modEventBus;
    public static IEventBus commonEventBus = NeoForge.EVENT_BUS;

    public LifestealNeoForge(IEventBus modEventBusParam) {
        modEventBus = modEventBusParam;
        modEventBus.addListener(this::registerEvent);

        ForgeConfigRegistry.INSTANCE.register(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();

        LSDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        modEventBus.register(LSDataGenerators.class);
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