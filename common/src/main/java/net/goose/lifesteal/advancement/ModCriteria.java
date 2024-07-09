package net.goose.lifesteal.advancement;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteria {

    public static LSAdvancementTrigger GET_10_MAX_HEARTS = new LSAdvancementTrigger(ModResources.GET_10_MAX_HEARTS);
    public static LSAdvancementTrigger USE_TOTEM_WHILE_20_MAX_HEARTS = new LSAdvancementTrigger(ModResources.USE_TOTEM_WHILE_20_MAX_HEARTS);
    public static LSAdvancementTrigger BACK_FROM_THE_DEAD = new LSAdvancementTrigger(ModResources.BACK_FROM_THE_DEAD);
    public static LSAdvancementTrigger REVIVED = new LSAdvancementTrigger(ModResources.REVIVED);

    public static void init() {
        LifeSteal.LOGGER.debug("Initializing ModCriteria for " + LifeSteal.MOD_ID);
        CriteriaTriggers.register(GET_10_MAX_HEARTS);
        CriteriaTriggers.register(USE_TOTEM_WHILE_20_MAX_HEARTS);
        CriteriaTriggers.register(BACK_FROM_THE_DEAD);
        CriteriaTriggers.register(REVIVED);
    }
}
