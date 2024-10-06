package mc.mian.lifesteal.fabric;

import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.configuration.ConfigHolder;
import mc.mian.lifesteal.fabric.event.CommandRegistry;
import mc.mian.lifesteal.fabric.event.ModEvents;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.world.gen.LSBiomeModifier;
import net.minecraft.advancements.CriteriaTriggers;
import net.neoforged.fml.config.ModConfig;

import static mc.mian.lifesteal.advancement.LSCriteria.*;

public class LifestealFabric implements ModInitializer {

    public static void registerCriteria(){
        LSConstants.LOGGER.debug("Initializing ModCriteria for " + LSConstants.MOD_ID);
        CriteriaTriggers.register(LSConstants.GET_10_MAX_HEARTS.toString(), GET_10_MAX_HEARTS);
        CriteriaTriggers.register(LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS.toString(), USE_TOTEM_WHILE_20_MAX_HEARTS);
        CriteriaTriggers.register(LSConstants.BACK_FROM_THE_DEAD.toString(), BACK_FROM_THE_DEAD);
        CriteriaTriggers.register(LSConstants.REVIVED.toString(), REVIVED);
    }
    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(LSConstants.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
        ModEvents.register();
        CommandRegistry.register();
        LSBiomeModifier.register();
        registerCriteria();
    }
}