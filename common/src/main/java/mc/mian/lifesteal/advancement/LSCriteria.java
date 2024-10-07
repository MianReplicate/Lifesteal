package mc.mian.lifesteal.advancement;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.advancements.CriteriaTriggers;

public class LSCriteria {

    public static LSAdvancementTrigger GET_10_MAX_HEARTS = new LSAdvancementTrigger(LSConstants.GET_10_MAX_HEARTS);
    public static LSAdvancementTrigger USE_TOTEM_WHILE_20_MAX_HEARTS = new LSAdvancementTrigger(LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS);
    public static LSAdvancementTrigger BACK_FROM_THE_DEAD = new LSAdvancementTrigger(LSConstants.BACK_FROM_THE_DEAD);
    public static LSAdvancementTrigger REVIVED = new LSAdvancementTrigger(LSConstants.REVIVED);

    public static void init() {
        LifeSteal.LOGGER.debug("Initializing ModCriteria for " + LifeSteal.MOD_ID);
        CriteriaTriggers.register(GET_10_MAX_HEARTS);
        CriteriaTriggers.register(USE_TOTEM_WHILE_20_MAX_HEARTS);
        CriteriaTriggers.register(BACK_FROM_THE_DEAD);
        CriteriaTriggers.register(REVIVED);
    }
}
