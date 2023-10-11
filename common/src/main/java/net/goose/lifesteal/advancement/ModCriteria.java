package net.goose.lifesteal.advancement;

import net.goose.lifesteal.LifeSteal;
import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteria {

    public static LSAdvancementTrigger GET_10_MAX_HEARTS = new LSAdvancementTrigger();
    public static LSAdvancementTrigger USE_TOTEM_WHILE_20_MAX_HEARTS = new LSAdvancementTrigger();
    public static LSAdvancementTrigger REVIVED = new LSAdvancementTrigger();

    public static void init() {
        LifeSteal.LOGGER.debug("Initializing ModCriteria for " + LifeSteal.MOD_ID);
        CriteriaTriggers.register("lifesteal:get_10_max_hearts", GET_10_MAX_HEARTS);
        CriteriaTriggers.register("lifesteal:use_totem_while_20_max_hearts", USE_TOTEM_WHILE_20_MAX_HEARTS);
        CriteriaTriggers.register("lifesteal:revived", REVIVED);
    }

}
