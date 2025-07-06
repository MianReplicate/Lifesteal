package mc.mian.lifesteal;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import mc.mian.lifesteal.common.advancement.LSCriteria;
import net.fabricmc.api.ModInitializer;
import mc.mian.lifesteal.common.configuration.ConfigHolder;
import mc.mian.lifesteal.event.CommandRegistry;
import mc.mian.lifesteal.event.LSEvents;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.world.gen.LSBiomeModifier;
import net.minecraft.advancements.CriteriaTriggers;
import net.neoforged.fml.config.ModConfig;

public class LifestealFabric implements ModInitializer {

    public static void registerCriteria(){
        LSConstants.LOGGER.debug("Initializing ModCriteria for " + LSConstants.MOD_ID);
        CriteriaTriggers.register(LSConstants.GET_10_MAX_HEARTS.toString(),
                LSCriteria.GET_10_MAX_HEARTS);
        CriteriaTriggers.register(LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS.toString(),
                LSCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS);
        CriteriaTriggers.register(LSConstants.BACK_FROM_THE_DEAD.toString(),
                LSCriteria.BACK_FROM_THE_DEAD);
        CriteriaTriggers.register(LSConstants.REVIVED.toString(),
                LSCriteria.REVIVED);
    }
    @Override
    public void onInitialize() {
        ConfigRegistry.INSTANCE.register(LSConstants.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
        LSEvents.register();
        CommandRegistry.register();
        LSBiomeModifier.register();
        registerCriteria();
    }
}